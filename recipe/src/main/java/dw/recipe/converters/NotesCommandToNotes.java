package dw.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import dw.recipe.commands.NotesCommand;
import dw.recipe.model.Notes;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes>{
	
	@Override
	public Notes convert(NotesCommand source) {
		if(source == null) {
			return null;
		}
		
		Notes notes = new Notes();
		notes.setId(source.getId());
		notes.setRecipeNotes(source.getRecipeNotes());
		return notes;
	}

}
