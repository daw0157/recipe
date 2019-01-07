package dw.recipe.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dw.recipe.commands.RecipeCommand;
import dw.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {

	private final RecipeService recipeService;
	
	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@GetMapping({"/recipe/{id}/show"})
	public String showById(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id).block());
		
		return "recipe/show";
	}
	
	@GetMapping("recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		
		return "recipe/recipeform";
	}
	
	@GetMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findRecipeCommandById(id).block());
		
		return "recipe/recipeform";
	}
	
	@PostMapping("recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> {
				log.debug(objectError.toString());
			});
			return "recipe/recipeform";
		}
		
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command).block();
		
		return "redirect:/recipe/"+ savedCommand.getId() + "/show/";
	}
	
	@GetMapping("/recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		log.debug("Deleting id: " + id);
		recipeService.deleteRecipeById(id);
		return "redirect:/";
	}
}
