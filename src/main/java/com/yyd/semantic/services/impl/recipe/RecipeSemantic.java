package com.yyd.semantic.services.impl.recipe;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.nlp.NLPFactory;
import com.yyd.semantic.nlp.WordTerm;

@Component
public class RecipeSemantic implements Semantic<RecipeBean> {
	private final static String PREFIX = "PREFIX recipe: <http://www.yydrobot.com/ontologies/recipe.owl#>";
	@Value("${tdb.datasource.url}")
	private String tdbConnectionStr;

	@Value("${tdb.datasource.dataset.recipe}")
	private String datasetRecipe;

	@Override
	public RecipeBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		RecipeBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("action");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
		case RecipeIntent.IS_INGREDIENT_OF:
			result = isIngredientOf(objects, semanticContext);
			break;
		case RecipeIntent.HAS_COOKING_TIME:
		case RecipeIntent.HAS_HARD_LEVEL:
		case RecipeIntent.HAS_INGREDIENT:
		case RecipeIntent.HAS_STEPS_TEXT:
		case RecipeIntent.HAS_TASTE:
			result = processRecipeFood(objects, semanticContext, action);
			break;
		default:
			result = new RecipeBean("这句话太复杂了，我还不能理解");
			break;
		}
		return result;
	}

	private RecipeBean processRecipeFood(Map<String, String> slots, SemanticContext semanticContext, String action) {
		String result = "听不懂你说的什么";
		String recipeFood = slots.get(RecipeSlot.RECIPE_FOOD);
		RecipeSlot recipeSlot = new RecipeSlot(semanticContext.getParams());
		if (recipeFood == null) {
			recipeFood = recipeSlot.getRecipeFood();
		}
		if (recipeFood != null) {
			List<String> list = new LinkedList<>();
			StringBuilder sparsql = new StringBuilder(PREFIX).append("SELECT DISTINCT ?c WHERE {");
			sparsql.append("recipe:").append(recipeFood).append(" recipe:").append(action).append(" ?c.}");
			try (RDFConnection conn = RDFConnectionFactory.connect(tdbConnectionStr + datasetRecipe);
					QueryExecution qe = conn.query(sparsql.toString())) {
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
				result = "我没吃过" + recipeFood + "，不了解";
			} else {
				result = StringUtil.joiner(list, "、");
				recipeSlot.setRecipeFood(recipeFood);
			}
		}
		return new RecipeBean(result);
	}

	private RecipeBean isIngredientOf(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "听不懂你说的什么";
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
				result = "我不知道" + recpieIngredients + "能做什么菜";
			} else {
				result = StringUtil.joiner(list, "、");
			}
		}
		return new RecipeBean(result);
	}

}
