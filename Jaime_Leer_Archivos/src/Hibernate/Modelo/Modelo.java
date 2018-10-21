package Hibernate.Modelo;

import Hibernate.Vista.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.mysql.cj.util.EscapeTokenizer;

import Estandar.Modelo.in_data;
import Estandar.Modelo.out_data;


public class Modelo {
	private Vista vc;
	private Properties config;
	private in_data input;
	private out_data output;
	private Session s;
	public static enum queryTipe{SELECT, DELETE, INSERT, UPDATE};
	
	public Modelo(Vista vc) throws FileNotFoundException {
		this.vc = vc;
		this.startUp();
	}
	
	private void startUp() throws FileNotFoundException {
		s = new Configuration().configure("/Hibernate/Modelo/hibernate.cfg.xml").buildSessionFactory().openSession();
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
	
	private void update(Datos e) {
		this.beginSession();
		s.update(e);
	}
	
	private void delete(Datos e) {
		this.beginSession();
		s.delete(e);
	}
	
	public void kill() {
		this.s.close();
		this.input.destroyStream();
	}
	
	public ArrayList<Datos> lanzarQuery(queryTipe qt, Datos e){
		ArrayList<Datos> arD = null;
		switch (qt) {
		case SELECT:
			arD = this.recuperarTodos(e);
			break;
			
		case DELETE:
			this.delete(e);
			break;
			
		case INSERT:
			this.insertData(e);
			break;
			
		case UPDATE:
			this.update(e);
			break;
			
		default:
			break;
		}
		return arD;
		
	}
	
	private void insertData(Datos e) {
		this.beginSession();
		s.save(e);
	}

	private ArrayList<Datos> recuperarTodos(Datos e){
		ArrayList<Datos> result = new ArrayList<Datos>();
		Query q = s.createQuery("Select e from "+ e.getClass() +" e");
		Iterator peñita = q.list().iterator();
		while (peñita.hasNext()) {
			result.add((Datos) peñita.next());
		}
		return null;
	}
	
	public void setInput(String file) {
		if (this.input!=null) {
			this.input.destroyStream();
		}
		try {
			this.input = new in_data(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.print("Error, no se encontro el archivo");
//			e.printStackTrace();
		}
	}
	public in_data getInput() {
		if (this.input==null) {
			this.input.destroyStream();
		}
		return this.input;
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

	public void setOutput(String file) {
		this.output = new out_data(file);
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
		this.vc = yoH;
	}
	
}


