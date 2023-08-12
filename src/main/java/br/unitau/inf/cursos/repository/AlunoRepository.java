package br.unitau.inf.cursos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unitau.inf.cursos.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
	Optional<Aluno> findByNome(String nome);
}