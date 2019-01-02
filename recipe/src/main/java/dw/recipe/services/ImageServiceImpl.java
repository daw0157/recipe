package dw.recipe.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dw.recipe.model.Recipe;
import dw.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;
	
	public ImageServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void saveImageFile(String recipeId, MultipartFile file) {
		try {
			Recipe recipe = recipeRepository.findById(recipeId).get();
			
			Byte[] byteObjects = new Byte[file.getBytes().length];
			
			int i = 0;
			
			for(byte b : file.getBytes()) {
				byteObjects[i++] = b;
			}
			
			recipe.setImage(byteObjects);
			
			recipeRepository.save(recipe);
		} catch (IOException e) {
			log.error("error occured: " + e);
			e.printStackTrace();
		}

	}

}
