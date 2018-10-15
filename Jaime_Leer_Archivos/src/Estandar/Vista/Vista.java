package Estandar.Vista;

import java.util.Scanner;
import Estandar.Controlador.*;
import Estandar.Modelo.*;

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
			cl.select();
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
