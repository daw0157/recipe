package dw.recipe.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dw.recipe.commands.IngredientCommand;
import dw.recipe.converters.IngredientToIngredientCommand;
import dw.recipe.model.Recipe;
import dw.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand ingredientCommand;
	private final RecipeRepository recipeRepository;

	public IngredientServiceImpl(IngredientToIngredientCommand ingredientCommand, RecipeRepository recipeRepository) {
		super();
		this.ingredientCommand = ingredientCommand;
		this.recipeRepository = recipeRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map( ingredient -> ingredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
	}
	
}
