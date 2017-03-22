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
			PreparedStatement st = con.getConnection().prepareStatement(Constantes.Consultar_si_Categoria_Existe);
			st.setString(1, nombre);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				if (nombre.equals(rs.getString(Constantes.NOMBRE_DE_CATEGORIA))){
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
			PreparedStatement st = con.getConnection().prepareStatement(Constantes.Insertar_Nombre_de_Categoria);
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
			PreparedStatement st = con.getConnection().prepareStatement(Constantes.Seleccionar_todo_desde_Categorias_Where_NOMBRE);
			st.setString(1, art.getCategoria());
			ResultSet rs = st.executeQuery();
			idCat=rs.getInt(Constantes.ID_DE_CATEGORIA);
			st= con.getConnection().prepareStatement(Constantes.Insertar_en_articulos_Nombre_Precio_Categoria);
			st.setString(1, art.getNombre());
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
			PreparedStatement st = con.getConnection().prepareStatement(Constantes.Update_NombreCategoria);
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
			st = con.getConnection().prepareStatement(Constantes.Delete_Categoria_Where_Nobre);
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
			st = con.getConnection().prepareStatement(Constantes.Delete_Articulo_Where_Nombre);
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
			PreparedStatement st = con.getConnection().prepareStatement(Constantes.Select_ID_Where_nombre);
			st.setString(1, nombre);
			ResultSet rs = st.executeQuery();
			int idarticulo = 0;
			while (rs.next()){
				idarticulo=rs.getInt(Constantes.ID_DE_ARTICULO);
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
	public int obtenerIdCategoria(Articulo art){
		Conexion con = new Conexion();
		try {
			con.conectar();
			con.getConnection().setAutoCommit(true);
			//Esta consulta es para obtener y guardar el numero de id de la categoria del art
			int idCat=0;
			PreparedStatement st = con.getConnection().prepareStatement(Constantes.Select_All_Categorias_Where_Nombre);
			st.setString(1, art.getCategoria());
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				idCat=rs.getInt(Constantes.ID_DE_CATEGORIA);
			}
			return idCat;
		}
		catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
			return -1;
		}finally {
			con.cerrar();
		}
	}
	public void actualizarArticulo(Articulo art,String nombreViejo,int idCat) {
		Conexion con = new Conexion();
		try {
			con.conectar();
			con.getConnection().setAutoCommit(true);
			//Esta consulta es para actualizar el art
			PreparedStatement st= con.getConnection().prepareStatement(Constantes.Update_nombre_precio_categoria_where_idArt);
			st.setString(1, art.getNombre());
			st.setFloat(2, art.getPrecio());
			st.setInt(3, idCat);
			st.setString(4, nombreViejo);
			st.executeUpdate();
			JOptionPane.showMessageDialog(null, "Se actualizo el articulo");
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			con.cerrar();
		}
	}
}
