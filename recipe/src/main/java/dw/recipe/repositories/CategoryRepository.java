package dw.recipe.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dw.recipe.model.Category;

public interface CategoryRepository extends CrudRepository<Category, String> {

	Optional<Category> findByDescription(String description);
	
}
