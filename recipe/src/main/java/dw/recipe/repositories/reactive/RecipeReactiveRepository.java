package dw.recipe.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import dw.recipe.model.Recipe;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String>{

}
