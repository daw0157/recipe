package dw.recipe.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import dw.recipe.model.Category;
import dw.recipe.model.Recipe;

public class CategoryTest {

	Category category;
	
	@Before
	public void setup(){
		category = new Category();
		
	}
	
	@Test
	public void testGetId() {
		Long idValue = 4l;
		category.setId(idValue);
		assertEquals(idValue, category.getId());
	}

	@Test
	public void testGetDescription() {
		String description = "test description";
		category.setDescription(description);
		assertEquals(description, category.getDescription());
	}

	@Ignore
	@Test
	public void testGetRecipes() {
		Recipe r1 = new Recipe();
		r1.setId(1L);
		category.getRecipes().add(r1);
		fail("Not yet implemented");
	}

}
