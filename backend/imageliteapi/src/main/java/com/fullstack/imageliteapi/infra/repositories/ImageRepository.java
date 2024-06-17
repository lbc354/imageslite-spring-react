package com.fullstack.imageliteapi.infra.repositories;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import com.fullstack.imageliteapi.domain.entities.Image;
import com.fullstack.imageliteapi.domain.enums.ImageExtension;
import com.fullstack.imageliteapi.infra.repositories.specs.GenericSpecs;
import com.fullstack.imageliteapi.infra.repositories.specs.ImageSpecs;

//specification é uma forma de criar critérios de pesquisa orientado a objeto sem precisar escrever sql
public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

//	SELECT * FROM IMAGE WHERE 1 = 1 AND EXTENSION = 'PNG' AND ( NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY' )
	default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {
//		SELECT * FROM IMAGE WHERE 1 = 1
//		where 1 = 1 retorna true, ou seja, vai listar tudo no banco
		Specification<Image> spec = Specification.where(GenericSpecs.conjunction());

//		aqui está a necessidade do specification
//		assim, conseguimos criar querys dinâmicas baseado na condição de ter um parâmetro ou não
		if (extension != null) {
			spec = spec.and(ImageSpecs.extensionEqual(extension));
		}

//		usamos hasText ao invés de comparar com nulo porque, caso o usuário digite um espaço,
//		espaço é diferente de nulo e isso faria com que buscássemos por espaços no banco
//		com hasText esperamos um valor de fato ser inserido
		if (StringUtils.hasText(query)) {
//			essa é a parte do AND ( NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY' )
			Specification<Image> nameOrTagsLike = Specification.anyOf(ImageSpecs.nameLike(query),
					ImageSpecs.tagsLike(query));

			spec = spec.and(nameOrTagsLike);
		}

		return findAll(spec);
	}

}