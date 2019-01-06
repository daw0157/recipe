package dw.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dw.recipe.commands.IngredientCommand;
import dw.recipe.commands.RecipeCommand;
import dw.recipe.commands.UnitOfMeasureCommand;
import dw.recipe.services.IngredientService;
import dw.recipe.services.RecipeService;
import dw.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService uomService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService uomService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.uomService = uomService;
	}

	@GetMapping({"/recipe/{id}/ingredients", "/recipe/{id}/ingredients/"})
	public String getIngredients(@PathVariable String id, Model model) {
		log.debug("Getting ingredient list for recipe id: " + id);
		model.addAttribute("recipe", recipeService.findRecipeCommandById(id));
		
		return "recipe/ingredient/list";
	}
	
	@GetMapping({"/recipe/{recipeId}/ingredient/{ingredientId}/show"})
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		log.debug("Getting ingredient list for recipe id: {1} and ingredient id: {2}", recipeId, ingredientId);
		
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block());
		
		return "recipe/ingredient/show";
	}
	
	@GetMapping({"/recipe/{recipeId}/ingredient/{ingredientId}/update"})
	public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		log.debug("Getting ingredient list for recipe id: {1} and ingredient id: {2}", recipeId, ingredientId);
		
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block());
		
		model.addAttribute("uomList", uomService.listAllUoms().collectList().block());
		
 		return "recipe/ingredient/ingredientform";
	}
	
	@PostMapping({"recipe/{recipeId}/ingredient"})
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		IngredientCommand saveCommand = ingredientService.saveIngredientCommand(command).block();
		
		return "redirect:/recipe/" + command.getRecipeId() + "/ingredient/" + saveCommand.getId() + "/show";
	}
	
	@GetMapping({"recipe/{recipeId}/ingredient/new"})
	public String newIngredient(@PathVariable String recipeId, Model model) {
		RecipeCommand recipeCommand = recipeService.findRecipeCommandById(recipeId);
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeCommand.getId());
		model.addAttribute("ingredient", ingredientCommand);
		ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
		model.addAttribute("uomList", uomService.listAllUoms().collectList().block());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping({"recipe/{recipeId}/ingredient/{ingredientId}/delete"})
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		ingredientService.deleteIngredient(recipeId, ingredientId).block();
		log.debug("recipeId: " + recipeId);
		log.debug("ingredientId: " + ingredientId);
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
	
}
