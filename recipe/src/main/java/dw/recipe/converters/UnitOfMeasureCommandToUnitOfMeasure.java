package dw.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import dw.recipe.commands.UnitOfMeasureCommand;
import dw.recipe.model.UnitOfMeasure;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

	@Override
	public UnitOfMeasure convert(UnitOfMeasureCommand source) {
		if(source == null) {
			return null;
		}
		
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription(source.getDescription());
		uom.setId(source.getId());
		return uom;
	}

}
