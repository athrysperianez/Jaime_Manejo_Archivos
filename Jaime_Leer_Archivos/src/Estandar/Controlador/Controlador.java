package Estandar.Controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import Estandar.Vista.*;
import Estandar.Modelo.*;

/*
 *Creado por Elias Periañez
 *8 oct. 2018
 *Como parte del proyecto Jaime_Leer_Archivos
 *Este archivo esta bajo la licencia de Creative Commons Reconocimiento 4.0 Internacional (Más informacion https://creativecommons.org/licenses/by/4.0/)
________________________________________________________________________________________________________________________________________________________
 *Created by Elias Periañez
 *8 oct. 2018
 *As part of the project Jaime_Leer_Archivos
 *This file is under the Creative Commons Attribution 4.0 International (More info here https://creativecommons.org/licenses/by/4.0/)
 */

public class Controlador {

	private Modelo md;
	private Vista vc;

	public Controlador(Vista vc, Modelo md) {
		this.vc = vc;
		this.md = md;
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

	// TODO Completar el puto metodo

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
					vc.imprimirErr("Error al encontrar el archivo indicado\n");
					}
			
			break;

		case "2":

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
				vc.imprimirErr("El id debe ser un numero");
			}
			break;

		case "3":
			String data = md.getConexion().procesarRset(
					md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tablaDs")), "@", "·");
			for (String x : data.split("·")) {
				Desarrollador dev1 = new Desarrollador(x, "@");
				dev1.imprimir();
			}

			break;

		case "4":
			vc.imprimir(
					"ATENCION Esto sustituira todos los datos del fichero local, introduzca \"Si\" si esta seguro de que desea proceder, introduzca cualquier otra cosa para cancelar");
			if (vc.askData().equals("Si")) {
				vc.imprimir("Introduzca el archivo en el que quiere que se escriban los datos");
				md.setOutput(vc.askData());
				md.getOutput().overwrite(md.getConexion().procesarRset(
						md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tablaDs")), "@", "·"));
				vc.imprimir("Se han sustituido los datos exitosamente\n");
			} else {
				vc.imprimir("Se ha cancelado el proceso\n");
			}
			break;

		case "5":
			
			String datosDb = md.getConexion().procesarRset(
					md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tablaDs")), "@", "·");
			vc.imprimir("Introduzca el nombre del archivo a leer");
			md.setInput(vc.askData());
			String datosTxt = md.getInput().leer().get("Datos del archivo");
			for (String x : datosTxt.split("·")) {
				boolean subir = true;
				Desarrollador dev1 = new Desarrollador(x, "@");
				for (String y : datosDb.split("·")) {
					Desarrollador dev2 = new Desarrollador(y, "@");
					if (dev1.compararId(dev2)) {
						subir = false;
					}
				}
				if (subir) {
					md.getConexion().insertarDatos(md.getConfig().getProperty("tablaDs"), campos,
							dev1.toProcesedString("·").split("·"));
				}
			}
			break;
			

		case "6":
			String[] arCampos = { "Nombre", "Categoria" };
			vc.imprimir("Introduzca el nombre");
			String nombre = vc.askData();
			vc.imprimir("Introduzca la categoria");
			String categoria = vc.askData();
			String[] arValues = { nombre, categoria };
			md.getConexion().insertarDatos(md.getConfig().getProperty("tablaDs"), arCampos, arValues);
			vc.imprimir("Se insertaron los datos correctamente \n");
			break;

		case "7":
			vc.imprimir("\nLa configuracion actual es la siguiente: ");
			vc.imprimir("1.Base de datos: " + md.getConfig().getProperty("db"));
			vc.imprimir("2.Usuario de la base de datos: " + md.getConfig().getProperty("user"));
			vc.imprimir("3.Contraseña de la base de datos: " + md.getConfig().getProperty("psw"));
			vc.imprimir("4.Servidor: " + md.getConfig().getProperty("target"));
			vc.imprimir("5.Campos de la tablaDs objetivo: " + md.getConfig().getProperty("camposdb2"));
			// TODO Añadir menu de configuracion
			vc.imprimir(
					"\nIntroduzca un numero asociado a una configuracion para introducir un nuevo valor, si desea salir introduzca cualquier otro comando");
			switch (vc.askData()) {
			case "1":
				vc.imprimir("Introduzca la nueva configuracion");
				md.getConfig().setProperty("db", vc.askData());
				md.setOutput("config.ini");
				try {
					md.modifyConfig("config.ini");
				} catch (FileNotFoundException e) {
					vc.imprimirErr("Error, no se encontro el archivo, vuelvelo a intentar");
				} catch (IOException e) {
					vc.imprimirErr("Error IO, contanta con el administrador del sistema o intentalo de nuevo");
				}
				vc.imprimir("Configuracion cambiada, regresando al menu principal y recargando la configuracion\n");
				md.updateConfig();
				break;
			case "2":
				vc.imprimir("Introduzca la nueva configuracion");
				md.getConfig().setProperty("user", vc.askData());
				md.setOutput("config.ini");
				try {
					md.modifyConfig("config.ini");
				} catch (FileNotFoundException e) {
					vc.imprimirErr("Error, no se encontro el archivo, vuelvelo a intentar");
				} catch (IOException e) {
					vc.imprimirErr("Error IO, contanta con el administrador del sistema o intentalo de nuevo");
				}
				vc.imprimir("Configuracion cambiada, regresando al menu principal y recargando la configuracion\n");
				md.updateConfig();
				break;
			case "3":
				vc.imprimir("Introduzca la nueva configuracion");
				md.getConfig().setProperty("psw", vc.askData());
				md.setOutput("config.ini");
				try {
					md.modifyConfig("config.ini");
				} catch (FileNotFoundException e) {
					vc.imprimirErr("Error, no se encontro el archivo, vuelvelo a intentar");
				} catch (IOException e) {
					vc.imprimirErr("Error IO, contanta con el administrador del sistema o intentalo de nuevo");
				}
				vc.imprimir("Configuracion cambiada, regresando al menu principal y recargando la configuracion\n");
				md.updateConfig();
				break;
			case "4":
				vc.imprimir("Introduzca la nueva configuracion");
				md.getConfig().setProperty("target", vc.askData());
				md.setOutput("config.ini");
				try {
					md.modifyConfig("config.ini");
				} catch (FileNotFoundException e) {
					vc.imprimirErr("Error, no se encontro el archivo, vuelvelo a intentar");
				} catch (IOException e) {
					vc.imprimirErr("Error IO, contanta con el administrador del sistema o intentalo de nuevo");
				}
				vc.imprimir("Configuracion cambiada, regresando al menu principal y recargando la configuracion\n");
				md.updateConfig();
				break;
			case "5":
				vc.imprimir("Introduzca la nueva configuracion");
				md.getConfig().setProperty("camposdb2", vc.askData());
				md.setOutput("config.ini");
				try {
					md.modifyConfig("config.ini");
				} catch (FileNotFoundException e) {
					vc.imprimirErr("Error, no se encontro el archivo, vuelvelo a intentar");
				} catch (IOException e) {
					vc.imprimirErr("Error IO, contanta con el administrador del sistema o intentalo de nuevo");
				}
				vc.imprimir("Configuracion cambiada, regresando al menu principal y recargando la configuracion\n");
				md.updateConfig();
				break;

			default:
				vc.imprimir("No se ha reconocido el comando, regresando al menu\n");
				break;
			}

			break;

		case "8":

			break;
		default:
			vc.imprimir("Error, no se reconocio la orden, introduzcala de nuevo\n");
			break;
		}
		this.select();
	}

	public void vd() {
		System.out.println(
				"Introduzca 1 para leer el archivo.\nIntroduzca 2 para escribir en el.\nIntroduzca 3 para ver los datos de la base de datos.\nIntroduzca 4 para descargar los datos de la base de datos al fichero local.\nIntroduzca 5 para subir los datos del fichero a la base datos.\nIntroduzca 6 para añadir datos a la base de datos.\nIntroduzca 7 para cambiar la configuracion del programa.\nIntroduzca 8 para terminar el programa");
		String campos[] = md.getConfig().getProperty("camposdb").split(",");
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
				vc.imprimirErr("Error al encontrar el archivo indicado\n");
			}
			break;

		case "2":
			vc.imprimir("Escribe el archivo objetivo: ");
			md.setOutput(vc.askData());
			vc.imprimir("Introduzca id");
			String id = vc.askData();
			vc.imprimir("Introduzca el titulo");
			String dato1 = vc.askData();
			vc.imprimir("Introduzca el descripcion");
			String dato2 = vc.askData();
			vc.imprimir("Introduzca dato");
			String dato3 = vc.askData();
			vc.imprimir("Introduzca el numero del desarrollador, estos son los desarrolladores disponibles:");
			String devData = md.callForInStream().leer().get("Datos del archivo");
			int index = 0;
			for (String x : devData.split("·")) {
				vc.imprimir(index + ". " + x.split("@")[1] + "\n");
				index++;
			}
			Desarrollador dev = new Desarrollador(devData.split("·")[Integer.parseInt(vc.askData())], "@");
			if (new Orwell(dev, md.callForInStream().getStream(), md.getConexion().getConexion()).askBigBrother()) {
				try {
					Videojuego vg = new Videojuego(Integer.parseInt(id), dato1, dato2, dato3, devData);
					md.getOutput().escribir(vg.toProcesedString("@"), "·");
				} catch (NumberFormatException e) {
					vc.imprimirErr("El id debe ser un numero");
				}
			} else {
				System.err.println("El desarrollador no existe, crealo primero o usa otro");
			}

			break;

		case "3":
			String dataCase3 = md.getConexion().procesarRset(
					md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tablaVd")), "@", "·");
			for (String x : dataCase3.split("·")) {
				Videojuego vd1 = new Videojuego(x, "@", null);
				vd1.imprimir();
			}

			break;

		case "4":
			vc.imprimir(
					"ATENCION Esto sustituira todos los datos del fichero local, introduzca \"Si\" si esta seguro de que desea proceder, introduzca cualquier otra cosa para cancelar");
			if (vc.askData().equals("Si")) {
				vc.imprimir("Introduzca el archivo en el que quiere que se escriban los datos");
				md.setOutput(vc.askData());
				md.getOutput().overwrite(md.getConexion().procesarRset(
						md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tablaVd")), "@", "·"));
				vc.imprimir("Se han sustituido los datos exitosamente\n");
			} else {
				vc.imprimir("Se ha cancelado el proceso\n");
			}
			break;

		case "5":
				String datosDb = md.getConexion().procesarRset(
						md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tablaVd")), "@", "·");
				vc.imprimir("Introduzca el nombre del archivo a leer");
				md.setInput(vc.askData());
				String datosTxt = md.getInput().leer().get("Datos del archivo");
				for (String x : datosTxt.split("·")) {
					boolean subir = true;
					Videojuego vg1 = new Videojuego(x, "@", null);
					for (String y : datosDb.split("·")) {
						Videojuego vg2 = new Videojuego(y, "@", null);
						if (vg1.compararId(vg2)) {
							subir = false;
						}
					}
					if (subir) {
						md.getConexion().insertarDatos(md.getConfig().getProperty("tablaVd"), campos,
								vg1.toProcesedString("·").split("·"));
					}
				}
				break;

		case "6":
			String[] arCampos = { "Titulo", "Descripcion", "Genero", "Desarrolladora"};
			vc.imprimir("Introduzca el titulo");
			String titulo = vc.askData();
			vc.imprimir("Introduzca la descripcion");
			String descripcion = vc.askData();
			vc.imprimir("Introduzca el genero");
			String dato = vc.askData();
			vc.imprimir("Introduzca el desarrollador");
			String developer = vc.askData();
			String[] arValues = { titulo, descripcion, dato, developer };
			md.getConexion().insertarDatos(md.getConfig().getProperty("tablaVd"), arCampos, arValues);
			vc.imprimir("Se insertaron los datos correctamente \n");
			break;

		case "7":
			vc.imprimir("\nLa configuracion actual es la siguiente: ");
			vc.imprimir("1.Base de datos: " + md.getConfig().getProperty("db"));
			vc.imprimir("2.Usuario de la base de datos: " + md.getConfig().getProperty("user"));
			vc.imprimir("3.Contraseña de la base de datos: " + md.getConfig().getProperty("psw"));
			vc.imprimir("4.Servidor: " + md.getConfig().getProperty("target"));
			vc.imprimir("5.Campos de la tablaVd objetivo: " + md.getConfig().getProperty("camposdb"));
			// TODO Añadir menu de configuracion
			vc.imprimir(
					"\nIntroduzca un numero asociado a una configuracion para introducir un nuevo valor, si desea salir introduzca cualquier otro comando");
			switch (vc.askData()) {
			case "1":
				vc.imprimir("Introduzca la nueva configuracion");
				md.getConfig().setProperty("db", vc.askData());
				md.setOutput("config.ini");
				try {
					md.modifyConfig("config.ini");
				} catch (FileNotFoundException e) {
					vc.imprimirErr("Error, no se encontro el archivo, vuelvelo a intentar");
				} catch (IOException e) {
					vc.imprimirErr("Error IO, contanta con el administrador del sistema o intentalo de nuevo");
				}
				vc.imprimir("Configuracion cambiada, regresando al menu principal y recargando la configuracion\n");
				md.updateConfig();
				break;
			case "2":
				vc.imprimir("Introduzca la nueva configuracion");
				md.getConfig().setProperty("user", vc.askData());
				md.setOutput("config.ini");
				try {
					md.modifyConfig("config.ini");
				} catch (FileNotFoundException e) {
					vc.imprimirErr("Error, no se encontro el archivo, vuelvelo a intentar");
				} catch (IOException e) {
					vc.imprimirErr("Error IO, contanta con el administrador del sistema o intentalo de nuevo");
				}
				vc.imprimir("Configuracion cambiada, regresando al menu principal y recargando la configuracion\n");
				md.updateConfig();
				break;
			case "3":
				vc.imprimir("Introduzca la nueva configuracion");
				md.getConfig().setProperty("psw", vc.askData());
				md.setOutput("config.ini");
				try {
					md.modifyConfig("config.ini");
				} catch (FileNotFoundException e) {
					vc.imprimirErr("Error, no se encontro el archivo, vuelvelo a intentar");
				} catch (IOException e) {
					vc.imprimirErr("Error IO, contanta con el administrador del sistema o intentalo de nuevo");
				}
				vc.imprimir("Configuracion cambiada, regresando al menu principal y recargando la configuracion\n");
				md.updateConfig();
				break;
			case "4":
				vc.imprimir("Introduzca la nueva configuracion");
				md.getConfig().setProperty("target", vc.askData());
				md.setOutput("config.ini");
				try {
					md.modifyConfig("config.ini");
				} catch (FileNotFoundException e) {
					vc.imprimirErr("Error, no se encontro el archivo, vuelvelo a intentar");
				} catch (IOException e) {
					vc.imprimirErr("Error IO, contanta con el administrador del sistema o intentalo de nuevo");
				}
				vc.imprimir("Configuracion cambiada, regresando al menu principal y recargando la configuracion\n");
				md.updateConfig();
				break;
			case "5":
				vc.imprimir("Introduzca la nueva configuracion");
				md.getConfig().setProperty("camposdb", vc.askData());
				md.setOutput("config.ini");
				try {
					md.modifyConfig("config.ini");
				} catch (FileNotFoundException e) {
					vc.imprimirErr("Error, no se encontro el archivo, vuelvelo a intentar");
				} catch (IOException e) {
					vc.imprimirErr("Error IO, contanta con el administrador del sistema o intentalo de nuevo");
				}
				vc.imprimir("Configuracion cambiada, regresando al menu principal y recargando la configuracion\n");
				md.updateConfig();
				break;

			default:
				vc.imprimir("No se ha reconocido el comando, regresando al menu\n");
				break;
			}

			break;

		case "8":

			break;
		default:
			vc.imprimir("Error, no se reconocio la orden, introduzcala de nuevo\n");
			break;
		}
		this.select();
	}

	public void setVista(Vista vc) {

		this.vc = vc;
	}

	public void setModelo(Modelo md) {
		this.md = md;
	}

}
