package Hibernate.Modelo;

/*
 *Creado por Elias Peria�ez
 *8 oct. 2018
 *Como parte del proyecto Jaime_Leer_Archivos
 *Este archivo esta bajo la licencia de Creative Commons Reconocimiento 4.0 Internacional (M�s informacion https://creativecommons.org/licenses/by/4.0/)
________________________________________________________________________________________________________________________________________________________
 *Created by Elias Peria�ez
 *8 oct. 2018
 *As part of the project Jaime_Leer_Archivos
 *This file is under the Creative Commons Attribution 4.0 International (More info here https://creativecommons.org/licenses/by/4.0/)
 */

public class Desarrollador implements Datos {

	private int id;
	private String nombre;
	private String tama�o;

	public Desarrollador(int id, String nombre, String tama�o) {
		this.id = id;
		this.nombre = nombre;
		this.tama�o = tama�o;
	}
	
	public Desarrollador() {
		
	}

	public Desarrollador(String data, String regex) {
		this(Integer.parseInt(data.split(regex)[0]), data.split(regex)[1], data.split(regex)[2]);
	}

	public boolean Comparador(Desarrollador ds) {
		boolean result = false;
		if (this.getId() == ds.getId() && this.getNombre() == ds.getNombre() && this.getTama�o() == ds.getTama�o()) {
			result = true;
		}

		return result;
	}

	public String imprimir() {
		return "Id: " + this.getId() + "\nNombre: " + this.getNombre() + "\nTama�o: " + this.getTama�o() + "\n";
		
	}
	
	public boolean compararId(Desarrollador dev) {
		boolean result = false;
		if(this.getId()==dev.getId()) {
			result = true;
		}
		
		return result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTama�o() {
		return tama�o;
	}

	public void setTama�o(String tama�o) {
		this.tama�o = tama�o;
	}

	public String toProcesedString(String string) {
		return this.getId() + string + this.getNombre() + string + this.getTama�o();
	}

	@Override
	public String toProcesedString(char separador, char separadorFinal) {
		return this.id + separador + this.nombre + separador + this.tama�o + separadorFinal;
	}

}
