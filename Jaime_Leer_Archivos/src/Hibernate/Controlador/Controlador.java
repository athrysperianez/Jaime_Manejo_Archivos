package Hibernate.Controlador;

import Hibernate.Vista.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import Hibernate.Modelo.*;
import Hibernate.Modelo.Modelo.queryType;

public class Controlador {
	private Modelo md;
	private Vista vc;

	public Controlador(Vista vc, Modelo md) {
		this.setVista(vc);
		this.setModelo(md);
	}

	public void setModelo(Modelo md) {
		this.md = md;
	}

	public void setVista(Vista yoH) {
		this.vc = yoH;
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
			break;
		case "3":
			md.lanzarQuery(queryType.SELECT, new Videojuego());
			break;
		case "4":
			vc.imprimir("Elija el archivo en el que desea descargar la base de datos");
			try {
				md.updateFile(vc.askData(), new Videojuego());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case "5":
			vc.imprimir("Elija el archivo en el que desea descargar la base de datos");
			md.updateDb(vc.askData(), new Videojuego());
			break;
		case "6":
			vc.imprimir("Introduzca el titulo");
			String titulo = vc.askData();
			vc.imprimir("Introduzca la descripcion");
			String descripcion = vc.askData();
			vc.imprimir("Introduzca el genero");
			String dato = vc.askData();
			vc.imprimir("Introduzca el id del desarrollador, se le buscara en la base de datos");
			String developer = vc.askData();
			Desarrollador dev = new Desarrollador(Integer.parseInt(developer), null, null);
			ArrayList<Datos> al = md.lanzarQuery(queryType.SELECT, dev);
			for(Datos x : al) {
				if(x.getId() == dev.getId()) {
					dev = (Desarrollador) x;
				}
			
			}
			md.ejecutarQuery(queryType.INSERT, new Videojuego((Integer) null, titulo, descripcion, dato, dev));
			
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
			System.exit(0);
			break;
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
			Desarrollador data = new Desarrollador((Integer) null, null, null);
			md.lanzarQuery(queryType.SELECT, data);
			break;
		case "4":
			vc.imprimir("Elija el archivo en el que desea descargar la base de datos");
			try {
				md.updateFile(vc.askData(), new Desarrollador());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case "5":
			vc.imprimir("Elija el archivo en el que desea descargar la base de datos");
			md.updateDb(vc.askData(), new Desarrollador());
			break;
		case "6":
			vc.imprimir("Introduzca el nombre");
			String nombre = vc.askData();
			vc.imprimir("Introduzca el tamaño");
			String tamaño = vc.askData();
			md.ejecutarQuery(queryType.INSERT, new Desarrollador((Integer) null, nombre, tamaño));
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
			System.exit(0);
			break;
		}
	}
	
	
}
