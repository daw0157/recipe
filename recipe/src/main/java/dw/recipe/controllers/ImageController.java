package dw.recipe.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import dw.recipe.commands.RecipeCommand;
import dw.recipe.services.ImageService;
import dw.recipe.services.RecipeService;

@Controller
public class ImageController {
	
	private final RecipeService recipeService;
	private final ImageService imageService;
	
	public ImageController(RecipeService recipeService, ImageService imageService) {
		super();
		this.recipeService = recipeService;
		this.imageService = imageService;
	}

	@GetMapping("recipe/{id}/image")
	public String showUploadForm(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findRecipeCommandById(id));
		return "recipe/imageuploadform";
	}
	
	@PostMapping("recipe/{id}/image")
	public String saveImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
		imageService.saveImageFile(id, file);
		return "redirect:/recipe/" + id + "/show";
	}
	
	@GetMapping("/recipe/{id}/recipeimage")
	public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
		RecipeCommand command = recipeService.findRecipeCommandById(id);
		
		if (command.getImage() != null) {
            byte[] byteArray = new byte[command.getImage().length];
            int i = 0;

            for (Byte wrappedByte : command.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
	}

}
