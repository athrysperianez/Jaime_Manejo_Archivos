import java.util.Scanner;

/*
 *Creado por Elias Periañez
 *1 oct. 2018
 *Como parte del proyecto Jaime_Leer_Archivos
 *Este archivo esta bajo la licencia de Creative Commons Reconocimiento 4.0 Internacional (Más informacion https://creativecommons.org/licenses/by/4.0/)
________________________________________________________________________________________________________________________________________________________
 *Created by Elias Periañez
 *1 oct. 2018
 *As part of the project Jaime_Leer_Archivos
 *This file is under the Creative Commons Attribution 4.0 International (More info here https://creativecommons.org/licenses/by/4.0/)
 */

public class Vista {
	Scanner sc;
	Controlador cl;
	Modelo md;

	public Vista(Controlador cl, Modelo md) {
		this.sc = new Scanner(System.in);
		this.cl = cl;
	}

	public void setControlador(Controlador cl) {
		this.cl = cl;
	}

	public void setModelo(Modelo md) {
		this.md = md;
	}

	public void imprimir(String s) {
		System.out.println(s);
	}

	public void imprimirErr(String s) {
		System.err.println(s);
	}

	public void menuLoop() {
		while (true) {
			System.out.println(
					"Introduzca 1 para leer el archivo.\nIntroduzca 2 para escribir en el.\nIntroduzca 3 para ver los datos de la base de datos.\nIntroduzca 4 para descargar los datos de la base de datos al fichero local.\nIntroduzca 5 para subir los datos del fichero a la base datos.\nIntroduzca 6 para añadir datos a la base de datos.\nIntroduzca 7 para cambiar la configuracion del programa.\nIntroduzca 8 para terminar el programa");
			cl.select(sc.nextLine());
		}
	}
	
	public void loopBreak() {
		sc.close();
		md.closeStreams();
		System.exit(0);
	}
	
	public String askData() {
		return sc.nextLine();
	}
}
