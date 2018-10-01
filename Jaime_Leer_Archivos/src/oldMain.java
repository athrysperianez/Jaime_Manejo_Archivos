import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

/*
 *Creado por Elias Periañez
 *17 sept. 2018
 *Como parte del proyecto Jaime_Leer_Archivos
 *Este archivo esta bajo la licencia de Creative Commons Reconocimiento 4.0 Internacional (Más informacion https://creativecommons.org/licenses/by/4.0/)
________________________________________________________________________________________________________________________________________________________
 *Created by Elias Periañez
 *17 sept. 2018
 *As part of the project Jaime_Leer_Archivos
 *This file is under the Creative Commons Attribution 4.0 International (More info here https://creativecommons.org/licenses/by/4.0/)
 */

public class oldMain {

	//TODO Orientacion a objetos
	//TODO MVC
	//TODO Interfaces
	public static void uwu(String[] args) {
		Scanner sc = new Scanner(System.in);
		Boolean exit = false;
		in_data config_in = new in_data("config.ini");
		Properties config = config_in.startupConfig();
		config_in.destroyStream();
		String campos[] = null;
		String tabla = null;
		try {
			System.out.println("Se va a cargar la configuracion");
			campos = config.getProperty("camposdb").split(",");
		} catch (NullPointerException e) {
			System.err.println("Error en el archivo de configuracion, esta corrupto o no se leyo de forma correcta");
			System.exit(-1);
		}
		while (!exit) {
			System.out.println(
					"Introduzca 1 para leer el archivo.\nIntroduzca 2 para escribir en el.\nIntroduzca 3 para ver los datos de la base de datos.\nIntroduzca 4 para descargar los datos de la base de datos al fichero local.\nIntroduzca 5 para subir los datos del fichero a la base datos.\nIntroduzca 6 para añadir datos a la base de datos.\nIntroduzca 7 para cambiar la configuracion del programa.\nIntroduzca 8 para terminar el programa");
			Conexion con = new Conexion(config.getProperty("db"), config.getProperty("user"), config.getProperty("psw"),
					false, config.getProperty("target"));
			out_data out;
			in_data in;
			switch (sc.nextLine()) {

			case "1":
				System.out.println("Introduzca el nombre del archivo que desea leer");
				in = new in_data(sc.nextLine());
				HashMap<String, String> hs = in.leer();
				if (hs != null) {
					String data = hs.get("Datos del archivo");
					for (String x : data.split("·")) {
						Videojuego vg = new Videojuego(x, "@");
						vg.imprimir();
					}
				} else {
					System.err.println("Error al encontrar el archivo indicado\n");
				}
				break;

			case "2":
				System.out.println("Escribe el archivo objetivo: ");
				out = new out_data(sc.nextLine());
				System.out.println("Introduzca id");
				String id = sc.nextLine();
				System.out.println("Introduzca el titulo");
				String dato1 = sc.nextLine();
				System.out.println("Introduzca el descripcion");
				String dato2 = sc.nextLine();
				System.out.println("Introduzca dato");
				String dato3 = sc.nextLine();
				try {
					Videojuego vg = new Videojuego(Integer.parseInt(id), dato1, dato2, dato3);
					out.escribir(vg.toProcesedString("@"), "·");
				} catch (NumberFormatException e) {
					System.err.println("El id debe ser un numero");
				}
				break;

			case "3":
				String data = con.ProcesarRset(con.Consulta("SELECT * FROM " + config.getProperty("tabla")), "@", "·");//TODO Cambiar la seleccion de tabla para que sea desde el archivo .ini
				for (String x : data.split("·")) {
					Videojuego vg1 = new Videojuego(x, "@");
					vg1.imprimir();
				}

				break;

			case "4":
				System.out.println(
						"ATENCION Esto sustituira todos los datos del fichero local, introduzca \"Si\" si esta seguro de que desea proceder, introduzca cualquier otra cosa para cancelar");
				if (sc.nextLine().equals("Si")) {
					System.out.println("Introduzca el archivo en el que quiere que se escriban los datos");
					out = new out_data(sc.nextLine());
					out.overwrite(con.ProcesarRset(con.Consulta("SELECT * FROM " + config.getProperty("tabla")), "@", "·"));
					System.out.println("Se han sustituido los datos exitosamente\n");
				} else {
					System.out.println("Se ha cancelado el proceso\n");
				}
				break;

			case "5":
				System.out.println(
						"Si desea subir solo los datos nuevos introduzca 1, si desea sustituir todos los archivos de la base de datos introduzca 2, introduzca cualquier otro comando para cancelar");
				switch (sc.nextLine()) {
				case "1":
					String datosDb = con.ProcesarRset(con.Consulta("SELECT * FROM " + config.getProperty("tabla")), "@", "·");
					System.out.println("Introduzca el nombre del archivo a leer");
					in = new in_data(sc.nextLine());
					String datosTxt = in.leer().get("Datos del archivo");
					for (String x : datosTxt.split("·")) {
						boolean subir = true;
						Videojuego vg1 = new Videojuego(x, "@");
						for (String y : datosDb.split("·")) {
							Videojuego vg2 = new Videojuego(y, "@");
							if (vg1.compararId(vg2)) {
								subir = false;
							}
						}
						if (subir) {
							con.insertarDatos(config.getProperty("tabla"), campos, vg1.toProcesedString("·").split("·"));
						}
					}
					break;

				case "2":
					System.out.println(
							"ATENCION Esto sustituira todos los datos de la base de datos, introduzca \"Si\" si esta seguro de que desea proceder, introduzca cualquier otra cosa para cancelar");
					if (sc.nextLine().equals("Si")) {
						System.out.println("Introduzca el nombre del archivo a leer");
						in = new in_data(sc.nextLine());
						String datificacion = in.leer().get("Datos del archivo");
						String ar[] = datificacion.split("·");

						con.borrarFila(config.getProperty("tabla"), "1");
						for (String arTmp : ar) {
							con.insertarDatos(config.getProperty("tabla"), campos, arTmp.split("@"));
						}
						System.out.println("Se han subido los datos correctamente\n");
					} else {
						System.out.println("Se ha cancelado el proceso\n");
					}
					break;
				default:
					System.out.println("Se cancelo la operacion");
					break;
				}

				break;

			case "6":
				String[] arCampos = { "Titulo", "Descripcion", "Dato" };
				System.out.println("Introduzca el titulo");
				String titulo = sc.nextLine();
				System.out.println("Introduzca la descripcion");
				String descripcion = sc.nextLine();
				System.out.println("Introduzca el dato");
				String dato = sc.nextLine();
				String[] arValues = { titulo, descripcion, dato };
				con.insertarDatos(config.getProperty("tabla"), arCampos, arValues);
				System.out.println("Se insertaron los datos correctamente \n");
				break;
				
			case "7":
				System.out.println("\n La configuracion actual es la siguiente: ");
				System.out.println("1.Base de datos: " + config.getProperty("db"));
				System.out.println("2.Usuario de la base de datos: " + config.getProperty("user"));
				System.out.println("3.Contraseña de la base de datos: " + config.getProperty("psw"));
				System.out.println("4.Servidor: " + config.getProperty("target"));
				System.out.println("5.Campos de la tabla objetivo: " + config.getProperty("camposdb"));
				//TODO Añadir menu de configuracion
				System.out.println("\n Introduzca un numero asociado a una configuracion para introducir un nuevo valor, si desea salir introduzca cualquier otro comando");
				
				break;
				
			case "8":
				exit = true;
				break;
			default:
				System.out.println("Error, no se reconocio la orden, introduzcala de nuevo\n");
				break;
			}
		}
		sc.close();
		System.exit(0);
	}

}
