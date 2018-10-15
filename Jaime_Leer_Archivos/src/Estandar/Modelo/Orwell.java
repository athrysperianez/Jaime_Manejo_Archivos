import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 *Creado por Elias Periañez
 *8 oct. 2018
 *Como parte del proyecto Jaime_Leer_Archivos
 *Este archivo esta bajo la licencia de Creative Commons Reconocimiento 4.0 Internacional (Más informacion https://creativecommons.org/licenses/by/4.0/)
________________________________________________________________________________________________________________________________________________________
 *Created by Elias Periañez
 *8 oct. 2018
 *As part of the project Jaime_Leer_Archivos
 *This file is under the Creative Commons Attribution 4.0 International (More info here https://creativecommons.org/licenses/by/4.0/)
 */

public class Orwell {

	private Desarrollador ds;
	private InputStream fileStream;
	private Connection dbStream;

	public Orwell(Desarrollador ds, InputStream fileStream, Connection dbStream) {

		this.setDs(ds);
		this.setFileStream(fileStream);
		this.setDbStream(dbStream);

	}

	public boolean askOrwell(String tablaDs) {
		boolean confirmation = false;
		ResultSet rset = null;
		String queryDs = "SELECT * FROM " + tablaDs + "WHERE `id`=" + this.ds.getId() + " AND `nombre`="
				+ this.ds.getNombre() + " AND `categoria`=" + this.ds.getTamaño();

		try {
			PreparedStatement pstmt = this.dbStream.prepareStatement(queryDs);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				confirmation = false;
			}
		} catch (SQLException s) {
			System.err.println("Orwell ha fallado con el siguiente error: " + s.getStackTrace());
		}

		return confirmation;
	}

	public boolean askBigBrother() {
		boolean confirmation = false;
		try {
			int i = fileStream.read();
			String datos = "";
			while (i != -1) {
				datos += (char) i;
				i = fileStream.read();
			}
			for (String x : datos.split("·")) {
				if(ds.Comparador(new Desarrollador(x, "@"))){
					confirmation = true;
				}
			}
		} catch (IOException e) {
			System.err.println("\"Big brother\" no ha sido capaz de leer el archivo local");
		}
		return confirmation;

	}
	
	public Connection getDbStream() {
		return dbStream;
	}

	public void setDbStream(Connection dbStream) {
		this.dbStream = dbStream;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	public Desarrollador getDs() {
		return ds;
	}

	public void setDs(Desarrollador ds) {
		this.ds = ds;
	}

}
