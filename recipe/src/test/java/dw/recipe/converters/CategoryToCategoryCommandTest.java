package dw.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import dw.recipe.commands.CategoryCommand;
import dw.recipe.model.Category;

public class CategoryToCategoryCommandTest {

	public static final String ID = "1";
	public static String DESCRIPTION = "description";
	CategoryToCategoryCommand converter;
	
	@Before
	public void setUp() {
		converter = new CategoryToCategoryCommand();
	}
	
	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Category()));
	}
	
	@Test
	public void testConvert() {
		//given
		Category category  = new Category();
		category.setId(ID);
		category.setDescription(DESCRIPTION);
		
		//when
		CategoryCommand categoryCommand = converter.convert(category);
		
		//then
		assertEquals(ID, categoryCommand.getId());
		assertEquals(DESCRIPTION, categoryCommand.getDescription());
		
	}

}
