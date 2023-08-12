package br.unitau.inf.cursos.anotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.unitau.inf.cursos.validator.CursoFKValidator;

@Constraint(validatedBy = CursoFKValidator.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CursoFK {
    String message() default "Curso invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
