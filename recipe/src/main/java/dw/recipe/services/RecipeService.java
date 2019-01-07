package dw.recipe.services;

import dw.recipe.commands.RecipeCommand;
import dw.recipe.model.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {
	
	Flux<Recipe> getRecipes();
	Mono<Recipe> findById(String id);
	Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);
	Mono<RecipeCommand> findRecipeCommandById(String id);
	Mono<Void> deleteRecipeById(String id);
	
}
