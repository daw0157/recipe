package dw.recipe.services;

import java.util.Set;

import dw.recipe.model.Recipe;

public interface RecipeService {
	
	Set<Recipe> getRecipes();
	Recipe findById(Long l);
	
}
