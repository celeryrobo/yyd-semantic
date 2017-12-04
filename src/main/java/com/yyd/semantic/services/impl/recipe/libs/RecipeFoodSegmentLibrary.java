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
public class RecipeFoodSegmentLibrary implements SegmentLibrary {
	private final static String QUERY = "PREFIX recipe: <http://www.yydrobot.com/ontologies/recipe.owl#>"
			+ "SELECT DISTINCT ?a WHERE {?a recipe:hasIngredient ?c}";
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
				String localName = qs.getResource("a").getLocalName();
				if (!localName.isEmpty()) {
					result.add(new Value(localName, "recipeFood", "1"));
				}
			}
		}
		return result;
	}

}
