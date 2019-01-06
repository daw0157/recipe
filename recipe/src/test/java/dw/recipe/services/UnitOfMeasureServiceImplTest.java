package dw.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dw.recipe.commands.UnitOfMeasureCommand;
import dw.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dw.recipe.model.UnitOfMeasure;
import dw.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import reactor.core.publisher.Flux;

public class UnitOfMeasureServiceImplTest {

	UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	UnitOfMeasureService uomService;
	
	@Mock
	UnitOfMeasureReactiveRepository uomRepository;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		uomService = new UnitOfMeasureServiceImpl(uomRepository, uomToUomCommand);
	}
	
	@Test
	public void testListAllUoms() {
		Set<UnitOfMeasure> uoms = new HashSet<>();
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId("1");
		uoms.add(uom1);
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId("2");
		uoms.add(uom2);
		
		when(uomRepository.findAll()).thenReturn(Flux.just(uom1, uom2));
		
		List<UnitOfMeasureCommand> commands = uomService.listAllUoms().collectList().block();
		
		assertEquals(2, commands.size());
		verify(uomRepository, times(1)).findAll();
	}

}
