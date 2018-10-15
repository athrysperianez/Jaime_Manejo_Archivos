package Estandar.Modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import Estandar.Vista.Vista;
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

public class Modelo {

	private Vista vc;
	private Properties config;
	private Conexion con;
	private in_data input;
	private out_data output;

	// Metodos de inicializacion
	public Modelo(Vista vc) {
		this.setVista(vc);
		this.startUp();
	}

	private void startUp() {
		this.initConfig();
		this.setConexion(config.getProperty("db"), config.getProperty("user"), config.getProperty("psw"), false,
				config.getProperty("target"));
	}

	private void initConfig() {
		this.input = new in_data("config.ini");
		this.config = input.startupConfig();
		this.input.destroyStream();
	}

	// Getters y setters
	public Vista getVista() {
		return vc;
	}

	public void setVista(Vista vc) {
		this.vc = vc;
	}

	public Conexion getConexion() {
		return con;
	}

	public void setConexion(String db, String user, String pass, Boolean localhost, String server) {
		try {
			this.con.kill();
		} catch (NullPointerException e) {};
		this.con = new Conexion(db, user, pass, localhost, server);
	}

	public in_data callForInStream() {
		return input;
	}

	public out_data callForOutStream() {
		return output;
	}
	//Se que estos getter estan repetidos, pero me gusta poder llamarlo de ambas maneras, manias mias.
	public in_data getInput() {
		if (this.input==null) {
			this.input.destroyStream();
		}
		return this.input;
	}

	public out_data getOutput() {
		return this.output;
	}

	public void setInput(String file) {
		if (this.input==null) {
			this.input.destroyStream();
		}
		this.input = new in_data(file);
	}

	public void setOutput(String file) {
		this.output = new out_data(file);
	}

	public Properties getConfig() {
		return this.config;
	}

	// Procesos
	public void closeStreams() {
		input.destroyStream();
		con.kill();
	}

	public void updateConfig() {
		this.initConfig();
	}
	
	public void modifyConfig(String file) throws FileNotFoundException, IOException {
		this.config.store(output.createStream(file), null);
	}
}
