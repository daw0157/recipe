package dw.recipe.services;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dw.recipe.commands.IngredientCommand;
import dw.recipe.converters.IngredientCommandToIngredient;
import dw.recipe.converters.IngredientToIngredientCommand;
import dw.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import dw.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dw.recipe.model.Ingredient;
import dw.recipe.model.Recipe;
import dw.recipe.repositories.RecipeRepository;
import dw.recipe.repositories.UnitOfMeasureRepository;

public class IngredientServiceImplTest {

	IngredientService ingredientService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	UnitOfMeasureRepository uomRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		ingredientService = new IngredientServiceImpl(new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
				recipeRepository, uomRepository);
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
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		
		IngredientCommand command = ingredientService.findByRecipeIdAndIngredientId("1", "3");
		
		assertEquals(Long.valueOf(3L), command.getId());
		assertEquals(Long.valueOf(1L), command.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyString());
	}
	
	@Test
	public void testSaveRecipe() throws Exception {
		IngredientCommand command = new IngredientCommand();
		command.setId("3");
		command.setRecipeId("2");
		
		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		
		Recipe savedRecipe   = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId("3");
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		assertEquals(Long.valueOf(3), savedCommand.getId());
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository, times(1)).save(any());
	}
	
	@Test
	public void testDeleteIngredient() throws Exception {
		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();
		ingredient.setId("3");
		recipe.addIngredient(ingredient);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		
		ingredientService.deleteIngredient("1", "3");
		
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	}

}
