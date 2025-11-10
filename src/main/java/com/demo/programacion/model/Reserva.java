package com.demo.programacion.model;

public class Reserva {
	
	private String clase;
	private String dia;
	
	
	
	
	public Reserva(String clase, String dia) {
		super();
		this.clase = clase;
		this.dia = dia;
	}
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	
	

}
