package dw.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dw.recipe.converters.RecipeCommandToRecipe;
import dw.recipe.converters.RecipeToRecipeCommand;
import dw.recipe.exceptions.NotFoundException;
import dw.recipe.model.Recipe;
import dw.recipe.repositories.RecipeRepository;

public class RecipeServiceImplTest {

	RecipeService recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}
	
	@Test
	public void getRecipeByIdTest() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		
		Recipe recipeReturned = recipeService.findById("1");
		
		assertNotNull("Null recipe returned", recipeReturned);
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository, never()).findAll();
	}
	
	@Test
	public void getRecipesTest() {
		Recipe recipe = new Recipe();
		Set<Recipe> recipesData = new HashSet<>();
		recipesData.add(recipe);
		
		when(recipeService.getRecipes()).thenReturn(recipesData);
		
		Set<Recipe> recipes = recipeService.getRecipes();
		
		assertEquals(recipes.size(), 1);
		verify(recipeRepository, times(1)).findAll();
		verify(recipeRepository, never()).findById(anyString());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		recipeService.deleteRecipeById("2");
		
		verify(recipeRepository, times(1)).deleteById(anyString());
	}
	
	@Test(expected = NotFoundException.class)
	public void getRecipeByIdTestNotFound() throws Exception {
		Optional<Recipe> recipeOptional = Optional.empty();
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		
		Recipe recipeReturned = recipeService.findById("1");
	}

}
