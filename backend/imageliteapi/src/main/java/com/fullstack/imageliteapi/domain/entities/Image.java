package com.fullstack.imageliteapi.domain.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fullstack.imageliteapi.domain.enums.ImageExtension;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_image")
@EntityListeners(AuditingEntityListener.class)
public class Image {

//	ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column
	private String name;

	@Column
	private Long size;

	@Column
	@Enumerated(EnumType.STRING)
	private ImageExtension extension;

	@Column
	@CreatedDate
	private LocalDateTime uploadDate;

	@Column
	private String tags;

	@Column(name = "file", columnDefinition = "LONGBLOB")
	@Lob // indica que o campo é um arquivo, uma stream de dados
	private byte[] file;

//	CONSTRUTORES
	public Image() {
	}

	public Image(String name, Long size, ImageExtension extension, String tags, byte[] file) {
		this.name = name;
		this.size = size;
		this.extension = extension;
		this.tags = tags;
		this.file = file;
	}

	public Image(String name, Long size, ImageExtension extension, LocalDateTime uploadDate, String tags, byte[] file) {
		this.name = name;
		this.size = size;
		this.extension = extension;
		this.uploadDate = uploadDate;
		this.tags = tags;
		this.file = file;
	}

	public Image(String id, String name, Long size, ImageExtension extension, LocalDateTime uploadDate, String tags,
			byte[] file) {
		this.id = id;
		this.name = name;
		this.size = size;
		this.extension = extension;
		this.uploadDate = uploadDate;
		this.tags = tags;
		this.file = file;
	}

//	GETTERS AND SETTERS
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public ImageExtension getExtension() {
		return extension;
	}

	public void setExtension(ImageExtension extension) {
		this.extension = extension;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

//	MÉTODOS
	public String nomeDoArquivo() {
		return getName().concat(".").concat(getExtension().name());
	}

}