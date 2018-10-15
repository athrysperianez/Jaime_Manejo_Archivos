package Hibernate.Modelo;

import Hibernate.Vista.*;
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
	
	public Modelo(Vista vc) {
		this.vc = vc;
		this.startUp();
	}
	
	private void startUp() {
		s = new Configuration().configure().buildSessionFactory().openSession();
		this.initConfig();
	}

	private void initConfig() {
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
