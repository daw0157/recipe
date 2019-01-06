package dw.recipe.services;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dw.recipe.commands.IngredientCommand;
import dw.recipe.commands.UnitOfMeasureCommand;
import dw.recipe.converters.IngredientCommandToIngredient;
import dw.recipe.converters.IngredientToIngredientCommand;
import dw.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import dw.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dw.recipe.model.Ingredient;
import dw.recipe.model.Recipe;
import dw.recipe.repositories.RecipeRepository;
import dw.recipe.repositories.reactive.RecipeReactiveRepository;
import dw.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import reactor.core.publisher.Mono;

public class IngredientServiceImplTest {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
	
	@Mock
	RecipeReactiveRepository recipeReactiveRepository;
	
	@Mock
	UnitOfMeasureReactiveRepository uomRepository;
	
	IngredientService ingredientService;
	
	public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
				recipeReactiveRepository, uomRepository);
	}
	
	@Test
	public void testFindByRecipeIdAndIngredientId() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");
		
		Ingredient ing1 = new Ingredient();
		ing1.setId("1");
		
		Ingredient ing2 = new Ingredient();
		ing2.setId("2");
		
		Ingredient ing3 = new Ingredient();
		ing3.setId("3");
		
		recipe.addIngredient(ing1);
		recipe.addIngredient(ing2);
		recipe.addIngredient(ing3);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
		
		IngredientCommand command = ingredientService.findByRecipeIdAndIngredientId("1", "3").block();
		
		assertEquals("3", command.getId());
		verify(recipeReactiveRepository, times(1)).findById(anyString());
	}
	
	@Test
	public void testSaveRecipe() throws Exception {
		IngredientCommand command = new IngredientCommand();
		command.setId("3");
		command.setRecipeId("2");
		command.setUnitOfMeasure(new UnitOfMeasureCommand());
		command.getUnitOfMeasure().setId("1234");
		
		Recipe savedRecipe   = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId("3");
		
		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
		when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));
		
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();
		
		assertEquals("3", savedCommand.getId());
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, times(1)).save(any());
	}
	
	@Test
	public void testDeleteIngredient() throws Exception {
		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();
		ingredient.setId("3");
		recipe.addIngredient(ingredient);
		
		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));
		
		ingredientService.deleteIngredient("1", "3");
		
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
	}

}
