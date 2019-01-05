package dw.recipe.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import dw.recipe.model.Category;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String>{

}
