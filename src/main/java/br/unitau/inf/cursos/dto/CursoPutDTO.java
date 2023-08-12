package br.unitau.inf.cursos.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.unitau.inf.cursos.model.Curso;

public class CursoPutDTO {
	@NotBlank
	@Length(max = 20)
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void update(Curso item) {
		item.setDescricao(this.descricao);
	}
}