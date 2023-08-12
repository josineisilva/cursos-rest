package br.unitau.inf.cursos.anotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.unitau.inf.cursos.validator.AlunoFKValidator;

@Constraint(validatedBy = AlunoFKValidator.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AlunoFK {
    String message() default "Aluno invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
