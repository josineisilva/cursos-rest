package br.unitau.inf.cursos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.unitau.inf.cursos.dto.CursoTotaisDTO;
import br.unitau.inf.cursos.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
	Optional<Curso> findByDescricao(String descricao);
	@Query(value = "SELECT curso.id, curso.descricao, COUNT(*) AS alunos, SUM(valor) AS valor "
			  + "FROM curso, matricula WHERE matricula.curso_id=curso.id "
			  + "GROUP BY curso.id ORDER BY curso.id", nativeQuery = true)
	List<CursoTotaisDTO> totais();
}