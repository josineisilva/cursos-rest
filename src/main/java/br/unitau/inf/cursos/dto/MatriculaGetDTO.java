package br.unitau.inf.cursos.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.unitau.inf.cursos.model.Matricula;

public class MatriculaGetDTO {
	private Integer id;
	private Date data;
	private float valor;
	Integer curso_id;
	String curso_descricao;
	Integer aluno_id;
	String aluno_nome;

	public MatriculaGetDTO() {
	}

	public MatriculaGetDTO(Matricula matricula) {
		this.id = matricula.getId();
		this.data = matricula.getData();
		this.valor = matricula.getValor();
		this.curso_id = matricula.getCurso().getId();
		this.curso_descricao = matricula.getCurso().getDescricao();
		this.aluno_id = matricula.getAluno().getId();
		this.aluno_nome = matricula.getAluno().getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public Integer getCurso_id() {
		return curso_id;
	}

	public void setCurso_id(Integer curso_id) {
		this.curso_id = curso_id;
	}

	public String getCurso_descricao() {
		return curso_descricao;
	}

	public void setCurso_descricao(String curso_descricao) {
		this.curso_descricao = curso_descricao;
	}

	public Integer getAluno_id() {
		return aluno_id;
	}

	public void setAluno_id(Integer aluno_id) {
		this.aluno_id = aluno_id;
	}

	public String getAluno_nome() {
		return aluno_nome;
	}

	public void setAluno_nome(String aluno_nome) {
		this.aluno_nome = aluno_nome;
	}

	public static List<MatriculaGetDTO> convert(List<Matricula> lista) {
		return lista.stream().map(MatriculaGetDTO::new).collect(Collectors.toList());
	}
}