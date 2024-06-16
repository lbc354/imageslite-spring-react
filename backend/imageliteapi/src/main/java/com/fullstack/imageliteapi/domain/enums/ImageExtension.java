package com.fullstack.imageliteapi.domain.enums;

import java.util.Arrays;

import org.springframework.http.MediaType;

public enum ImageExtension {

	PNG(MediaType.IMAGE_PNG), JPEG(MediaType.IMAGE_JPEG), GIF(MediaType.IMAGE_GIF);

	private MediaType mediaType;

	private ImageExtension(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public static ImageExtension valueOf(MediaType mediaType) {
		return Arrays.stream(values()).filter(imageExtension -> imageExtension.mediaType.equals(mediaType)).findFirst()
				.orElse(null);
	}

}
