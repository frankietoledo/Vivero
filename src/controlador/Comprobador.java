package controlador;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import vista.Principal;

public class Comprobador {
	private Principal miVentanaPrincipal;
	private Coordinador miCoordinador;
	
	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}
	public void setMiVentanaPrincipal(Principal principal){
		this.miVentanaPrincipal=principal;
	}	
}
