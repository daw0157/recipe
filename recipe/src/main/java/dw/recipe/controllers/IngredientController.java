package dw.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping
	@RequestMapping({"/recipe/{recipeId}/ingredient/{ingredientId}/update"})
	public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		log.debug("Getting ingredient list for recipe id: {1} and ingredient id: {2}", recipeId, ingredientId);
		
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		
		model.addAttribute("uomList", uomService.listAllUoms());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@PostMapping
	@RequestMapping({"recipe/{recipeId}/ingredient"})
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		IngredientCommand saveCommand = ingredientService.saveIngredientCommand(command);
		
		return "redirect:/recipe/" + saveCommand.getRecipeId() + "/ingredient/" + saveCommand.getId() + "/show";
	}
	
	@GetMapping
	@RequestMapping({"recipe/{recipeId}/ingredient/new"})
	public String newIngredient(@PathVariable String recipeId, Model model) {
		RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeCommand.getId());
		model.addAttribute("ingredient", ingredientCommand);
		ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
		model.addAttribute("uomList", uomService.listAllUoms());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping
	@RequestMapping({"recipe/{recipeId}/ingredient/{ingredientId}/delete"})
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		ingredientService.deleteIngredient(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
	
}
