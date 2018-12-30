package dw.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import dw.recipe.commands.CategoryCommand;
import dw.recipe.commands.IngredientCommand;
import dw.recipe.commands.RecipeCommand;
import dw.recipe.model.Recipe;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe>{
	
	private final NotesCommandToNotes notesConverter;
	private final IngredientCommandToIngredient ingredientConverter;
	private final CategoryCommandToCategory categoryConverter;
	
	public RecipeCommandToRecipe(NotesCommandToNotes notesConverter, IngredientCommandToIngredient ingredientConverter,
			CategoryCommandToCategory categoryConverter) {
		super();
		this.notesConverter = notesConverter;
		this.ingredientConverter = ingredientConverter;
		this.categoryConverter = categoryConverter;
	}

	@Override
	public Recipe convert(RecipeCommand source) {
		if(source == null) {
			return null;
		}
		
		Recipe recipe = new Recipe();
		
		recipe.setCookTime(source.getCookTime());
		recipe.setDescription(source.getDescription());
		recipe.setDifficulty(source.getDifficulty());
		recipe.setDirections(source.getDirections());
		recipe.setId(source.getId());
		recipe.setNotes(notesConverter.convert(source.getNotes()));
		recipe.setPrepTime(source.getPrepTime());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		recipe.setImage(source.getImage());
		
		if(source.getCategories() != null && !source.getCategories().isEmpty()) {
			source.getCategories().forEach((CategoryCommand category) -> recipe.getCategories().add(categoryConverter.convert(category)));
		}
		
		if(source.getIngredients() != null && !source.getIngredients().isEmpty()) {
			source.getIngredients().forEach((IngredientCommand ingredient) -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
		}
		
		return recipe;
	}

}
