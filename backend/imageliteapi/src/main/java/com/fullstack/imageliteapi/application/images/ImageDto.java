package com.fullstack.imageliteapi.application.images;

import java.time.LocalDate;

public class ImageDto {

//	ATRIBUTOS
	private String url, name, extension;

	private Long size;

	private LocalDate uploadDate;

//	CONSTRUTORES
	public ImageDto() {
	}

	public ImageDto(String url, String name, String extension, Long size, LocalDate uploadDate) {
		this.url = url;
		this.name = name;
		this.extension = extension;
		this.size = size;
		this.uploadDate = uploadDate;
	}

//	GETTERS
	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	public String getExtension() {
		return extension;
	}

	public Long getSize() {
		return size;
	}

	public LocalDate getUploadDate() {
		return uploadDate;
	}

}