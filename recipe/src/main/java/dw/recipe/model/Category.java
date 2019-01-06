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
public class Category {

	@Id
	private String id;
	private String description;
	private Set<Recipe> recipes = new HashSet<>();
	
}
