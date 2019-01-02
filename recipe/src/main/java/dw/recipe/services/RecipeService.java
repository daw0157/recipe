package dw.recipe.services;

import java.util.Set;

import dw.recipe.commands.RecipeCommand;
import dw.recipe.model.Recipe;

public interface RecipeService {
	
	Set<Recipe> getRecipes();
	Recipe findById(String id);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	RecipeCommand findRecipeCommandById(String id);
	void deleteRecipeById(String id);
	
}
