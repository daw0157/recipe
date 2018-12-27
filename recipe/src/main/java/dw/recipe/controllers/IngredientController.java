package dw.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dw.recipe.services.IngredientService;
import dw.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}

	@GetMapping
	@RequestMapping({"/recipe/{id}/ingredients", "/recipe/{id}/ingredients/"})
	public String getIngredients(@PathVariable String id, Model model) {
		log.debug("Getting ingredient list for recipe id: " + id);
		model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));
		
		return "recipe/ingredient/list";
	}
	
	@GetMapping
	@RequestMapping({"/recipe/{recipeId}/ingredient/{ingredientId}/show"})
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		log.debug("Getting ingredient list for recipe id: {1} and ingredient id: {2}", recipeId, ingredientId);
		
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		
		return "recipe/ingredient/show";
	}
	
	
	
}
