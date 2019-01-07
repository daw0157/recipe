package dw.recipe.services;

import org.springframework.stereotype.Service;

import dw.recipe.commands.RecipeCommand;
import dw.recipe.converters.RecipeCommandToRecipe;
import dw.recipe.converters.RecipeToRecipeCommand;
import dw.recipe.model.Recipe;
import dw.recipe.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

	private final RecipeReactiveRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	
	public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Flux<Recipe> getRecipes() {
		return recipeRepository.findAll();
	}
	
	@Override
	public Mono<Recipe> findById(String id) {
		return recipeRepository.findById(id);
	}
	
	@Override
	public Mono<RecipeCommand> findRecipeCommandById(String id) {
		return recipeRepository.findById(id)
				.map(recipe -> {
					RecipeCommand command = recipeToRecipeCommand.convert(recipe);
					
					command.getIngredients().forEach(rc -> {
						rc.setRecipeId(command.getId());
					});
					return command;
				});
	}
	
	@Override
	public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
		return recipeRepository.save(recipeCommandToRecipe.convert(command))
				.map(recipeToRecipeCommand::convert);
	}
	
	@Override
	public Mono<Void> deleteRecipeById(String id) {
		recipeRepository.deleteById(id).block();
		
		return Mono.empty();
	}

}
