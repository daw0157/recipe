package dw.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dw.recipe.services.RecipeService;

@Controller
public class IndexController {

	private final RecipeService recipeService;
	
	public IndexController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@RequestMapping({"","/","index","/index"})
	public String index(Model model) {
		model.addAttribute("recipes", recipeService.getRecipes());
		
		return "index";
	}
	
}
