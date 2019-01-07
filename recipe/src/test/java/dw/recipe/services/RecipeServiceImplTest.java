package dw.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dw.recipe.converters.RecipeCommandToRecipe;
import dw.recipe.converters.RecipeToRecipeCommand;
import dw.recipe.exceptions.NotFoundException;
import dw.recipe.model.Recipe;
import dw.recipe.repositories.reactive.RecipeReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RecipeServiceImplTest {

	RecipeService recipeService;
	
	@Mock
	RecipeReactiveRepository recipeRepository;
	
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
		
		when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
		
		Mono<Recipe> recipeReturned = recipeService.findById("1");
		
		assertNotNull("Null recipe returned", recipeReturned);
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository, never()).findAll();
	}
	
	@Test
	public void getRecipesTest() {
		Recipe recipe = new Recipe();
		recipe.setId("1");
		
		when(recipeService.getRecipes()).thenReturn(Flux.just(recipe));
		
		Flux<Recipe> recipes = recipeService.getRecipes();
		
		assertEquals(recipes.collectList().block().size(), 1);
		verify(recipeRepository, times(1)).findAll();
		verify(recipeRepository, never()).findById(anyString());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		//given
        String idToDelete = "2";

        when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());

        //when
        recipeService.deleteRecipeById(idToDelete);

        //then
        verify(recipeRepository, times(1)).deleteById(anyString());
	}
	

}
