package Hibernate.Controlador;

import Hibernate.Vista.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import Estandar.Modelo.Desarrollador;
import Estandar.Modelo.Videojuego;
import Hibernate.Modelo.*;
import Hibernate.Modelo.Modelo.queryTipe;

public class Controlador {
	private Modelo md;
	private Vista vc;

	public Controlador(Vista vc, Modelo md) {
		this.setVista(vc);
		this.setModelo(md);
	}

	public void setModelo(Modelo md) {
		
	}

	public void setVista(Vista yoH) {
		
	}

	public Modelo getModelo() {
		return md;
	}

	public Vista getVista() {
		return vc;
	}

	public void select() {
		vc.imprimir("Introduzca dev si desea manejar los desarrolladores o vd si desea manejar los videojuegos");		
		String opcion = vc.askData();
		while (!opcion.equals("vd") && !opcion.equals("dev")) {
			System.out.println("Error, escoja una de las disponibles");
			opcion = "";
			opcion = vc.askData();
		}
		if (opcion.equals("vd")) {
			this.vd();
		}
		if (opcion.equals("dev")) {
			this.dev();
		}

	
		
	}
	public void vd() {
		System.out.println(
				"Introduzca 1 para leer el archivo.\nIntroduzca 2 para escribir en el.\nIntroduzca 3 para ver los datos de la base de datos.\nIntroduzca 4 para descargar los datos de la base de datos al fichero local.\nIntroduzca 5 para subir los datos del fichero a la base datos.\nIntroduzca 6 para añadir datos a la base de datos.\nIntroduzca 7 para cambiar la configuracion del programa.\nIntroduzca 8 para terminar el programa");
		switch (vc.askData()) {
		
		case "1":
			vc.imprimir("Introduzca el nombre del archivo que desea leer");
			md.setInput(vc.askData());
			HashMap<String, String> hs = md.getInput().leer();
			if (hs != null) {
				String data = hs.get("Datos del archivo");
				for (String x : data.split("·")) {
					Videojuego vg = new Videojuego(x, "@", null);
					vg.imprimir();
				}
			} else {
				vc.imprimir("Error al encontrar el archivo indicado\n");
			}
			break;
		case "2":
			//TODO Hacer opcion
		case "3":
			queryTipe SELECT = null;
			Datos data = null;
			md.lanzarQuery(SELECT, data);
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":	
		}
	}
	
	
	public void dev() {
		System.out.println(
				"Introduzca 1 para leer el archivo.\nIntroduzca 2 para escribir en el.\nIntroduzca 3 para ver los datos de la base de datos.\nIntroduzca 4 para descargar los datos de la base de datos al fichero local.\nIntroduzca 5 para subir los datos del fichero a la base datos.\nIntroduzca 6 para añadir datos a la base de datos.\nIntroduzca 7 para cambiar la configuracion del programa.\nIntroduzca 8 para terminar el programa");
		String campos[] = md.getConfig().getProperty("camposdb2").split(",");
		switch (vc.askData()) {
		
		case "1":
			vc.imprimir("Introduzca el nombre del archivo que desea leer");
			md.setInput(vc.askData());
			HashMap<String, String> hs = null;
			try {
				hs = md.getInput().leer();
			} catch (Exception e) {
				System.out.println("hola");
				e.printStackTrace();
			}
				
			if (hs != null) {
					String data = hs.get("Datos del archivo");
					for (String x : data.split("·")) {
						Desarrollador dev = new Desarrollador(x, "@");
						dev.imprimir();
					}
				} else {
					vc.imprimir("Error al encontrar el archivo indicado\n");
					}
			
			break;
		case "2":
			//TODO Revisar ese error, añadiendo el setOutput a modelo
			vc.imprimir("Escribe el archivo objetivo: ");
			md.setOutput(vc.askData());
			vc.imprimir("Introduzca id");
			String id = vc.askData();
			vc.imprimir("Introduzca el nombre");
			String dato1 = vc.askData();
			vc.imprimir("Introduzca la categoria");
			String dato2 = vc.askData();

			try {
				Desarrollador vg = new Desarrollador(Integer.parseInt(id), dato1, dato2);
				md.getOutput().escribir(vg.toProcesedString("@"), "·");
			} catch (NumberFormatException e) {
				vc.imprimir("El id debe ser un numero");
			}
			break;
		case "3":
			queryTipe SELECT = null;
			Datos data = null;
			md.lanzarQuery(SELECT, data);
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":	
		}
	}
	
	
}
