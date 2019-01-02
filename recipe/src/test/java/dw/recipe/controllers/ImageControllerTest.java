package dw.recipe.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import dw.recipe.commands.RecipeCommand;
import dw.recipe.services.ImageService;
import dw.recipe.services.RecipeService;

public class ImageControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	ImageService imageService;
	
	MockMvc mockMvc;
	
	ImageController controller;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		controller = new ImageController(recipeService, imageService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}
	
	@Test
	public void testShowUploadForm() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId("1");
		
		when(recipeService.findRecipeCommandById(anyString())).thenReturn(command);
		
		mockMvc.perform(get("/recipe/1/image"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/imageuploadform"))
			.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testSaveImagePost() throws Exception {
		MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "test".getBytes());
		
		mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/recipe/1/show"));
		
		verify(imageService, times(1)).saveImageFile(anyString(), any());
		
	}
	
	@Test
	public void renderImageFromDB() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId("1");
		
		String s = "fake image text";
		Byte[] bytesBoxed = new Byte[s.getBytes().length];
		
		int i = 0;
		
		for(byte primByte : s.getBytes()) {
			bytesBoxed[i++] = primByte;
		}
		
		command.setImage(bytesBoxed);
		
		when(recipeService.findRecipeCommandById(anyString())).thenReturn(command);
		
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		byte[] responseBytes = response.getContentAsByteArray();
		
		assertEquals(s.getBytes().length, responseBytes.length);
	}
	
	@Test
	public void testGetImageNumberFormatException() throws Exception {
		mockMvc.perform(get("/recipe/asdf/recipeimage"))
			.andExpect(status().isBadRequest())
			.andExpect(view().name("400error"));
	}

}
