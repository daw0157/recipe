package dw.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import dw.recipe.commands.IngredientCommand;
import dw.recipe.model.Ingredient;
import dw.recipe.model.UnitOfMeasure;

public class IngredientToIngredientCommandTest {

	public static final String ID = "1";
	public static String DESCRIPTION = "description";
	public static String UOM_DESCRIPTION = "uom description";
	public static final String UOM_ID = "5";
	IngredientToIngredientCommand converter;
	
	@Before
	public void setUp() {
		converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}
	
	public void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	public void testEmptyObject() {
		assertNotNull(converter.convert(new Ingredient()));
	}
	
	@Test
	public void testConvert() {
		//given
		Ingredient ingredient  = new Ingredient();
		ingredient.setId(ID);
		ingredient.setDescription(DESCRIPTION);
		
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(UOM_ID);
		uom.setDescription(UOM_DESCRIPTION);
		ingredient.setUnitOfMeasure(uom);
		
		//when
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		
		//then
		assertEquals(ID, ingredientCommand.getId());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
		assertEquals(UOM_ID, ingredientCommand.getUnitOfMeasure().getId());
		assertEquals(UOM_DESCRIPTION, ingredientCommand.getUnitOfMeasure().getDescription());
	}

}
