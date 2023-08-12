package br.unitau.inf.cursos.dto;

import java.sql.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.unitau.inf.cursos.model.Matricula;

public class MatriculaPutDTO {
	@NotNull
	@PastOrPresent
	@JsonFormat(pattern="yyyy/MM/dd",timezone = "America/Sao_Paulo")	
	private Date data;
	@Positive
	private float valor;
	
	public Date getData() {
		return data;
	}

	public float getValor() {
		return valor;
	}

	public void update(Matricula item) {
		item.setData(this.data);
		item.setValor(this.valor);
	}
}