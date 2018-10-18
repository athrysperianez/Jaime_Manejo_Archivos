package Hibernate.Modelo;

import Hibernate.Vista.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
	
	public ArrayList<Object> recuperarTodos(Object e){
		ArrayList<Object> result = new ArrayList<Object>();
		Query q = s.createQuery("select e from Employee e");
		Iterator peñita = q.list().iterator();
		while (peñita.hasNext()) {
			result.add(peñita.next());
		}
		return null;
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
	
}
