package com.fullstack.imageliteapi.application.images;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fullstack.imageliteapi.domain.entities.Image;
import com.fullstack.imageliteapi.domain.services.ImageService;
import com.fullstack.imageliteapi.infra.repositories.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {

	private final ImageRepository imgrep;

	public ImageServiceImpl(ImageRepository imgrep) {
		this.imgrep = imgrep;
	}

	@Override
	@Transactional
	public Image salvar(Image img) {
		return imgrep.save(img);
	}

}