package dw.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import dw.recipe.commands.UnitOfMeasureCommand;
import dw.recipe.model.UnitOfMeasure;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure source) {
		if(source == null) {
			return null;
		}
		
		UnitOfMeasureCommand command = new UnitOfMeasureCommand();
		command.setDescription(source.getDescription());
		command.setId(source.getId());
		return command;
	}
	
	

}
