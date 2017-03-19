package modelo;

public class Articulo{
	private String nombre;
	private String categoria;
	private float precio;
	private int id;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Articulo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Articulo(String nombre, String categoria, float precio) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
	}
	@Override
	public String toString() {
		return "Articulo [nombre=" + nombre + ", categoria=" + categoria + ", precio=" + precio + ", id=" + id + "]";
	}
	@Override
	public boolean equals(Object a) {
		Articulo b=(Articulo)a;
		if (b.getId()==this.id && b.getPrecio()==this.precio){
			if (b.getNombre().equals(this.nombre)){
				if( b.getCategoria().equals(this.categoria)){
					return true;
				}
			}
		}
		return false;
	}
}
