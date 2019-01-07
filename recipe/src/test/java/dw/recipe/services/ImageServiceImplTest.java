package dw.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import dw.recipe.model.Recipe;
import dw.recipe.repositories.reactive.RecipeReactiveRepository;
import reactor.core.publisher.Mono;

public class ImageServiceImplTest {

	@Mock
	RecipeReactiveRepository recipeRepository;
	
	ImageService imageService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		imageService = new ImageServiceImpl(recipeRepository);
	}
	
	@Test
	public void testSaveImageFile() throws Exception{
		String id = "1";
		MultipartFile mutlipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "test".getBytes());
		
		Recipe recipe = new Recipe();
		recipe.setId(id);
		
		when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));
		
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		imageService.saveImageFile(id, mutlipartFile);
		
		verify(recipeRepository, times(1)).save(argumentCaptor.capture());
		Recipe savedRecipe = argumentCaptor.getValue();
		assertEquals(mutlipartFile.getBytes().length, savedRecipe.getImage().length);
	}

}
