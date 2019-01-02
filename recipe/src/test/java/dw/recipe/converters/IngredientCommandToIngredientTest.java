package dw.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import dw.recipe.commands.IngredientCommand;
import dw.recipe.commands.UnitOfMeasureCommand;
import dw.recipe.model.Ingredient;

public class IngredientCommandToIngredientTest {

	public static final String ID = "1";
	public static String DESCRIPTION = "description";
	public static String UOM_DESCRIPTION = "uom description";
	public static final String UOM_ID = "5";
	IngredientCommandToIngredient converter;
	
	@Before
	public void setUp() {
		converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}
	
	public void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	public void testEmptyObject() {
		assertNotNull(converter.convert(new IngredientCommand()));
	}
	
	@Test
	public void testConvert() {
		//given
		IngredientCommand ingredientCommand  = new IngredientCommand();
		ingredientCommand.setId(ID);
		ingredientCommand.setDescription(DESCRIPTION);
		
		UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
		uom.setId(UOM_ID);
		uom.setDescription(UOM_DESCRIPTION);
		ingredientCommand.setUnitOfMeasure(uom);
		
		//when
		Ingredient ingredient = converter.convert(ingredientCommand);
		
		//then
		assertEquals(ID, ingredient.getId());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId());
		assertEquals(UOM_DESCRIPTION, ingredient.getUnitOfMeasure().getDescription());
	}

}
