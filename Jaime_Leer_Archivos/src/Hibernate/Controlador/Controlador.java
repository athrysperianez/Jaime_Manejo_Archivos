package Hibernate.Controlador;

import Hibernate.Vista.*;
import Hibernate.Modelo.*;

public class Controlador {
	private Modelo md;
	private Vista vc;

	public Controlador(Vista vc, Modelo md) {
		this.setVista(vc);
		this.setModelo(md);
	}

	public void setModelo(Modelo md) {
		
	}

	public void setVista(Vista yoH) {
		
	}

	public Modelo getModelo() {
		return md;
	}

	public Vista getVista() {
		return vc;
	}

	public void select() {
		
	}
	
	
}
