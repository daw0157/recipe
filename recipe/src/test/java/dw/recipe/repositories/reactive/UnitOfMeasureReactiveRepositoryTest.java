package dw.recipe.repositories.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import dw.recipe.model.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

	@Autowired
	UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	
	@Before
	public void setUp() {
		unitOfMeasureReactiveRepository.deleteAll().block();
	}
	
	@Test
	public void testUomSave() {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription("uom description");
		
		unitOfMeasureReactiveRepository.save(uom).block();
		
		Long count = unitOfMeasureReactiveRepository.count().block();
		
		assertEquals(Long.valueOf(1L), count);
	}
	
	@Test
	public void testUomFindByDescription() {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription("uom description");
		
		unitOfMeasureReactiveRepository.save(uom).then().block();
		
		UnitOfMeasure uomFound = unitOfMeasureReactiveRepository.findByDescription("uom description").block();
		
		assertNotNull(uomFound);
	}

}
