package dw.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dw.recipe.commands.IngredientCommand;
import dw.recipe.converters.IngredientToIngredientCommand;
import dw.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dw.recipe.model.Ingredient;
import dw.recipe.model.Recipe;
import dw.recipe.repositories.RecipeRepository;

public class IngredientServiceImplTest {

	IngredientService ingredientService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		ingredientService = new IngredientServiceImpl(new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()), recipeRepository);
	}
	
	@Test
	public void testFindByRecipeIdAndIngredientId() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ing1 = new Ingredient();
		ing1.setId(1L);
		
		Ingredient ing2 = new Ingredient();
		ing2.setId(2L);
		
		Ingredient ing3 = new Ingredient();
		ing3.setId(3L);
		
		recipe.addIngredient(ing1);
		recipe.addIngredient(ing2);
		recipe.addIngredient(ing3);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		IngredientCommand command = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);
		
		assertEquals(Long.valueOf(3L), command.getId());
		assertEquals(Long.valueOf(1L), command.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());
		
		
	}

}
