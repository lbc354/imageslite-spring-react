package com.fullstack.imageliteapi.infra.repositories.specs;

import org.springframework.data.jpa.domain.Specification;

//public class GenericSpecs<T> {
public class GenericSpecs {

	private GenericSpecs() {
	}

//	no java, quando não especificamos o T lá em cima ^ para ele saber que esse T é o que passamos como parâmetro
//	vamos dizer que esse T é o que vamos receber como parâmetro lá na hora que for criada a especification
//	inserimos ele aqui para dizer que é qualquer parâmetro que quisermos receber lá
	public static <T> Specification<T> conjunction() {
		return (root, q, criteriaBuilder) -> criteriaBuilder.conjunction();
//		root são os dados da entidade que estamos pesquisando, no caso, a imagem
//		q é a query sendo montada no momento (SELECT * FROM IMAGE)
//		criteriaBuilder é o objeto em que consigo construir os critérios (WHERE 1 = 1 AND EXTENSION = 'PNG' AND ...)
	}

}
