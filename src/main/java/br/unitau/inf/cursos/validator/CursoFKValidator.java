package br.unitau.inf.cursos.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.unitau.inf.cursos.anotations.CursoFK;
import br.unitau.inf.cursos.model.Curso;
import br.unitau.inf.cursos.repository.CursoRepository;

public class CursoFKValidator implements ConstraintValidator<CursoFK, Integer> {
	@Autowired
	private CursoRepository repository;

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (value != null) {
			Optional<Curso> search = repository.findById(value);
			if (search.isPresent()) {
				return true;
			}
		}
		return false;
	}
}