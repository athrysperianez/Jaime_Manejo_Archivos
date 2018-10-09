import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import sun.security.krb5.internal.crypto.Des;

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
	
	//TODO Completar el puto metodo
	
	public void dev() {
		System.out.println("Introduzca 1 para leer el archivo.\nIntroduzca 2 para escribir en el.\nIntroduzca 3 para ver los datos de la base de datos.\nIntroduzca 4 para descargar los datos de la base de datos al fichero local.\nIntroduzca 5 para subir los datos del fichero a la base datos.\nIntroduzca 6 para añadir datos a la base de datos.\nIntroduzca 7 para cambiar la configuracion del programa.\nIntroduzca 8 para terminar el programa");
		
		switch (vc.askData()) {

		case "1":
			vc.imprimir("Introduzca el nombre del archivo que desea leer");
			md.setInput(vc.askData());
			HashMap<String, String> hs = md.getInput().leer();
			if (hs != null) {
				String data = hs.get("Datos del archivo");
				for (String x : data.split("·")) {
					//TODO Otro null
					Desarrollador dev = new Desarrollador(x, null);
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
			vc.imprimir("Introduzca el titulo");
			String dato1 = vc.askData();
			vc.imprimir("Introduzca el descripcion");
			String dato2 = vc.askData();
			vc.imprimir("Introduzca dato");
			String dato3 = vc.askData();
			vc.imprimir("Introduzca el desarrollador, estos son los desarrolladores disponibles:");
			String devData = md.callForInStream().leer().get("Datos del archivo");
			for(String x : devData.split("·")) {
				vc.imprimir(x.split("@")[1]+"\n");
			}
			String dev = vc.askData();
			try {
				//TODO Arregla este desastre porfavor
				Videojuego vg = new Videojuego(Integer.parseInt(id), dato1, dato2, dato3, null);
				md.getOutput().escribir(vg.toProcesedString("@"), "·");
			} catch (NumberFormatException e) {
				vc.imprimirErr("El id debe ser un numero");
			}
			break;
			

		case "3":
			String data = md.getConexion().ProcesarRset(
					md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tablaDs")), "@", "·");
			for (String x : data.split("·")) {
				//TODO null
				Desarrollador dev1 = new Desarrollador(x, null);
				dev1.imprimir();
			}

			break;
			

		case "4":
			vc.imprimir(
					"ATENCION Esto sustituira todos los datos del fichero local, introduzca \"Si\" si esta seguro de que desea proceder, introduzca cualquier otra cosa para cancelar");
			if (vc.askData().equals("Si")) {
				vc.imprimir("Introduzca el archivo en el que quiere que se escriban los datos");
				md.setOutput(vc.askData());
				md.getOutput().overwrite(md.getConexion().ProcesarRset(
						md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tablaDs")), "@", "·"));
				vc.imprimir("Se han sustituido los datos exitosamente\n");
			} else {
				vc.imprimir("Se ha cancelado el proceso\n");
			}
			break;

		case "5":
			
		case "6":
			

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
				md.updateConfig();				break;
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
				md.updateConfig();				break;
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
				md.updateConfig();				break;
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
					//TODO Otro null
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
			vc.imprimir("Introduzca el desarrollador, estos son los desarrolladores disponibles:");
			String devData = md.callForInStream().leer().get("Datos del archivo");
			for(String x : devData.split("·")) {
				vc.imprimir(x.split("@")[1]+"\n");
			}
			String dev = vc.askData();
			try {
				//TODO Arregla este desastre porfavor
				Videojuego vg = new Videojuego(Integer.parseInt(id), dato1, dato2, dato3, null);
				md.getOutput().escribir(vg.toProcesedString("@"), "·");
			} catch (NumberFormatException e) {
				vc.imprimirErr("El id debe ser un numero");
			}
			break;

		case "3":
			String data = md.getConexion().ProcesarRset(
					md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tablaVd")), "@", "·");
			for (String x : data.split("·")) {
				//TODO null
				Videojuego vg1 = new Videojuego(x, "@",null);
				vg1.imprimir();
			}

			break;

		case "4":
			vc.imprimir(
					"ATENCION Esto sustituira todos los datos del fichero local, introduzca \"Si\" si esta seguro de que desea proceder, introduzca cualquier otra cosa para cancelar");
			if (vc.askData().equals("Si")) {
				vc.imprimir("Introduzca el archivo en el que quiere que se escriban los datos");
				md.setOutput(vc.askData());
				md.getOutput().overwrite(md.getConexion().ProcesarRset(
						md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tablaVd")), "@", "·"));
				vc.imprimir("Se han sustituido los datos exitosamente\n");
			} else {
				vc.imprimir("Se ha cancelado el proceso\n");
			}
			break;

		case "5":
			vc.imprimir(
					"Si desea subir solo los datos nuevos introduzca 1, si desea sustituir todos los archivos de la base de datos introduzca 2, introduzca cualquier otro comando para cancelar");
			switch (vc.askData()) {
			case "1":
				String datosDb = md.getConexion().ProcesarRset(
						md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tablaVd")), "@", "·");
				vc.imprimir("Introduzca el nombre del archivo a leer");
				md.setInput(vc.askData());
				String datosTxt = md.getInput().leer().get("Datos del archivo");
				for (String x : datosTxt.split("·")) {
					boolean subir = true;
					//TODO null
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

			case "2":
				vc.imprimir(
						"ATENCION Esto sustituira todos los datos de la base de datos, introduzca \"Si\" si esta seguro de que desea proceder, introduzca cualquier otra cosa para cancelar");
				if (vc.askData().equals("Si")) {
					vc.imprimir("Introduzca el nombre del archivo a leer");
					md.setInput(vc.askData());
					String datificacion = md.getInput().leer().get("Datos del archivo");
					String ar[] = datificacion.split("·");

					md.getConexion().borrarFila(md.getConfig().getProperty("tablaVd"), "1");
					for (String arTmp : ar) {
						md.getConexion().insertarDatos(md.getConfig().getProperty("tablaVd"), campos, arTmp.split("@"));
					}
					vc.imprimir("Se han subido los datos correctamente\n");
				} else {
					vc.imprimir("Se ha cancelado el proceso\n");
				}
				break;
			default:
				vc.imprimir("Se cancelo la operacion");
				break;
			}

			break;

		case "6":
			String[] arCampos = { "Titulo", "Descripcion", "Dato" };
			vc.imprimir("Introduzca el titulo");
			String titulo = vc.askData();
			vc.imprimir("Introduzca la descripcion");
			String descripcion = vc.askData();
			vc.imprimir("Introduzca el dato");
			String dato = vc.askData();
			String[] arValues = { titulo, descripcion, dato };
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
				md.updateConfig();				break;
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
				md.updateConfig();				break;
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
				md.updateConfig();				break;
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

	}

	public void setVista(Vista vc) {

		this.vc = vc;
	}

	public void setModelo(Modelo md) {
		this.md = md;
	}

}
