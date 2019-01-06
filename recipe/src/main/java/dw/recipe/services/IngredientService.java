package dw.recipe.services;

import dw.recipe.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {

	Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
	Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);
	Mono<Void> deleteIngredient(String recipeId, String ingredientId);
	
}
