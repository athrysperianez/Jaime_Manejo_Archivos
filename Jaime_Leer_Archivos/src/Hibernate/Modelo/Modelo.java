package Hibernate.Modelo;

import Hibernate.Vista.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import Estandar.Modelo.in_data;


public class Modelo {
	private Vista vc;
	private Properties config;
	private in_data input;
	private out_data output;
	private Session s;
	public static enum queryTipe {SELECT, DELETE, INSERT, UPDATE};
	
	public Modelo(Vista vc) throws FileNotFoundException {
		this.vc = vc;
		this.startUp();
	}
	
	private void startUp() throws FileNotFoundException {
		s = new Configuration().configure().buildSessionFactory().openSession();
		this.initConfig();
	}

	private void initConfig() throws FileNotFoundException {
		this.input = new in_data("config.ini");
		this.setConfig(input.startupConfig());
		this.input.destroyStream();
	}
	
	private void beginSession() {
		if(this.s.getTransaction()==null) {
			s.beginTransaction();
		}
	}
	
	public void kill() {
		this.s.close();
		this.input.destroyStream();
	}
	
	public void uploadData(Object obj) {
		this.beginSession();
		s.save(obj);
	}
	
	public ArrayList<Datos> lanzarQuery(queryTipe qt){
		
		
		return null;
		
	}
	
	public ArrayList<Datos> recuperarTodos(Datos e){
		ArrayList<Datos> result = new ArrayList<Datos>();
		Query q = s.createQuery("Select e from "+ e.getClass() +" e");
		Iterator pe�ita = q.list().iterator();
		while (pe�ita.hasNext()) {
			result.add((Datos) pe�ita.next());
		}
		return null;
	}
	
	public void closeStreams() {
		input.destroyStream();
		s.cancelQuery();
		s.close();
	}
	//Setters y getters
	public Vista getVc() {
		return vc;
	}

	public void setVc(Vista vc) {
		this.vc = vc;
	}

	public Properties getConfig() {
		return config;
	}

	public void setConfig(Properties config) {
		this.config = config;
	}

	public out_data getOutput() {
		return output;
	}

	public void setOutput(out_data output) {
		this.output = output;
	}

	public void setVista(Vista yoH) {
		// TODO Ap�ndice de m�todo generado autom�ticamente
		
	}
	
}


