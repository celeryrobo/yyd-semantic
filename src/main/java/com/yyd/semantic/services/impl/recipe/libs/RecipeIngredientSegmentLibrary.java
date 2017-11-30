package com.yyd.semantic.services.impl.recipe.libs;

import java.util.LinkedList;
import java.util.List;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;

@Component
public class RecipeIngredientSegmentLibrary implements SegmentLibrary {
	private final static String QUERY = "PREFIX recipe: <http://www.yydrobot.com/ontologies/recipe.owl#>"
			+ "SELECT DISTINCT ?c WHERE {?a recipe:hasIngredient ?c}";
	@org.springframework.beans.factory.annotation.Value("${tdb.datasource.url}")
	private String tdbConnectionStr;

	@org.springframework.beans.factory.annotation.Value("${tdb.datasource.dataset.recipe}")
	private String datasetRecipe;

	@Override
	public List<Value> load() {
		List<Value> result = new LinkedList<>();
		try (RDFConnection conn = RDFConnectionFactory.connect(tdbConnectionStr + datasetRecipe)) {
			QueryExecution qe = conn.query(QUERY);
			ResultSet rs = qe.execSelect();
			while (rs.hasNext()) {
				QuerySolution qs = rs.next();
				String localName = qs.getResource("c").getLocalName();
				if(localName.length() > 0) {
					result.add(new Value(localName, "recipeIngredient", "1"));
				}
			}
		}
		return result;
	}

}
