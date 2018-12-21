package dw.recipe.repositories;

import org.springframework.data.repository.CrudRepository;

import dw.recipe.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
