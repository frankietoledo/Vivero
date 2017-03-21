package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ArticuloDao {
	//Clase para generar acceso a la base de datos sobre operaciones de articulos
	
	//Borrar una cat
	//Consultar si existe la cat
	public boolean consultarSiCategoriaExiste(String nombre){
		Conexion con = new Conexion();
		try {
			con.conectar();
			PreparedStatement st = con.getConnection().prepareStatement("select nombreCat from categorias where nombreCat=(?);");
			st.setString(1, nombre);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				if (nombre.equals((String)rs.getString("nombreCat"))){
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			return true;
		}finally {
			con.cerrar();
		}
	}
	//Guardar nueva categoria
	public boolean guardarCategoria(String nombre){
		Conexion con=new Conexion();
		try {
			con.conectar();
			PreparedStatement st = con.getConnection().prepareStatement("INSERT INTO categorias "
					+"(nombreCat) VALUES (?);");
			st.setString(1,nombre);
			st.execute();
			JOptionPane.showMessageDialog(null, "Categoria guardada.");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			return false;
		}finally {
			con.cerrar();
		}
	}
	//Borrar un art
	//Consultar si existe el art
	//Guardar un art nuevo
	public boolean guardarArticulo(Articulo art){
		Conexion con=new Conexion();
		try {
			con.conectar();
			//primero necesitamos el id de la categoria que eligio
			int idCat=0;
			PreparedStatement st = con.getConnection().prepareStatement("select * from categorias where nombreCat=(?);");
			st.setString(1, art.getCategoria());
			ResultSet rs = st.executeQuery();
			idCat=rs.getInt("idCat");
			st= con.getConnection().prepareStatement("insert into articulos (nombreArt,precioArt,categoriaArt) values (?,?,?);");
			st.setString(1, art.getNombre().trim().toUpperCase());
			st.setFloat(2, art.getPrecio());
			st.setInt(3, idCat);
			st.execute();
			JOptionPane.showMessageDialog(null, "Articulo ingresado");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			return false;
		}finally {
			con.cerrar();
		}
	}
	public void actualizarCategoria(String viejo, String nuevo) {
		Conexion con = new Conexion();
		try {
			con.conectar();
			PreparedStatement st = con.getConnection().prepareStatement("UPDATE categorias SET nombreCat = (?) WHERE nombreCat = (?);");
			st.setString(1, nuevo);
			st.setString(2, viejo);
			st.executeUpdate();
			JOptionPane.showMessageDialog(null, "Se actualizo ");
			
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			con.cerrar();
		}
	}
	public void borrarCategoria(String valueAt) {
		Conexion con = new Conexion();
		con.conectar();
		PreparedStatement st;
		try {
			st = con.getConnection().prepareStatement("DELETE FROM categorias WHERE nombreCat=(?);");
			st.setString(1, valueAt);
			st.execute();
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		}finally {
			con.cerrar();
		}
		
	}
	public void borrarArticulo(String valueAt) {
		Conexion con = new Conexion();
		con.conectar();
		PreparedStatement st;
		try {
			st = con.getConnection().prepareStatement("DELETE FROM articulos WHERE nombreArt=(?);");
			st.setString(1, valueAt);
			st.execute();
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		}finally {
			con.cerrar();
		}		
	}
	public int obtenerIDArticulo(String nombre){
		Conexion con = new Conexion();
		con.conectar();
		try {
			PreparedStatement st = con.getConnection().prepareStatement("SELECT idArt FROM articulos WHERE nombreArt=(?);");
			st.setString(1, nombre);
			ResultSet rs = st.executeQuery();
			int idarticulo = 0;
			while (rs.next()){
				idarticulo=rs.getInt("idArt");
			}
			return idarticulo;
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		}finally {
			con.cerrar();
		}
		return 0;
	}
	public void actualizarArticulo(Articulo art) {
		Conexion con = new Conexion();
		try {
			con.conectar();
			//Esta consulta es para obtener y guardar el numero de id de la categoria del art
			int idCat=0;
			PreparedStatement st = con.getConnection().prepareStatement("select * from categorias where nombreCat=(?);");
			st.setString(1, art.getCategoria());
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				idCat=rs.getInt("idCat");
			}
			//Esta consulta es para actualizar el art
			System.out.println(art.getNombre());
			st= con.getConnection().prepareStatement("Update articulos set nombreArt=?,precioArt=?,categoriaArt=? where idArt=?;");
			st.setString(1, art.getNombre());
			st.setFloat(2, art.getPrecio());
			st.setInt(3, idCat);
			st.setInt(4, art.getId());
			st.executeUpdate();
			JOptionPane.showMessageDialog(null, "Se actualizo ");
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			con.cerrar();
		}
	}	
}
