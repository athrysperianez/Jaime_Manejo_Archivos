package Hibernate.Vista;

import java.util.Scanner;

import Hibernate.Controlador.Controlador;
import Hibernate.Modelo.Modelo;

public class Vista {

	private Controlador cl;
	private Modelo md;
	private final Scanner sc = new Scanner(System.in);

	public Vista(Controlador cl, Modelo md) {
		this.setControlador(cl);
		this.setModelo(md);
	}

	public void setControlador(Controlador cl) {

	}

	public void setModelo(Modelo md) {

	}

	public String askData() {
		return sc.nextLine();
	}

	public void imprimir(String data) {
		System.out.println(data);
	}

	public void menuLoop() {
		cl.select();
	}

	public void loopBreak() {
		sc.close();
		md.closeStreams();
		System.exit(0);
	}

	public Controlador getControlador() {
		return cl;
	}

	public Modelo getModelo() {
		return md;
	}

}
