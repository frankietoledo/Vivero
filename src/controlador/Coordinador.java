package controlador;

import modelo.Articulo;
import modelo.Logica;
import vista.DetalleArticulo;
import vista.DetalleCategorias;
import vista.EditarCategoria;

public  class Coordinador {
	//Atributos
	private modelo.Logica miLogica;
	private vista.Principal miVentanaPrincipal;
	private vista.NuevoArticulo miNuevoArticulo;
	private vista.NuevaCategoria miNuevaCategoria;
	private EditarCategoria miEditarCategoria;
	private DetalleCategorias miDetalleCategorias;
	private DetalleArticulo miDetalleArticulo;

	
	public void setDetalleArticulo(DetalleArticulo miDetalleArticulo){
		this.miDetalleArticulo=miDetalleArticulo;
	}
	public void setEditarCategorias(EditarCategoria editarCategoria){
		this.miEditarCategoria=editarCategoria;
	}
	public void setDetalleCategorias(DetalleCategorias detalleCategorias){
		this.miDetalleCategorias=detalleCategorias;
	}
	public void setLogica(Logica logica){
		this.miLogica=logica;
	}
	public void setVentanaPrincipal(vista.Principal principal){
		this.miVentanaPrincipal=principal;
	}
	public void setMiNuevoArticulo(vista.NuevoArticulo nuevo){
		this.miNuevoArticulo = nuevo;
	}
	public void setMiNuevaCategoria(vista.NuevaCategoria categoria){
		this.miNuevaCategoria=categoria;
	}
	public void nuevoArticulo(){
		miNuevoArticulo.setVisible(true);
	}

	public void nuevaCategoria() {
		miNuevaCategoria.setVisible(true);
	}
	public void actualizarLista() {
		miVentanaPrincipal.actualizarLista();
	}

	public void realizarBusqueda(String text) {
		
	}
	public boolean verificarCategoria(String text) {
		//validar categoria da true si existe una misma categoria en la bd
		return miLogica.validarCategoria(text);
	}
	
	public void guardarCategoria(String text) {
		miLogica.guardarCategoria(text);
		miNuevoArticulo.actualizarComboBox();	
		miDetalleCategorias.actualizarTablas();
	}
	public void guardarArticulo(Articulo art){
		if (miLogica.validarArticulo(art)){
			miNuevoArticulo.vaciarCampos();
			miVentanaPrincipal.actualizarLista();
		}	
	}
	public void mostrarDetalleCategorias() {
		miDetalleCategorias.setVisible(true);
	}
	public void editarCategoria(String nombre) {
		miEditarCategoria.setVisible(true);
		miEditarCategoria.cargarLabel(nombre);
	}
	public void actualizarCategoria(String nombreViejo,String nombreNuevo) {
		miLogica.actualizarCategoria(nombreViejo,nombreNuevo);
		miDetalleCategorias.actualizarTablas();
	}
	public void borrarCategoria(String valueAt) {
		miLogica.borrarCategoria(valueAt);
		miDetalleCategorias.actualizarTablas();
		
	}
	public void borrarArticulo(String valueAt) {
		miLogica.borrarArticulo(valueAt);
		
	}
	public void nuevoDetalleArticulo(Articulo obtenerArticuloDesdeTabla) {
		miDetalleArticulo.setArticulo(obtenerArticuloDesdeTabla);
		miDetalleArticulo.setVisible(true);
	}
	public void actualizarArticulo(Articulo art,String nombreViejo) {
		miLogica.actualizarArticulo(art,nombreViejo);
	}
	public int obtenerIdArticulo(String nombre) {
		return miLogica.obtenerIdArticulo(nombre);
	}
}
