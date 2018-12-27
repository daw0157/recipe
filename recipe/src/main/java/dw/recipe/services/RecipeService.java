package dw.recipe.services;

import java.util.Set;

import dw.recipe.commands.RecipeCommand;
import dw.recipe.model.Recipe;

public interface RecipeService {
	
	Set<Recipe> getRecipes();
	Recipe findById(Long l);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	RecipeCommand findRecipeCommandById(Long id);
	
}
