package br.unitau.inf.cursos.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.unitau.inf.cursos.model.Curso;
import br.unitau.inf.cursos.model.Matricula;

public class CursoGetDTO {
	private Integer id;
	private String descricao;
	
	private List<MatriculaGetDTO> matriculas;
	
	public CursoGetDTO() {	
	}
	
	public CursoGetDTO(Curso curso) {
		this.id = curso.getId();
		this.descricao = curso.getDescricao();
		List<Matricula> matriculas = curso.getMatriculas();
		if (matriculas!=null) {
			this.matriculas = MatriculaGetDTO.convert(matriculas);
		}
	}
	
	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<MatriculaGetDTO> getMatriculas() {
		return matriculas;
	}
	
	public static List<CursoGetDTO> convert(List<Curso> lista) {
		return lista.stream().map(CursoGetDTO::new).collect(Collectors.toList());
	}
}