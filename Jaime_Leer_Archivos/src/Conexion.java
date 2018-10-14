import java.sql.*;
/*
 *Creado por Elias Periañez
 This file is under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International (More info here http://creativecommons.org/licenses/by-nc-sa/4.0/)
 */

public class Conexion {

	private String bd;
	private String login;
	private String password;
	private String url = "jdbc:mysql:";
	private Connection conexion;

	/**
	 * @author Elias
	 * @param db Es la base de datos
	 * 
	 */
	public Conexion(String bd, String login, String pass, Boolean localhost) {
		this(bd, login, pass, true, "localhost");
		if (!localhost) {
			System.err.println(
					"Has llamado al constructor de manera incorrecta, no se puede llamar con localhost como false y no proporcionar un servidor, hemos supuesto que el servidor es localhost.");
		}
	}

	public Connection getConexion() {
		return conexion;
	}
	
	public String getDbStatus(String tabla) {
		String devolver = "";
		devolver += "DB: "+this.bd;
		devolver += "\nUsuario: "+this.login;
		devolver+="\nUsando el siguiente enlace: "+this.url;
		devolver+="\nLa query de prueba ";
		Statement stmt;
		try {
			stmt = conexion.createStatement();
			stmt.executeQuery("SELECT * FROM "+tabla);
			devolver += "tuvo exito";
		} catch (SQLException e) {
			devolver+="fue un error, a continuacion tienes el mensaje de JDBC:";
			devolver+="\n"+e.getMessage();
		}
		return devolver;
		
	}
	
	public Conexion(String bd, String login, String pass, boolean localhost, String server) {
		this.login = login;
		this.password = pass;
		this.bd = bd;
		this.url += "//" + server + "/" + bd
				+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		try {
			conexion = DriverManager.getConnection(url, login, password);
		} catch (SQLException e) {
			System.err.println("La conexion fallo, aqui tienes el mensaje de error: ");
			System.err.println(e.getMessage());
		}
	}

	public ResultSet Consulta(String query) {
		ResultSet rset = null;
		try {
			PreparedStatement pstmt = conexion.prepareStatement(query);
			rset = pstmt.executeQuery(query);
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return rset;
	}

	public String Consulta(String query, Boolean procesar) {
		ResultSet rset = Consulta(query);
		return procesarRset(rset);
	}

	public String Consulta(String query, Boolean procesar, int columna) {
		ResultSet rset = Consulta(query);
		return procesarRset(rset, columna);
	}

	public String procesarRset(ResultSet rset) {
		String resultado = "";
		try {
			ResultSetMetaData rsmd = rset.getMetaData();
			while (rset.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					resultado += rset.getString(i) + ",";
				}
				resultado = resultado.substring(0, resultado.length() - 1);
				resultado += "/";
			}
			resultado = resultado.substring(0, resultado.length() - 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public String procesarRset(ResultSet rset, int columnas) {
		String resultado = "";
		try {
			ResultSetMetaData rsmd = rset.getMetaData();
			while (rset.next()) {
				resultado += rset.getString(columnas) + "/";
			}
			resultado = resultado.substring(0, resultado.length() - 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public boolean insertarDatos(String tabla, String[] campos, byte[] values) {
		boolean resultado = true;
		String sql = "INSERT INTO ";
		sql += "`" + tabla + "`(";
		for (String x : campos) {
			sql += "`" + x + "`,";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ") VALUES (";
		for (byte x : values) {
			sql += "'" + x + "',";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			resultado = false;
			e.printStackTrace();
		}
		return resultado;

	}

	public boolean insertarDatos(String tabla, String[] campos, int[] values) {
		boolean resultado = true;
		String sql = "INSERT INTO ";
		sql += "`" + tabla + "`(";
		for (String x : campos) {
			sql += "`" + x + "`,";
		}
		
		sql = sql.substring(0, sql.length() - 1);
		sql += ") VALUES (";
		for (int x : values) {
			sql += "'" + x + "',";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			resultado = false;
			e.printStackTrace();
		}
		return resultado;

	}

	public boolean insertarDatos(String tabla, String[] campos, String[] values) {
		boolean resultado = true;
		String sql = "INSERT INTO ";
		sql += "`" + tabla + "`(";
		for (String x : campos) {
			sql += "`" + x + "`,";
		}
		
		sql = sql.substring(0, sql.length() - 1);
		sql += ") VALUES (";
		for (String x : values) {
			sql += "'" + x + "',";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			resultado = false;
			e.printStackTrace();
		}
		return resultado;

	}

	public boolean insertarDatos(String tabla, String[] campos, short[] values) {
		boolean resultado = true;
		String sql = "INSERT INTO ";
		sql += "`" + tabla + "`(";
		for (String x : campos) {
			sql += "`" + x + "`,";
		}
		
		sql = sql.substring(0, sql.length() - 1);
		sql += ") VALUES (";
		for (short x : values) {
			sql += "'" + x + "',";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			resultado = false;
			e.printStackTrace();
		}
		return resultado;

	}

	public boolean insertarDatos(String tabla, String[] campos, long[] values) {
		boolean resultado = true;
		String sql = "INSERT INTO ";
		sql += "`" + tabla + "`(";
		for (String x : campos) {
			sql += "`" + x + "`,";
		}
		
		sql = sql.substring(0, sql.length() - 1);
		sql += ") VALUES (";
		for (long x : values) {
			sql += "'" + x + "',";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			resultado = false;
			e.printStackTrace();
		}
		return resultado;

	}

	public boolean insertarDatos(String tabla, String[] campos, float[] values) {
		boolean resultado = true;
		String sql = "INSERT INTO ";
		sql += "`" + tabla + "`(";
		for (String x : campos) {
			sql += "`" + x + "`,";
		}
		
		sql = sql.substring(0, sql.length() - 1);
		sql += ") VALUES (";
		for (float x : values) {
			sql += "'" + x + "',";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			resultado = false;
			e.printStackTrace();
		}
		return resultado;

	}

	public boolean insertarDatos(String tabla, String[] campos, boolean[] values) {
		boolean resultado = true;
		String sql = "INSERT INTO ";
		sql += "`" + tabla + "`(";
		for (String x : campos) {
			sql += "`" + x + "`,";
		}
		
		sql = sql.substring(0, sql.length() - 1);
		sql += ") VALUES (";
		for (boolean x : values) {
			sql += "'" + x + "',";
		}	
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			resultado = false;
		}
		return resultado;

	}

	public boolean borrarFila(String tabla, String condicion) {
		boolean resultado = true;
		String sql = "DELETE FROM `";
		sql += tabla + "` WHERE " + condicion;
		Statement stmt;

		try {
			stmt = conexion.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			resultado = false;
		}

		return resultado;
	}
	
	/**
	 * 
	 * IMPORTANTE: Pasar la misma cantidad de columnas y valores.
	 */
	public boolean editarDatos(String tabla, String [] columnas, String [] valores, String condicion) {
		boolean devolver = true;
		String sql = "UPDATE `"+tabla+"` SET ";
		for(int i = 0; i<columnas.length;i++) {
			
			sql+= "`"+columnas[i]+"`" + " = " + valores[i] + ", ";
		}
		sql = sql.substring(0, sql.length()-2);
		if(condicion.equals("")) {
			condicion = "1";
		}
		sql += " WHERE "+ condicion;
		try {
			Statement stm =  conexion.createStatement();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			devolver = false;
		}
		return devolver;
	}

	public String procesarRset(ResultSet rset, String separador, String separador2) {
		String resultado = "";
		try {
			ResultSetMetaData rsmd = rset.getMetaData();
			while (rset.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					resultado += rset.getString(i) + separador;
				}
				resultado = resultado.substring(0, resultado.length() - 1);
				resultado += separador2;
			}
			resultado = resultado.substring(0, resultado.length() - 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public void kill() {
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}