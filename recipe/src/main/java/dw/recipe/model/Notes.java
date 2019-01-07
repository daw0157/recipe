package dw.recipe.model;

import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notes {

	@Id
	private String id;
	private String recipeNotes;
	
}
