package com.fullstack.imageliteapi.application.images;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fullstack.imageliteapi.domain.entities.Image;
import com.fullstack.imageliteapi.domain.enums.ImageExtension;

@Component
public class ImageMapper {

	public Image mapToImage(MultipartFile file, String name, List<String> tags) throws IOException {
		return new Image(name, file.getSize(), ImageExtension.valueOf(MediaType.valueOf(file.getContentType())),
				String.join(",", tags), file.getBytes());
	}

}