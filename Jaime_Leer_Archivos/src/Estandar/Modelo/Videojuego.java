package Estandar.Modelo;

/*
 *Creado por Elias Periañez
 *24 sept. 2018
 *Como parte del proyecto Jaime_Leer_Archivos
 *Este archivo esta bajo la licencia de Creative Commons Reconocimiento 4.0 Internacional (Más informacion https://creativecommons.org/licenses/by/4.0/)
________________________________________________________________________________________________________________________________________________________
 *Created by Elias Periañez
 *24 sept. 2018
 *As part of the project Jaime_Leer_Archivos
 *This file is under the Creative Commons Attribution 4.0 International (More info here https://creativecommons.org/licenses/by/4.0/)
 */

public class Videojuego {

	private int id;
	private String titulo;
	private String descripcion;
	private String dato;
	private Desarrollador dev;

	public Videojuego(int id, String titulo, String descripcion, String dato, Desarrollador dev) {
		this.setId(id);
		this.setTitulo(titulo);
		this.setDescripcion(descripcion);
		this.setDato(dato);
		this.setDev(dev);
	}
	
	public Videojuego() {
		this.setId((Integer) null);
		this.setTitulo(null);
		this.setDescripcion(null);
		this.setDato(null);
		this.setDev(null);
	}

	public Videojuego(String data, String regex, Desarrollador dev) {
		String[] ar = data.split(regex);
		try {
			this.setId(Integer.parseInt(ar[0]));
		} catch (NumberFormatException e) {
			this.setId((Integer) null);
			System.err.println("El id debe ser un numero, se ha creado el objeto con id null");
		}
		this.setTitulo(ar[1]);
		this.setDescripcion(ar[2]);
		this.setDato(ar[3]);
		this.setDev(dev);
	}
	
	public boolean compararId(Videojuego vg) {
		boolean result = false;
		if(this.getId()==vg.getId()) {
			result = true;
		}
		
		return result;
	}
	
	public void imprimir() {
		System.out.println("Id: " + this.getId() + "\nTitulo:" + this.getTitulo() + "\nDescripcion: "
				+ this.getDescripcion() + "\nDato: " + this.getDato() + "\n");
	}

	public String toString() {
		return "Id " + this.getId() + "/Titulo " + this.getTitulo() + "/Descripcion " + this.getDescripcion() + "/Dato "
				+ this.getDato();
	}

	public String toProcesedString(String regex) {
		return this.getId() + regex + this.getTitulo() + regex + this.getDescripcion() + regex + this.getDato();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public Desarrollador getDev() {
		return dev;
	}

	public void setDev(Desarrollador dev) {
		this.dev = dev;
	}
}
