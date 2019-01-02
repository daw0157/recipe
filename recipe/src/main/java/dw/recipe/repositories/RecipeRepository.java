package dw.recipe.repositories;

import org.springframework.data.repository.CrudRepository;

import dw.recipe.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, String>{

}
