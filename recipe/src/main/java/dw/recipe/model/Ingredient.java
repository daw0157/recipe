package dw.recipe.model;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingredient {

	@Id
	private String id;
	private String description;
	private BigDecimal amount;
	
	@DBRef
	private UnitOfMeasure unitOfMeasure;
	//private Recipe recipe;
	
	public Ingredient() {
	}
	
	public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
		this.description = description;
		this.amount = amount;
		this.unitOfMeasure = unitOfMeasure;
	}
	
	public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure, Recipe recipe) {
		this.description = description;
		this.amount = amount;
		this.unitOfMeasure = unitOfMeasure;
		//this.recipe = recipe;
	}
}
