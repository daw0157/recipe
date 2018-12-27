package dw.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import dw.recipe.commands.NotesCommand;
import dw.recipe.model.Notes;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

	@Override
	public NotesCommand convert(Notes source) {
		if(source == null) {
			return null;
		}
		
		NotesCommand command = new NotesCommand();
		command.setId(source.getId());
		command.setRecipeNotes(source.getRecipeNotes());
		return command;
	}

	
	
}
