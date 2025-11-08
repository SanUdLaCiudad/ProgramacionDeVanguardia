package com.demo.programacion.model;

import lombok.Data;

@Data
public class Clase {
	
	private String disciplina;
	private String dia;
	private int reservas;
	private String entrenador;
	
	
	public Clase() {
		
	}
	
	public Clase(String disciplina, String dia, int reservas, String entrenador) {
		this.disciplina = disciplina;
		this.dia = dia;
		this.reservas = reservas;
		this.entrenador = entrenador;
	}
	
	
	public String getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getEntrenador() {
		return entrenador;
	}
	public void setEntrenador(String entrenador) {
		this.entrenador = entrenador;
	}
	public int getReservas() {
		return reservas;
	}
	public void setReservas(int reservas) {
		this.reservas = reservas;
	}
	
	
	

}
