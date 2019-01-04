package dw.recipe.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dw.recipe.commands.IngredientCommand;
import dw.recipe.converters.IngredientCommandToIngredient;
import dw.recipe.converters.IngredientToIngredientCommand;
import dw.recipe.model.Ingredient;
import dw.recipe.model.Recipe;
import dw.recipe.repositories.RecipeRepository;
import dw.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient; 
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository uomRepository;

	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository,
			UnitOfMeasureRepository uomRepository) {
		super();
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.uomRepository = uomRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map( ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }
        
        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
        ingredientCommand.setRecipeId(recipeId);

        return ingredientCommandOptional.get();
	}
	
	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		
		if(!recipeOptional.isPresent()) {
			log.error("Recipe not found for id: " + command.getRecipeId());
			return new IngredientCommand();
		}
		Recipe recipe = recipeOptional.get();
		
		Optional<Ingredient> ingredientOptional = recipe.getIngredients()
				.stream()
				.filter(ingredient -> ingredient.getId().equals(command.getId()))
				.findFirst();
		
		if(ingredientOptional.isPresent()) {
			Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());
            ingredientFound.setUnitOfMeasure(uomRepository
                    .findById(command.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
		} else {
			Ingredient ingredient = ingredientCommandToIngredient.convert(command);
			recipe.addIngredient(ingredient);
		}
		
		Recipe savedRecipe = recipeRepository.save(recipe);
		
		Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
				.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
				.findFirst();
		
		if(!savedIngredientOptional.isPresent()) {
			savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
					.filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
					.filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
					.findFirst();
		}
		
		return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
		
	}
	
	@Override
	public void deleteIngredient(String recipeId, String ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if(!recipeOptional.isPresent()) {
			throw new RuntimeException("no recipe " + recipeId);
		}
		Recipe recipe = recipeOptional.get();
		
		Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
			.filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientId))
			.findFirst();
		
		if(!optionalIngredient.isPresent()) {
			throw new RuntimeException("no ingredient " + ingredientId);
		}
		
		Ingredient ingredientToDelete = optionalIngredient.get();
		recipe.getIngredients().remove(optionalIngredient.get());
		recipeRepository.save(recipe);
	}
}
