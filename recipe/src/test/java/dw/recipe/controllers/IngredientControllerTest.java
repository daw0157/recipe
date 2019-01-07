package dw.recipe.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import dw.recipe.commands.IngredientCommand;
import dw.recipe.commands.RecipeCommand;
import dw.recipe.commands.UnitOfMeasureCommand;
import dw.recipe.services.IngredientService;
import dw.recipe.services.RecipeService;
import dw.recipe.services.UnitOfMeasureService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IngredientControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	IngredientService ingredientService;
	
	@Mock
	UnitOfMeasureService uomService;
	
	IngredientController controller;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		controller = new IngredientController(recipeService, ingredientService, uomService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testListIngredients() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findRecipeCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
		
		//when
		mockMvc.perform(get("/recipe/1/ingredients"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/list"))
			.andExpect(model().attributeExists("recipe"));
		
		//then
		verify(recipeService, times(1)).findRecipeCommandById(anyString());
	}
	
	@Test
	public void testShowIngredient() throws Exception {
		IngredientCommand command = new IngredientCommand();
		
		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(Mono.just(command));
		
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/show"))
			.andExpect(model().attributeExists("ingredient"));
	}
	
	@Test
	public void testNewIngredientForm() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("1");
		
		when(recipeService.findRecipeCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
		when(uomService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));
		
		mockMvc.perform(get("/recipe/1/ingredient/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/ingredientform"))
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(model().attributeExists("uomList"));
	}
	
	@Test
	public void testUpdateIngredientForm() throws Exception {
		IngredientCommand command = new IngredientCommand();
		
		when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(Mono.just(command));
		when(uomService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));
		
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/ingredientform"))
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(model().attributeExists("uomList"));
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		IngredientCommand command = new IngredientCommand();
		command.setId("3");
		command.setRecipeId("2");
		
		when(ingredientService.saveIngredientCommand(any())).thenReturn(Mono.just(command));
		
		mockMvc.perform(post("/recipe/2/ingredient")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("id", "")
					.param("description", "some string")
				)
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
	}
	
	@Test
	public void testDeleteIngredient() throws Exception {
		when(ingredientService.deleteIngredient(anyString(), anyString())).thenReturn(Mono.empty());
		
		mockMvc.perform(get("/recipe/2/ingredient/1/delete"))
				
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/ingredients"));
		
		verify(ingredientService, times(1)).deleteIngredient(anyString(), anyString());
	}
}
