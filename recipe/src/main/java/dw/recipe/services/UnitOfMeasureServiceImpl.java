package dw.recipe.services;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import dw.recipe.commands.UnitOfMeasureCommand;
import dw.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dw.recipe.repositories.UnitOfMeasureRepository;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureRepository uomRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand command;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository,
			UnitOfMeasureToUnitOfMeasureCommand command) {
		super();
		this.uomRepository = uomRepository;
		this.command = command;
	}

	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {
		return StreamSupport.stream(uomRepository.findAll()
				.spliterator(), false)
				.map(command::convert)
				.collect(Collectors.toSet());
		
	}

}
