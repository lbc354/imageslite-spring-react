package com.fullstack.imageliteapi.domain.services;

import java.util.List;
import java.util.Optional;

import com.fullstack.imageliteapi.domain.entities.Image;
import com.fullstack.imageliteapi.domain.enums.ImageExtension;

public interface ImageService {

	Image salvar(Image img);

	Optional<Image> buscarUma(String id);

	List<Image> buscarImagensPorCriterio(ImageExtension extension, String query);

}