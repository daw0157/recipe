package dw.recipe.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notes {

	@Id
	private String id;
	private String recipeNotes;
	
}
