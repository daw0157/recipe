package dw.recipe.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dw.recipe.commands.RecipeCommand;
import dw.recipe.converters.RecipeCommandToRecipe;
import dw.recipe.converters.RecipeToRecipeCommand;
import dw.recipe.exceptions.NotFoundException;
import dw.recipe.model.Recipe;
import dw.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	
	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.debug("I'm in the service");
		
		Set<Recipe> recipes = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}
	
	@Override
	public Recipe findById(String id) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(id);
		
		if(!recipeOptional.isPresent()) {
			throw new NotFoundException("Recipe not found for id: " + id.toString());
		}
		
		return recipeOptional.get();
	}
	
	@Override
	@Transactional
	public RecipeCommand findRecipeCommandById(String id) {
		return recipeToRecipeCommand.convert(findById(id));
	}
	
	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		log.debug("Saved RecipeId: " + savedRecipe.getId());
		return recipeToRecipeCommand.convert(savedRecipe);
	}
	
	@Override
	public void deleteRecipeById(String id) {
		recipeRepository.deleteById(id);
	}

}
