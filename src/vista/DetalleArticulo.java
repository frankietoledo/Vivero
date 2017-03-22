package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import controlador.Coordinador;
import modelo.Articulo;
import modelo.Conexion;
import modelo.Constantes;

public class DetalleArticulo extends JFrame implements ItemListener {

	private JPanel contentPane;
	private JTextField tfPrecio;
	private JTextField tfNombre;
	private Articulo art;
	private JComboBox<String> cbCategoria = new JComboBox<String>();
	private Coordinador miCoordinador;
	private JLabel lblYaExisteEse = new JLabel("Ya existe ese producto");
	private Articulo copia=new Articulo();
	private boolean estado=false;

	
	public DetalleArticulo() {
		
		//Este bloque de codigo es para que cuando se aprete ESC se cierre la ventana
		KeyboardFocusManager kb = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kb.addKeyEventPostProcessor(new KeyEventPostProcessor(){
		      @Override
		      public boolean postProcessKeyEvent(KeyEvent e){
		      if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
		           dispose();
		           return true;
		      }
		    });
		
		AutoCompleteDecorator.decorate(cbCategoria);
		cbCategoria.addItemListener(this);
		addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				if (estado==false){
					cargarCampos();
					estado=true;
				}
			}
			@Override
			public void windowLostFocus(WindowEvent arg0) {
			}
		});
		setTitle("Vivero Sue\u00F1o Verde");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DetalleArticulo.class.getResource("/vista/iconos/flower128.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 530, 295);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEditarArticulo = new JLabel("Editar articulo:");
		lblEditarArticulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditarArticulo.setFont(new Font("Verdana", Font.PLAIN, 38));
		lblEditarArticulo.setBounds(92, 0, 294, 67);
		contentPane.add(lblEditarArticulo);
		
		JLabel label = new JLabel("Nombre:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 22));
		label.setBounds(102, 63, 108, 25);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Precio:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		label_1.setBounds(102, 115, 108, 25);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Categoria:");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 22));
		label_2.setBounds(102, 163, 108, 31);
		contentPane.add(label_2);
		
		tfPrecio = new JTextField();
		tfPrecio.setFont(new Font("Tahoma", Font.PLAIN, 22));
		tfPrecio.setColumns(10);
		tfPrecio.setBounds(220, 113, 294, 28);
		contentPane.add(tfPrecio);
		
		tfNombre = new JTextField();
		tfNombre.setFont(new Font("Tahoma", Font.PLAIN, 22));
		tfNombre.setColumns(10);
		tfNombre.setBounds(220, 63, 294, 28);
		contentPane.add(tfNombre);
		
		cbCategoria.setFont(new Font("Tahoma", Font.PLAIN, 22));
		cbCategoria.setBounds(220, 163, 294, 31);
		contentPane.add(cbCategoria);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//compararArticulos devuelve true si son iguales
				if (compararArticulos()==false){
					miCoordinador.actualizarArticulo(art,copia.getNombre());
				}
				tfNombre.setText("");
				tfPrecio.setText("");
				cbCategoria.setSelectedIndex(0);
				estado=false;
				dispose();
			}
		});
		btnGuardar.setIcon(new ImageIcon(DetalleArticulo.class.getResource("/vista/iconos/accept32.png")));
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnGuardar.setBounds(108, 218, 188, 40);
		contentPane.add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(DetalleArticulo.class.getResource("/vista/iconos/cancel32.png")));
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				estado=false;
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnCancelar.setBounds(315, 218, 188, 40);
		contentPane.add(btnCancelar);
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(DetalleArticulo.class.getResource("/vista/iconos/flower128.png")));
		label_3.setBounds(-17, 49, 128, 128);
		contentPane.add(label_3);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(92, 11, 2, 247);
		contentPane.add(separator_1);
		
		lblYaExisteEse.setVisible(false);
		lblYaExisteEse.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblYaExisteEse.setForeground(Color.RED);
		lblYaExisteEse.setBounds(230, 90, 284, 25);
		contentPane.add(lblYaExisteEse);
		
		tfPrecio.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(java.awt.event.KeyEvent e) {
			
			}

			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				
			}

			@Override
			public void keyTyped(java.awt.event.KeyEvent e) {
				int limite=10;
				if (tfPrecio.getText().length()>limite)
					e.consume();
				if (!Character.isDigit(e.getKeyChar())){
					if (e.getKeyChar()!= ',')
						if (e.getKeyChar()!='.')
							e.consume();
					if (tfPrecio.getText().indexOf('.')!=-1)
						e.consume();
				}
					
			}
			
		});
		//FINNNNNNNNNNNNNN DEL CREATE
	}
	protected boolean compararArticulos() {
		art.setNombre(tfNombre.getText().trim().toUpperCase());
		art.setId(miCoordinador.obtenerIdArticulo(art.getNombre()));
		art.setPrecio(Float.parseFloat(tfPrecio.getText()));
		String nombreCat=(String)cbCategoria.getSelectedItem();
		art.setCategoria(nombreCat.trim().toUpperCase());
		copia.setId(art.getId());

		if (art.equals(copia)){
			return true;
		}
		return false;
	}
	public void cargarCampos(){
		copia.setNombre(art.getNombre());
		copia.setPrecio(art.getPrecio());
		copia.setCategoria(art.getCategoria());
		cargarComboBox();
		tfNombre.setText(art.getNombre());
		tfPrecio.setText(String.valueOf(art.getPrecio()));
		cbCategoria.setSelectedItem(art.getCategoria());
	}

	private void cargarComboBox() {
		cbCategoria.removeAllItems();
		Conexion con = new Conexion();
		try {
			con.conectar();
			PreparedStatement st = con.getConnection().prepareStatement(Constantes.Select_NombreCat_from_CategoriasDB);
			ResultSet rs = st.executeQuery();
			String cadena;
			while (rs.next()){
				cadena = rs.getString(Constantes.NOMBRE_DE_CATEGORIA);
				cbCategoria.addItem(cadena);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar el ComboBox");
			e.printStackTrace();
		} finally {
			con.cerrar();
		}		
		cbCategoria.addItem("Agregar nueva categoria");
	}
	public void setArticulo(Articulo obtenerArticuloDesdeTabla) {
		this.art=obtenerArticuloDesdeTabla;
	}
	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (cbCategoria.getSelectedItem()!=null){
			if (cbCategoria.getSelectedItem().equals("Agregar nueva categoria")){
				miCoordinador.nuevaCategoria();
			}
		}
	}
}
