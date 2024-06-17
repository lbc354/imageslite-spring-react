package com.fullstack.imageliteapi.infra.repositories.specs;

import org.springframework.data.jpa.domain.Specification;

import com.fullstack.imageliteapi.domain.entities.Image;
import com.fullstack.imageliteapi.domain.enums.ImageExtension;

public class ImageSpecs {

	private ImageSpecs() {
	}

	public static Specification<Image> extensionEqual(ImageExtension extension) {
//		o nome "extension temos que olhar para o nome do campo na entidade e não no banco
		return (root, q, cb) -> cb.equal(root.get("extension"), extension);
	}

	public static Specification<Image> nameLike(String name) {
		return (root, q, cb) -> cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");
	}

//	colocando em caixa alta, independe de como o usuário digitar (caixa alta, caixa baixa, camel case, etc),
//	vamos transformar tudo em caixa alta e comparar
//  e os % antes e depois é para procurar por qualquer string que contenha o valor da query (igual no sql mesmo)

	public static Specification<Image> tagsLike(String tags) {
		return (root, q, cb) -> cb.like(cb.upper(root.get("tags")), "%" + tags.toUpperCase() + "%");
	}

}