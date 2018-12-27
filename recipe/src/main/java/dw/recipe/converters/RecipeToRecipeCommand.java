package dw.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import dw.recipe.commands.RecipeCommand;
import dw.recipe.model.Category;
import dw.recipe.model.Ingredient;
import dw.recipe.model.Recipe;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand>{
	
	private final IngredientToIngredientCommand ingredientConverter;;
	
	private final NotesToNotesCommand notesConverter;
	
	private final CategoryToCategoryCommand categoryConverter;
	
	public RecipeToRecipeCommand(IngredientToIngredientCommand ingredientConverter, NotesToNotesCommand notesConverter,
			CategoryToCategoryCommand categoryConverter) {
		super();
		this.ingredientConverter = ingredientConverter;
		this.notesConverter = notesConverter;
		this.categoryConverter = categoryConverter;
	}

	@Override
	public RecipeCommand convert(Recipe source) {
		if(source == null) {
			return null;
		}
		
		RecipeCommand command = new RecipeCommand();
		command.setCookTime(source.getCookTime());
		command.setDescription(source.getDescription());
		command.setDifficulty(source.getDifficulty());
		command.setDirections(source.getDirections());
		command.setId(source.getId());
		command.setNotes(notesConverter.convert(source.getNotes()));
		command.setPrepTime(source.getPrepTime());
		command.setServings(source.getServings());
		command.setSource(source.getSource());
		command.setUrl(source.getUrl());
		
		if(source.getCategories() != null && !source.getCategories().isEmpty()) {
			source.getCategories().forEach((Category category) -> command.getCategories().add(categoryConverter.convert(category))); 
		}
		
		if(source.getIngredients() != null && !source.getIngredients().isEmpty()) {
			source.getIngredients().forEach((Ingredient ingredient) -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
		}
		
		return command;
	}
	

}
