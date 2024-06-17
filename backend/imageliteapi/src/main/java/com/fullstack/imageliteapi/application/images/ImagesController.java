package com.fullstack.imageliteapi.application.images;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fullstack.imageliteapi.domain.entities.Image;
import com.fullstack.imageliteapi.domain.enums.ImageExtension;
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

//	vamos retornar o array de bytes que representa a imagem
	@GetMapping("{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
		var possibleImage = imgserv.buscarUma(id);

		if (possibleImage.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		var img = possibleImage.get();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(img.getExtension().getMediaType());
		headers.setContentLength(img.getSize());
//		o primeiro parâmetro é a forma que vamos disponibilizar o conteúdo (download, nova página, etc)
//		o segundo parâmetro é o nome do arquivo
//		inline; filename="image.PNG"
		headers.setContentDispositionFormData("inline; filename=\"" + img.nomeDoArquivo() + "\"", img.nomeDoArquivo());

		return new ResponseEntity<>(img.getFile(), headers, HttpStatus.OK);
	}

	@GetMapping
//	http://localhost:8080/images?extension=PNG&query=puma
//	o defaultValue é para não dar erro de null
	public ResponseEntity<List<ImageDto>> buscarImagensPorCriterio(
			@RequestParam(value = "extension", required = false, defaultValue = "") String extension,
			@RequestParam(value = "query", required = false) String query) {

		var result = imgserv.buscarImagensPorCriterio(ImageExtension.valorDeNome(extension), query);

		var images = result.stream().map(image -> {
			var url = buildImageUrl(image);
			return imgmap.imageToDto(image, url.toString());
		}).collect(Collectors.toList());

		return ResponseEntity.ok(images);

	}

//	http://localhost:8080/images/{uuid}
	private URI buildImageUrl(Image image) {
		String imagePath = "/" + image.getId();
		return ServletUriComponentsBuilder.fromCurrentRequestUri().path(imagePath).build().toUri();
	}

}