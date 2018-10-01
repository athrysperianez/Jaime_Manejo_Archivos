import java.util.HashMap;

public class Controlador {

	private Modelo md;
	private Vista vc;
	
	public Controlador(Vista vc, Modelo md) {
		this.vc = vc;
		this.md = md;
	}
	
	public void select(String scData) {
		String campos[] = md.getConfig().getProperty("camposdb").split(",");
		switch (vc.askData()) {

		case "1":
			vc.imprimir("Introduzca el nombre del archivo que desea leer");
			md.getInput().setFile(vc.askData());
			HashMap<String, String> hs = md.getInput().leer();
			if (hs != null) {
				String data = hs.get("Datos del archivo");
				for (String x : data.split("·")) {
					Videojuego vg = new Videojuego(x, "@");
					vg.imprimir();
				}
			} else {
				vc.imprimirErr("Error al encontrar el archivo indicado\n");
			}
			break;

		case "2":
			vc.imprimir("Escribe el archivo objetivo: ");
			md.getOutput().setFile(vc.askData());
			vc.imprimir("Introduzca id");
			String id = vc.askData();
			vc.imprimir("Introduzca el titulo");
			String dato1 = vc.askData();
			vc.imprimir("Introduzca el descripcion");
			String dato2 = vc.askData();
			vc.imprimir("Introduzca dato");
			String dato3 = vc.askData();
			try {
				Videojuego vg = new Videojuego(Integer.parseInt(id), dato1, dato2, dato3);
				md.getOutput().escribir(vg.toProcesedString("@"), "·");
			} catch (NumberFormatException e) {
				vc.imprimirErr("El id debe ser un numero");
			}
			break;

		case "3":
			String data = md.getConexion().ProcesarRset(md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tabla")), "@", "·");//TODO Cambiar la seleccion de tabla para que sea desde el archivo .ini
			for (String x : data.split("·")) {
				Videojuego vg1 = new Videojuego(x, "@");
				vg1.imprimir();
			}

			break;

		case "4":
			vc.imprimir(
					"ATENCION Esto sustituira todos los datos del fichero local, introduzca \"Si\" si esta seguro de que desea proceder, introduzca cualquier otra cosa para cancelar");
			if (vc.askData().equals("Si")) {
				vc.imprimir("Introduzca el archivo en el que quiere que se escriban los datos");
				md.getOutput().setFile(vc.askData());
				md.getOutput().overwrite(md.getConexion().ProcesarRset(md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tabla")), "@", "·"));
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
				String datosDb = md.getConexion().ProcesarRset(md.getConexion().Consulta("SELECT * FROM " + md.getConfig().getProperty("tabla")), "@", "·");
				vc.imprimir("Introduzca el nombre del archivo a leer");
				md.getInput().setFile(vc.askData());
				String datosTxt = md.getInput().leer().get("Datos del archivo");
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
						md.getConexion().insertarDatos(md.getConfig().getProperty("tabla"), campos, vg1.toProcesedString("·").split("·"));
					}
				}
				break;

			case "2":
				vc.imprimir(
						"ATENCION Esto sustituira todos los datos de la base de datos, introduzca \"Si\" si esta seguro de que desea proceder, introduzca cualquier otra cosa para cancelar");
				if (vc.askData().equals("Si")) {
					vc.imprimir("Introduzca el nombre del archivo a leer");
					md.getInput().setFile(vc.askData());
					String datificacion = md.getInput().leer().get("Datos del archivo");
					String ar[] = datificacion.split("·");

					md.getConexion().borrarFila(md.getConfig().getProperty("tabla"), "1");
					for (String arTmp : ar) {
						md.getConexion().insertarDatos(md.getConfig().getProperty("tabla"), campos, arTmp.split("@"));
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
			md.getConexion().insertarDatos(md.getConfig().getProperty("tabla"), arCampos, arValues);
			vc.imprimir("Se insertaron los datos correctamente \n");
			break;
			
		case "7":
			vc.imprimir("\n La configuracion actual es la siguiente: ");
			vc.imprimir("1.Base de datos: " + md.getConfig().getProperty("db"));
			vc.imprimir("2.Usuario de la base de datos: " + md.getConfig().getProperty("user"));
			vc.imprimir("3.Contraseña de la base de datos: " + md.getConfig().getProperty("psw"));
			vc.imprimir("4.Servidor: " + md.getConfig().getProperty("target"));
			vc.imprimir("5.Campos de la tabla objetivo: " + md.getConfig().getProperty("camposdb"));
			//TODO Añadir menu de configuracion
			vc.imprimir("\n Introduzca un numero asociado a una configuracion para introducir un nuevo valor, si desea salir introduzca cualquier otro comando");
			
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
