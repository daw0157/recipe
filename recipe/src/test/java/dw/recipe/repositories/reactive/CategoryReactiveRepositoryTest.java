package dw.recipe.repositories.reactive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import dw.recipe.model.Category;
import dw.recipe.model.Recipe;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

	@Autowired
	CategoryReactiveRepository categoryReactiveRepository;
	
	@Before
	public void setUp() {
		categoryReactiveRepository.deleteAll().block();
	}
	
	@Test
	public void testCategorySave() {
		Category category = new Category();
		category.setDescription("test desc");
		
		categoryReactiveRepository.save(category).block();
		
		Long count = categoryReactiveRepository.count().block();
		
		assertEquals(Long.valueOf(1L), count);
	}

}
