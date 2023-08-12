package br.unitau.inf.cursos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unitau.inf.cursos.model.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
	Optional<Matricula> findByCurso_idAndAluno_id(Integer curso_id, Integer aluno_id);
	List<Matricula> findByCurso_id(Integer curso_id);
	List<Matricula> findByAluno_id(Integer aluno_id);
}