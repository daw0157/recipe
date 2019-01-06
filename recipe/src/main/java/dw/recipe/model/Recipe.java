package dw.recipe.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class Recipe {
	
	@Id
	private String id;
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	private String directions;
	private Byte[] image;
	private Difficulty difficulty;
	private Notes notes;
	private Set<Ingredient> ingredients = new HashSet<>();
	private Set<Category> categories = new HashSet<>();
	
	public Recipe addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
		return this;
	}
	
	public void setNotes(Notes notes) {
		if(notes != null) {
			this.notes = notes;
		}
	}
	
}
