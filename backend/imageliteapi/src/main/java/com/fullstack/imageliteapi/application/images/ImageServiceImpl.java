package com.fullstack.imageliteapi.application.images;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fullstack.imageliteapi.domain.entities.Image;
import com.fullstack.imageliteapi.domain.enums.ImageExtension;
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
//	transactional é usado para operações de escrita
//	como a função de baixo é de busca, não tem necessidade
	public Image salvar(Image img) {
		return imgrep.save(img);
	}

	@Override
	public Optional<Image> buscarUma(String id) {
		return imgrep.findById(id);
	}

	@Override
	public List<Image> buscarImagensPorCriterio(ImageExtension extension, String query) {
		return imgrep.findByExtensionAndNameOrTagsLike(extension, query);
	}

}