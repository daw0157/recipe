package dw.recipe.services;

import dw.recipe.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
	IngredientCommand saveIngredientCommand(IngredientCommand command);
	void deleteIngredient(String recipeId, String ingredientId);
	
}
