package br.unitau.inf.cursos.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.unitau.inf.cursos.model.Aluno;
import br.unitau.inf.cursos.model.Matricula;

public class AlunoGetDTO {
	private Integer id;
	private String nome;
	
	private List<MatriculaGetDTO> matriculas;
	
	public AlunoGetDTO() {	
	}
	
	public AlunoGetDTO(Aluno curso) {
		this.id = curso.getId();
		this.nome = curso.getNome();
		List<Matricula> matriculas = curso.getMatriculas();
		if (matriculas!=null) {
			this.matriculas = MatriculaGetDTO.convert(matriculas);
		}
	}
	
	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public List<MatriculaGetDTO> getMatriculas() {
		return matriculas;
	}
	
	public static List<AlunoGetDTO> convert(List<Aluno> lista) {
		return lista.stream().map(AlunoGetDTO::new).collect(Collectors.toList());
	}
}