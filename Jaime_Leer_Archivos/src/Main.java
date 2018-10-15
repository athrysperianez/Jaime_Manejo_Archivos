import java.sql.Connection;
import java.sql.DriverManager;
import Estandar.*;
import Hibernate.*;

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

public class Main {
		//TODO Interfaces
	public static void main(String[] args) {
		
		Estandar.Controlador.Controlador cl = new Estandar.Controlador.Controlador(null, null);
		Estandar.Vista.Vista yo = new Estandar.Vista.Vista(null, null);
		Estandar.Modelo.Modelo md = new Estandar.Modelo.Modelo(yo);
		yo.setControlador(cl);
		yo.setModelo(md);
		cl.setVista(yo);
		cl.setModelo(md);
		md.setVista(yo);
		yo.menuLoop();
	}
}
