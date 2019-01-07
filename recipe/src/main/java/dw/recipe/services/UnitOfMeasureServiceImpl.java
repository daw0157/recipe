package dw.recipe.services;

import org.springframework.stereotype.Service;

import dw.recipe.commands.UnitOfMeasureCommand;
import dw.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dw.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureReactiveRepository uomRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand command;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository uomRepository,
			UnitOfMeasureToUnitOfMeasureCommand command) {
		super();
		this.uomRepository = uomRepository;
		this.command = command;
	}

	@Override
	public Flux<UnitOfMeasureCommand> listAllUoms() {
		
		return uomRepository
				.findAll()
				.map(command::convert);
		
	}

}
