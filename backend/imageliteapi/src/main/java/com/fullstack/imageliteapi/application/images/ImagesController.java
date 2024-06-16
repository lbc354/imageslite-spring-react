package com.fullstack.imageliteapi.application.images;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fullstack.imageliteapi.domain.entities.Image;
import com.fullstack.imageliteapi.domain.services.ImageService;

@RestController
@RequestMapping("/images")
public class ImagesController {

	private final ImageService imgserv;
	private final ImageMapper imgmap;

	public ImagesController(ImageService imgserv, ImageMapper imgmap) {
		this.imgserv = imgserv;
		this.imgmap = imgmap;
	}

//	as imagens vindas do frontend não vêm em application/json, vêm em multi-part/formdata

	@PostMapping
//	para transformar um parâmetro em opcional:
//	@RequestParam(value = "file", required = false)
	public ResponseEntity save(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("tags") List<String> tags) throws IOException {

		Image image = imgmap.mapToImage(file, name, tags);
		Image savedImage = imgserv.salvar(image);
		URI imageUri = buildImageUrl(savedImage);

//		return new ResponseEntity<String>("Imagem recebida:\nnome original: " + file.getOriginalFilename()
//				+ "\nnome definido para a imagem: " + name + "\ntamanho: " + file.getSize() + "\nextensão: "
//				+ ImageExtension.valueOf(MediaType.valueOf(file.getContentType())) + "\ntags: " + String.join(",", tags)
//				+ "\narquivo: " + file.getBytes(), HttpStatus.CREATED);

		return ResponseEntity.created(imageUri).build();

	}

//	http://localhost:8080/images/{uuid}
	private URI buildImageUrl(Image image) {
		String imagePath = "/" + image.getId();
		return ServletUriComponentsBuilder.fromCurrentRequest().path(imagePath).build().toUri();
	}

}