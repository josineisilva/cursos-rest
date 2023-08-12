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

import br.unitau.inf.cursos.dto.CursoGetDTO;
import br.unitau.inf.cursos.dto.CursoPostDTO;
import br.unitau.inf.cursos.dto.CursoPutDTO;
import br.unitau.inf.cursos.dto.CursoTotaisDTO;
import br.unitau.inf.cursos.model.Curso;
import br.unitau.inf.cursos.model.Matricula;
import br.unitau.inf.cursos.repository.CursoRepository;
import br.unitau.inf.cursos.repository.MatriculaRepository;

@RestController
@RequestMapping("/curso")
public class CursoController {
	@Autowired
	private CursoRepository repository;

	@Autowired
	private MatriculaRepository matriculaRepository;

    @GetMapping
    public List<CursoGetDTO> get() {
    	List<Curso> lista = repository.findAll();
    	return CursoGetDTO.convert(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoGetDTO> getById(@PathVariable Integer id) {
    	ResponseEntity<CursoGetDTO> ret = ResponseEntity.notFound().build();
    	Optional<Curso> search = repository.findById(id);
    	if (search.isPresent()) {
    		Curso item = search.get();
    		ret = ResponseEntity.ok(new CursoGetDTO(item));
    	} else
    		System.out.println("Curso nao encontrado");
    	return ret;
    }
    
    @GetMapping("/totais")
    public List<CursoTotaisDTO> totais() {
    	List<CursoTotaisDTO> lista = repository.totais();
    	return lista;
    }

	@PostMapping
	@Transactional
	public ResponseEntity<CursoGetDTO> post(@RequestBody @Valid CursoPostDTO body, UriComponentsBuilder uriBuilder) {
		ResponseEntity<CursoGetDTO> ret = ResponseEntity.unprocessableEntity().build();
		Curso item = body.convert();
		Optional<Curso> search = repository.findByDescricao(item.getDescricao());
		if (!search.isPresent()) {
			repository.save(item);
			URI uri = uriBuilder.path("/curso/{id}").buildAndExpand(item.getId()).toUri();
			ret = ResponseEntity.created(uri).body(new CursoGetDTO(item));
		} else
			System.out.println("Descricao do curso ja existente");
		return ret;
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CursoGetDTO> put(@PathVariable("id") int id, @RequestBody @Valid CursoPutDTO body, UriComponentsBuilder uriBuilder) {
		ResponseEntity<CursoGetDTO> ret = ResponseEntity.notFound().build();
		Optional<Curso> search = repository.findById(id);
		if (search.isPresent()) {
			Curso item = search.get();
			boolean found = false;
			Optional<Curso> other = repository.findByDescricao(body.getDescricao());
			if (other.isPresent()) {
				Curso old = other.get();
				if (old.getId()!=item.getId()) {
					found = true;
					System.out.println("Descricao do curso ja existente");
					ret = ResponseEntity.unprocessableEntity().build();
				}
			}
			if (!found) {
				body.update(item);				
				URI uri = uriBuilder.path("/curso/{id}").buildAndExpand(item.getId()).toUri();
				ret = ResponseEntity.created(uri).body(new CursoGetDTO(item));
			}
		} else
			System.out.println("Curso nao encontrado");
		return ret;
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		ResponseEntity<Curso> ret = ResponseEntity.notFound().build();
		Optional<Curso> search = repository.findById(id);
		if (search.isPresent()) {
			Curso item = search.get();
			List<Matricula> matriculas = matriculaRepository.findByCurso_id(id);
			if (matriculas.size() == 0) {
				repository.delete(item);
				ret = ResponseEntity.ok().build();
			} else {
				System.out.println("Ja existem matriculas para o curso");
				ret = ResponseEntity.unprocessableEntity().build();
			}
		} else
			System.out.println("Curso nao encontrado");
		return ret;
	}
}