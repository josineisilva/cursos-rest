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

import br.unitau.inf.cursos.dto.AlunoGetDTO;
import br.unitau.inf.cursos.dto.AlunoPostDTO;
import br.unitau.inf.cursos.dto.AlunoPutDTO;
import br.unitau.inf.cursos.model.Aluno;
import br.unitau.inf.cursos.model.Matricula;
import br.unitau.inf.cursos.repository.AlunoRepository;
import br.unitau.inf.cursos.repository.MatriculaRepository;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
	@Autowired
	private AlunoRepository repository;

	@Autowired
	private MatriculaRepository matriculaRepository;

    @GetMapping
    public List<AlunoGetDTO> get() {
    	List<Aluno> lista = repository.findAll();
    	return AlunoGetDTO.convert(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getById(@PathVariable Integer id) {
    	ResponseEntity<Aluno> ret = ResponseEntity.notFound().build();
    	Optional<Aluno> search = repository.findById(id);
    	if (search.isPresent()) {
    		Aluno item = search.get();
    		ret = ResponseEntity.ok(item);
    	} else
    		System.out.println("Aluno nao encontrado");
    	return ret;
    }

	@PostMapping
	@Transactional
	public ResponseEntity<AlunoGetDTO> post(@RequestBody @Valid AlunoPostDTO body, UriComponentsBuilder uriBuilder) {
		ResponseEntity<AlunoGetDTO> ret = ResponseEntity.unprocessableEntity().build();
		Aluno item = body.convert();
		Optional<Aluno> search = repository.findByNome(item.getNome());
		if (!search.isPresent()) {
			repository.save(item);
			URI uri = uriBuilder.path("/aluno/{id}").buildAndExpand(item.getId()).toUri();
			ret = ResponseEntity.created(uri).body(new AlunoGetDTO(item));
		} else
			System.out.println("Nome do aluno ja existente");
		return ret;
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<AlunoGetDTO> put(@PathVariable("id") int id, @RequestBody @Valid AlunoPutDTO body, UriComponentsBuilder uriBuilder) {
		ResponseEntity<AlunoGetDTO> ret = ResponseEntity.notFound().build();
		Optional<Aluno> search = repository.findById(id);
		if (search.isPresent()) {
			Aluno item = search.get();
			boolean found = false;
			Optional<Aluno> other = repository.findByNome(body.getNome());
			if (other.isPresent()) {
				Aluno old = other.get();
				if (old.getId()!=item.getId()) {
					found = true;
					System.out.println("Nome do aluno ja existente");
					ret = ResponseEntity.unprocessableEntity().build();
				}
			}
			if (!found) {
				body.update(item);				
				URI uri = uriBuilder.path("/aluno/{id}").buildAndExpand(item.getId()).toUri();
				ret = ResponseEntity.created(uri).body(new AlunoGetDTO(item));
			}
		} else
			System.out.println("Aluno nao encontrado");
		return ret;
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		ResponseEntity<Aluno> ret = ResponseEntity.notFound().build();
		Optional<Aluno> search = repository.findById(id);
		if (search.isPresent()) {
			Aluno item = search.get();
			List<Matricula> matriculas = matriculaRepository.findByAluno_id(id);
			if (matriculas.size() == 0) {
				repository.delete(item);
				ret = ResponseEntity.ok().build();
			} else {
				System.out.println("Ja existem matriculas para o aluno");
				ret = ResponseEntity.unprocessableEntity().build();
			}
		} else
			System.out.println("Aluno nao encontrado");
		return ret;
	}
}