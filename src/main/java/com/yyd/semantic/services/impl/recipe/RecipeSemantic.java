package com.yyd.semantic.services.impl.recipe;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.DbSegLoader;
import com.yyd.semantic.common.DbSegLoader.Item;
import com.yyd.semantic.nlp.NLPFactory;
import com.yyd.semantic.nlp.WordTerm;

@Component
public class RecipeSemantic implements Semantic<RecipeBean> {
	private final static Integer SEMANTIC_FAIL = 101;
	private final static Integer RESOURCE_NOTEXSIT = 102;

	private final static String SEMANTIC_ERR_MSG = "听不懂你说的什么";

	private final static String PREFIX = "PREFIX recipe: <http://www.yydrobot.com/ontologies/recipe.owl#>";
	@Value("${tdb.datasource.url}")
	private String tdbConnectionStr;

	@Value("${tdb.datasource.dataset.recipe}")
	private String datasetRecipe;

	@Autowired
	private DbSegLoader dbSegLoader;

	@Override
	public RecipeBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		RecipeBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
		case RecipeIntent.IS_INGREDIENT_OF:
			result = isIngredientOf(objects, semanticContext);
			break;
		case RecipeIntent.HAS_COOKING_TIME:
		case RecipeIntent.HAS_HARD_LEVEL:
		case RecipeIntent.HAS_INGREDIENT:
		case RecipeIntent.HAS_TASTE:
			result = processRecipeFood(objects, semanticContext, action);
			break;
		case RecipeIntent.HAS_STEPS_TEXT:
			result = processRecipeStepText(objects, semanticContext);
			break;
		case RecipeIntent.THIS_RECIPE_FOOD:
			result = processThisRecipeFood(objects, semanticContext);
			break;
		default:
			result = new RecipeBean(SEMANTIC_FAIL, "这句话太复杂了，我还不能理解");
			break;
		}
		return result;
	}

	private RecipeBean processThisRecipeFood(Map<String, String> slots, SemanticContext semanticContext) {
		RecipeBean result = new RecipeBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		RecipeSlot recipeSlot = new RecipeSlot(semanticContext.getParams());
		String recipeFood = recipeSlot.getRecipeFood();
		if (recipeFood != null) {
			result.setText(recipeFood);
			result.setErrCode(0);
		}
		return result;
	}

	private RecipeBean processRecipeStepText(Map<String, String> slots, SemanticContext semanticContext) {
		RecipeBean result = new RecipeBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		String recipeFood = slots.get(RecipeSlot.RECIPE_FOOD);
		RecipeSlot recipeSlot = new RecipeSlot(semanticContext.getParams());
		if (recipeFood == null) {
			recipeFood = recipeSlot.getRecipeFood();
		}
		if (recipeFood != null) {
			Item item = dbSegLoader.getItem(recipeFood);
			Set<String> items = null;
			if (item == null) {
				items = new TreeSet<>();
				items.add(recipeFood);
			} else {
				items = item.getItems();
			}
			List<String> sets = new LinkedList<>();
			StringBuilder sparsql = new StringBuilder(PREFIX)
					.append("SELECT DISTINCT ?c (GROUP_CONCAT(DISTINCT ?d;SEPARATOR = \"|\") AS ?dd) WHERE {");
			for (String it : items) {
				sparsql.append("OPTIONAL {recipe:").append(it).append(" recipe:hasStepsText ?c}");
				sparsql.append("OPTIONAL {recipe:").append(it).append(" recipe:hasIngredient ?d}");
			}
			sparsql.append("} GROUP BY ?c");
			try (RDFConnection conn = RDFConnectionFactory.connect(tdbConnectionStr + datasetRecipe);
					QueryExecution qe = conn.query(sparsql.toString())) {
				ResultSet rs = qe.execSelect();
				while (rs.hasNext()) {
					QuerySolution qs = rs.next();
					String localName = qs.getResource("c").getURI();
					int idx = localName.indexOf("#");
					localName = idx > 0 ? localName.substring(idx + 1) : "";
					String ingredients = qs.get("dd").toString();
					String[] ingredientArr = ingredients.split("\\|");
					Set<String> ingredientSet = new TreeSet<>();
					for (String ingredient : ingredientArr) {
						idx = ingredient.indexOf("#");
						ingredient = idx > 0 ? ingredient.substring(idx + 1) : "";
						if (ingredient.length() > 0) {
							ingredientSet.add(ingredient);
						}
					}
					sets.add("首先需要准备的材料有：");
					sets.add(StringUtil.joiner(ingredientSet, ","));
					sets.add("；具体步骤：");
					if (localName.length() > 0) {
						sets.add(localName);
					}
				}
			}
			if (sets.isEmpty()) {
				result.setText("我没吃过" + recipeFood + "，不了解");
			} else {
				result.setText(StringUtil.joiner(sets, ""));
				recipeSlot.setRecipeFood(recipeFood);
			}
		}
		return result;
	}

	private RecipeBean processRecipeFood(Map<String, String> slots, SemanticContext semanticContext, String action) {
		RecipeBean result = new RecipeBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		String recipeFood = slots.get(RecipeSlot.RECIPE_FOOD);
		RecipeSlot recipeSlot = new RecipeSlot(semanticContext.getParams());
		if (recipeFood == null) {
			recipeFood = recipeSlot.getRecipeFood();
		}
		if (recipeFood != null) {
			Item item = dbSegLoader.getItem(recipeFood);
			Set<String> items = null;
			if (item == null) {
				items = new TreeSet<>();
				items.add(recipeFood);
			} else {
				items = item.getItems();
			}
			Set<String> sets = new HashSet<>();
			StringBuilder sparsql = new StringBuilder(PREFIX).append("SELECT DISTINCT ?c WHERE {");
			for (String it : items) {
				sparsql.append("OPTIONAL {recipe:").append(it).append(" recipe:").append(action).append(" ?c}");
			}
			sparsql.append("}");
			try (RDFConnection conn = RDFConnectionFactory.connect(tdbConnectionStr + datasetRecipe);
					QueryExecution qe = conn.query(sparsql.toString())) {
				ResultSet rs = qe.execSelect();
				while (rs.hasNext()) {
					QuerySolution qs = rs.next();
					String localName = qs.getResource("c").getURI();
					int idx = localName.indexOf("#");
					localName = idx > 0 ? localName.substring(idx + 1) : "";
					if (localName.length() > 0) {
						sets.add(localName);
					}
				}
			}
			if (sets.isEmpty()) {
				result.setText("我没吃过" + recipeFood + "，不了解");
			} else {
				result.setText(StringUtil.joiner(sets, "、"));
				recipeSlot.setRecipeFood(recipeFood);
			}
		}
		return result;
	}

	private RecipeBean isIngredientOf(Map<String, String> slots, SemanticContext semanticContext) {
		RecipeBean result = new RecipeBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		String recpieIngredients = slots.get(RecipeSlot.RECIPE_INGREDIENTS);
		if (recpieIngredients != null) {
			List<WordTerm> terms = NLPFactory.segment(recpieIngredients, "recipe_recipeIngredient");
			StringBuilder sb = new StringBuilder(PREFIX).append("SELECT DISTINCT ?c WHERE {");
			for (WordTerm term : terms) {
				if ("recipeIngredient".equals(term.getNature())) {
					sb.append("recipe:").append(term.getRealWord()).append(" recipe:isIngredientOf ?c. ");
				}
			}
			sb.append("} LIMIT 5");
			List<String> list = new LinkedList<>();
			try (RDFConnection conn = RDFConnectionFactory.connect(tdbConnectionStr + datasetRecipe);
					QueryExecution qe = conn.query(sb.toString())) {
				ResultSet rs = qe.execSelect();
				while (rs.hasNext()) {
					QuerySolution qs = rs.next();
					String localName = qs.getResource("c").getURI();
					int idx = localName.indexOf("#");
					localName = idx > 0 ? localName.substring(idx + 1) : "";
					if (localName.length() > 0) {
						list.add(localName);
					}
				}
			}
			if (list.isEmpty()) {
				result.setText("我不知道" + recpieIngredients + "能做什么菜");
			} else {
				result.setText(StringUtil.joiner(list, "、"));
			}
		}
		return result;
	}
}
