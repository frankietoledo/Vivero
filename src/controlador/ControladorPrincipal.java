package controlador;

import modelo.Logica;
import sun.management.snmp.util.MibLogger;
import vista.DetalleArticulo;
import vista.DetalleCategorias;
import vista.EditarCategoria;

public class ControladorPrincipal {
	public static void iniciar(){
		//inicializacion de todas las clases
		Coordinador miCoordinador = new Coordinador();
		Logica miLogica=new Logica();
		vista.Principal miVentanaPrincipal = new vista.Principal();
		vista.NuevoArticulo miNuevoArticulo = new vista.NuevoArticulo();
		vista.NuevaCategoria miNuevaCategoria = new vista.NuevaCategoria();
		DetalleCategorias miDetalleCategorias = new DetalleCategorias();
		EditarCategoria miEditarCategoria = new EditarCategoria();
		DetalleArticulo miDetalleArticulo = new DetalleArticulo();
		
		//Establecer la relacion clase-coordinador
		miLogica.setCoordinador(miCoordinador);
		miVentanaPrincipal.setCoordinador(miCoordinador);
		miNuevoArticulo.setCoordinador(miCoordinador);
		miNuevaCategoria.setCoordinador(miCoordinador);
		miEditarCategoria.setCoodinador(miCoordinador);
		miDetalleCategorias.setCoordinador(miCoordinador);
		miDetalleArticulo.setCoordinador(miCoordinador);
		
		//Establecer las relaciones coordinador-claser
		miCoordinador.setLogica(miLogica);
		miCoordinador.setVentanaPrincipal(miVentanaPrincipal);
		miCoordinador.setMiNuevoArticulo(miNuevoArticulo);
		miCoordinador.setMiNuevaCategoria(miNuevaCategoria);
		miCoordinador.setEditarCategorias(miEditarCategoria);
		miCoordinador.setDetalleCategorias(miDetalleCategorias);
		miCoordinador.setDetalleArticulo(miDetalleArticulo);
		
		//Iniciamos la ventana principal para que comienze la UI
		miVentanaPrincipal.setVisible(true);
	}
	public static void main(String[] args) {
		iniciar();
		
	}
	
	
}
