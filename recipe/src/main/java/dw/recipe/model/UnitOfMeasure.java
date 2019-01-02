package dw.recipe.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class UnitOfMeasure {

	@Id
	private String id;	
	private String description;


}
