package br.unitau.inf.cursos.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.unitau.inf.cursos.dto.MatriculaGetDTO;
import br.unitau.inf.cursos.dto.MatriculaPostDTO;
import br.unitau.inf.cursos.dto.MatriculaPutDTO;
import br.unitau.inf.cursos.model.Aluno;
import br.unitau.inf.cursos.model.Curso;
import br.unitau.inf.cursos.model.Matricula;
import br.unitau.inf.cursos.repository.AlunoRepository;
import br.unitau.inf.cursos.repository.CursoRepository;
import br.unitau.inf.cursos.repository.MatriculaRepository;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {
	@Autowired
	private MatriculaRepository repository;

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private AlunoRepository alunoRepository;

	@GetMapping
	public List<MatriculaGetDTO> get() {
		List<Matricula> lista = repository.findAll();
		return MatriculaGetDTO.convert(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Matricula> getById(@PathVariable Integer id) {
		ResponseEntity<Matricula> ret = ResponseEntity.notFound().build();
		Optional<Matricula> search = repository.findById(id);
		if (search.isPresent()) {
			Matricula item = search.get();
			ret = ResponseEntity.ok(item);
		} else
			System.out.println("Curso nao encontrado");
		return ret;
	}

	@PostMapping
	@Transactional
	public ResponseEntity<MatriculaGetDTO> post(@RequestBody @Valid MatriculaPostDTO body,
			UriComponentsBuilder uriBuilder) {
		ResponseEntity<MatriculaGetDTO> ret = ResponseEntity.unprocessableEntity().build();
		Matricula item = body.convert(cursoRepository, alunoRepository);
		Curso curso = item.getCurso();
		if (curso != null) {
			Aluno aluno = item.getAluno();
			if (aluno != null) {
				Optional<Matricula> search = repository.findByCurso_idAndAluno_id(curso.getId(), aluno.getId());
				if (!search.isPresent()) {
					repository.save(item);
					URI uri = uriBuilder.path("/matricula/{id}").buildAndExpand(item.getId()).toUri();
					ret = ResponseEntity.created(uri).body(new MatriculaGetDTO(item));
				} else
					System.out.println("Aluno ja matriculado no curso");
			} else
				System.out.println("Aluno nao encontrado");
		} else
			System.out.println("Curso nao encontrado");
		return ret;
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<MatriculaGetDTO> put(@PathVariable("id") int id, @RequestBody @Valid MatriculaPutDTO body,
			UriComponentsBuilder uriBuilder) {
		ResponseEntity<MatriculaGetDTO> ret = ResponseEntity.unprocessableEntity().build();
		Optional<Matricula> search = repository.findById(id);
		if (search.isPresent()) {
			Matricula item = search.get();
			body.update(item);				
			URI uri = uriBuilder.path("/matricula/{id}").buildAndExpand(item.getId()).toUri();
			ret = ResponseEntity.created(uri).body(new MatriculaGetDTO(item));
		} else
			System.out.println("Aluno nao esta matriculado no curso");
		return ret;
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		ResponseEntity<Matricula> ret = ResponseEntity.notFound().build();
		Optional<Matricula> search = repository.findById(id);
		if (search.isPresent()) {
			Matricula item = search.get();
			repository.delete(item);
			ret = ResponseEntity.ok().build();
		} else
			System.out.println("Matricula nao encontrada");
		return ret;
	}
}