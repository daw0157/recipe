package dw.recipe.services;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dw.recipe.commands.RecipeCommand;
import dw.recipe.converters.RecipeCommandToRecipe;
import dw.recipe.converters.RecipeToRecipeCommand;
import dw.recipe.model.Recipe;
import dw.recipe.repositories.reactive.RecipeReactiveRepository;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

	public static final String NEW_DESCRIPTION = "New description";
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
    RecipeReactiveRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;
	
    @Transactional
	@Test
	public void test() {
		//given
    	Flux<Recipe> recipes = recipeRepository.findAll();
    	Recipe testRecipe = recipes.blockFirst();
    	RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);
    	
    	//when
    	testRecipeCommand.setDescription(NEW_DESCRIPTION);
    	RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand).block();
    	
    	//then
    	assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
    	assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
	}

}
