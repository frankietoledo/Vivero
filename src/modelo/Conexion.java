package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sun.glass.ui.Window.Level;
import com.sun.istack.internal.logging.Logger;

public class Conexion {
	private String url = "C:\\Users\\frank\\workspace\\Vivero\\src\\databases\\vivero.db";
	Connection conexion;
	public Connection getConnection(){
		return conexion;
	}
	public void conectar(){
		try {
			conexion= DriverManager.getConnection("jdbc:sqlite:"+url);
			if (conexion!=null){
				System.out.println("Correctamente conectado");
			}
		} catch (SQLException e) {
			System.err.println("No se pudo conectar a la bd");
		}
	}
	public void cerrar(){
		try {
			conexion.close();
		} catch (SQLException e) {
			System.out.println("No se pudo cerrar la conexion de la bd");
		}
	}
	
}
