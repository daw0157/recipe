package dw.recipe.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import dw.recipe.model.Recipe;
import dw.recipe.repositories.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	
	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipes = new HashSet<>();
		
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		
		return recipes;
	}

}
