package com.yyd.semantic.services.impl.recipe;

import java.util.Map;

public class RecipeSlot {
	public final static String RECIPE_FOOD = "recipeFood";
	public final static String RECIPE_INGREDIENTS = "recipeIngredients";
	private Map<Object, Object> params;

	public RecipeSlot(Map<Object, Object> params) {
		this.params = params;
	}

	public String getRecipeFood() {
		return (String) params.get(RECIPE_FOOD);
	}

	public void setRecipeFood(String recipeFood) {
		params.put(RECIPE_FOOD, recipeFood);
	}
}
