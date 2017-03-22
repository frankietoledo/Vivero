package modelo;

public final class Constantes {
	//Detalles de la base de datos productos y nombres
	//de las columnas y demas
	public static final String ARTICULOS_DB="productos";
	public static final String NOMBRE_DE_ARTICULO="nombreProd";
	public static final String ID_DE_ARTICULO="idProd";
	public static final String PRECIO_DE_ARTICULO="precioProd";
	public static final String CATEGORIA_DEL_ARTICULO="categoriaProd";
	
	//Tablas de categorias
	public static final String CATEGORIAS_DB="categorias";
	public static final String ID_DE_CATEGORIA="idCat";
	public static final String NOMBRE_DE_CATEGORIA="nombreCat";
	
	//sentencias sql
	public static final String Consultar_si_Categoria_Existe="select "+NOMBRE_DE_CATEGORIA+" from "+CATEGORIAS_DB+" where "+NOMBRE_DE_CATEGORIA+"= (?);";

	public static final String Insertar_Nombre_de_Categoria="INSERT INTO "+CATEGORIAS_DB+" ("+NOMBRE_DE_CATEGORIA+") VALUES (?) ;";
	public static final String Insertar_en_articulos_Nombre_Precio_Categoria ="insert into "+ARTICULOS_DB+" ("+NOMBRE_DE_ARTICULO+","+PRECIO_DE_ARTICULO+","+CATEGORIA_DEL_ARTICULO+") values (?,?,?);";
	
	public static final String Select_NombreCat_from_CategoriasDB = "Select "+NOMBRE_DE_CATEGORIA+" from "+CATEGORIAS_DB+" order by "+NOMBRE_DE_CATEGORIA+";";
	public static final String Select_NombreCat_from_Categorias_orderByNombre = "select "+NOMBRE_DE_CATEGORIA+" from "+CATEGORIAS_DB+" order by "+NOMBRE_DE_CATEGORIA+";";
	
	public static final String Select_ID_Where_nombre = "SELECT "+ID_DE_ARTICULO+" FROM "+ARTICULOS_DB+" WHERE "+NOMBRE_DE_ARTICULO+"= (?) ;";
	
	public static final String Select_all_join_articulosYcategorias = "select "+NOMBRE_DE_ARTICULO+","+PRECIO_DE_ARTICULO+","+CATEGORIAS_DB+"."+NOMBRE_DE_CATEGORIA+" FROM "+ARTICULOS_DB+" JOIN "+CATEGORIAS_DB+" on "+ARTICULOS_DB+"."+CATEGORIA_DEL_ARTICULO+"="+CATEGORIAS_DB+"."+ID_DE_CATEGORIA+" order by "+NOMBRE_DE_ARTICULO+";";
	public static final String Select_All_Categorias_Where_Nombre = "select * from "+CATEGORIAS_DB+" where "+NOMBRE_DE_CATEGORIA+"= (?) ;";
	public static final String Seleccionar_todo_desde_Categorias_Where_NOMBRE = "select * from "+CATEGORIAS_DB+" where "+NOMBRE_DE_CATEGORIA+"= (?) ;";
	
	public static final String Update_nombre_precio_categoria_where_idArt = "Update "+ARTICULOS_DB+" set "+NOMBRE_DE_ARTICULO+" = (?) , "+PRECIO_DE_ARTICULO+" = (?) , "+CATEGORIA_DEL_ARTICULO+" = (?) where "+NOMBRE_DE_ARTICULO+" = (?);";
	public static final String Update_NombreCategoria = "UPDATE "+CATEGORIAS_DB+" SET "+NOMBRE_DE_CATEGORIA+" = (?) WHERE "+NOMBRE_DE_CATEGORIA+" = (?) ;";
	
	public static final String Delete_Categoria_Where_Nobre = "DELETE FROM "+CATEGORIAS_DB+" WHERE "+NOMBRE_DE_CATEGORIA+"= (?) ;";
	public static final String Delete_Articulo_Where_Nombre = "DELETE FROM "+ARTICULOS_DB+" WHERE "+NOMBRE_DE_ARTICULO+"= (?) ;";	
	
}
