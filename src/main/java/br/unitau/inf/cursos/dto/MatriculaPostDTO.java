package br.unitau.inf.cursos.dto;

import java.sql.Date;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.unitau.inf.cursos.anotations.AlunoFK;
import br.unitau.inf.cursos.anotations.CursoFK;
import br.unitau.inf.cursos.model.Aluno;
import br.unitau.inf.cursos.model.Curso;
import br.unitau.inf.cursos.model.Matricula;
import br.unitau.inf.cursos.repository.AlunoRepository;
import br.unitau.inf.cursos.repository.CursoRepository;

public class MatriculaPostDTO {
	@CursoFK
	Integer curso_id;
	@AlunoFK
	Integer aluno_id;
	@NotNull
	@PastOrPresent
	@JsonFormat(pattern="yyyy/MM/dd",timezone = "America/Sao_Paulo")	
	private Date data;
	@Positive
	private float valor;

	public Integer getCurso_id() {
		return curso_id;
	}

	public Integer getAluno_id() {
		return aluno_id;
	}
	
	public Date getData() {
		return data;
	}

	public float getValor() {
		return valor;
	}

	public Matricula convert(CursoRepository cursoRepository, AlunoRepository alunoRepository) {
		Matricula ret = new Matricula();
		ret.setData(this.data);
		ret.setValor(this.valor);
        Optional<Curso> cursoSearch = cursoRepository.findById(this.curso_id);
        if (cursoSearch.isPresent()) {
        	Curso curso = cursoSearch.get();
        	ret.setCurso(curso);
        }
        Optional<Aluno> alunoSearch = alunoRepository.findById(this.aluno_id);
        if (alunoSearch.isPresent()) {
        	Aluno aluno = alunoSearch.get();
        	ret.setAluno(aluno);
        }
		return ret;
	}
}