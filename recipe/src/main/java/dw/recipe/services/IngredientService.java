package dw.recipe.services;

import dw.recipe.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
	
}
