package dw.recipe.services;

import java.util.Set;

import dw.recipe.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listAllUoms();
	
}
