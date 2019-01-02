package dw.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import dw.recipe.commands.CategoryCommand;
import dw.recipe.model.Category;

public class CategoryCommandToCategoryTest {

	public static final String ID = "1";
	public static final String DESCRIPTION = "description";
	CategoryCommandToCategory converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new CategoryCommandToCategory();
	}
	
	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new CategoryCommand()));
	}
	
	@Test
	public void testConvert() throws Exception {
		//Given
		CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(ID);
		categoryCommand.setDescription(DESCRIPTION);
		
		//When
		Category category = converter.convert(categoryCommand);
		
		//then
		assertEquals(ID, category.getId());
		assertEquals(DESCRIPTION, category.getDescription());
		
	}

}
