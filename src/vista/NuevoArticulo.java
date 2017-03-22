package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import controlador.Coordinador;
import modelo.Articulo;
import modelo.Conexion;
import modelo.Constantes;

public class NuevoArticulo extends JFrame implements ItemListener {

	private JPanel contentPane;
	private JTextField tfNombre;
	private JTextField tfPrecio;
	private Coordinador miCoordinador;
	private JComboBox<String> comboBoxCat = new JComboBox<String>();
	private JLabel lblYaExisteEse = new JLabel("Ya existe ese articulo");

	/**
	 * Create the frame.
	 */
	public NuevoArticulo() {
		
		
		AutoCompleteDecorator.decorate(comboBoxCat);
		comboBoxCat.setEditable(true);
		addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				actualizarComboBox();
			}
			@Override
			public void windowLostFocus(WindowEvent arg0) {
			}
		});
		lblYaExisteEse.setVisible(false);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(NuevoArticulo.class.getResource("/vista/iconos/rose 64.png")));
		setTitle("Vivero Sue\u00F1o Verde");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 642, 305);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBorder(null);
		lblNewLabel.setBounds(516, 44, 128, 196);
		lblNewLabel.setIcon(new ImageIcon(NuevoArticulo.class.getResource("/vista/iconos/rose128.png")));
		contentPane.add(lblNewLabel);
		
		JLabel lblAgregarNuevoArticulo = new JLabel("Agregar nuevo articulo:");
		lblAgregarNuevoArticulo.setFont(new Font("Verdana", Font.PLAIN, 38));
		lblAgregarNuevoArticulo.setBounds(29, 0, 626, 47);
		contentPane.add(lblAgregarNuevoArticulo);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 51, 496, 25);
		contentPane.add(separator);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNombre.setBounds(48, 69, 121, 25);
		contentPane.add(lblNombre);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblPrecio.setBounds(48, 127, 121, 25);
		contentPane.add(lblPrecio);
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblCategoria.setBounds(48, 175, 121, 31);
		contentPane.add(lblCategoria);
		
		tfNombre = new JTextField();
		tfNombre.setFont(new Font("Tahoma", Font.PLAIN, 22));
		tfNombre.setBounds(201, 69, 294, 28);
		contentPane.add(tfNombre);
		tfNombre.setColumns(10);
		
		tfPrecio = new JTextField();
		tfPrecio.setFont(new Font("Tahoma", Font.PLAIN, 22));
		tfPrecio.setColumns(10);
		tfPrecio.setBounds(201, 125, 294, 28);
		contentPane.add(tfPrecio);
		
		
		comboBoxCat.setFont(new Font("Tahoma", Font.PLAIN, 22));
		comboBoxCat.setBounds(201, 176, 294, 28);
		contentPane.add(comboBoxCat);
		
		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Articulo art = obtenerArticulo();
				//Consultar si existe devuelte true si existe el art
				if (miCoordinador.consultarSiArticuloExiste(art.getNombre()))
					lblYaExisteEse.setVisible(true);
				else{
					miCoordinador.guardarArticulo(art);
					lblYaExisteEse.setVisible(false);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnNewButton.setIcon(new ImageIcon(NuevoArticulo.class.getResource("/vista/iconos/accept32.png")));
		btnNewButton.setBounds(68, 229, 188, 40);
		contentPane.add(btnNewButton);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				tfNombre.setText("");
				tfPrecio.setText("");
				comboBoxCat.setSelectedItem(0);
			}
		});
		btnCancelar.setIcon(new ImageIcon(NuevoArticulo.class.getResource("/vista/iconos/cancel32.png")));
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnCancelar.setBounds(286, 229, 188, 40);
		contentPane.add(btnCancelar);
		
		lblYaExisteEse.setForeground(Color.RED);
		lblYaExisteEse.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblYaExisteEse.setBounds(214, 96, 294, 31);
		contentPane.add(lblYaExisteEse);
		comboBoxCat.addItemListener(this);
		actualizarComboBox();
		
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
					if (e.getKeyChar()!='.')
						e.consume();
					if (tfPrecio.getText().indexOf('.')!=-1)
						e.consume();
				}
					
			}
			
		});
		
	}
	
	public void actualizarComboBox() {
		comboBoxCat.removeAllItems();
//		miCoordinador.cargarComboBox(comboBoxCat);
//		comboBoxCat.addItem("Agregar nueva categoria");
		Conexion con = new Conexion();
		try {
			con.conectar();
			PreparedStatement st = con.getConnection().prepareStatement(Constantes.Select_NombreCat_from_CategoriasDB);
			ResultSet rs = st.executeQuery();
			String cadena;
			while (rs.next()){
				cadena = rs.getString(Constantes.NOMBRE_DE_CATEGORIA);
				comboBoxCat.addItem(cadena);
			}
			comboBoxCat.addItem("Agregar nueva categoria");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar el ComboBox");
			e.printStackTrace();
		} finally {
			con.cerrar();
		}
	}
	public Articulo obtenerArticulo() {
		Articulo art = new Articulo();
		art.setNombre(tfNombre.getText().trim().toUpperCase());
		art.setPrecio(Float.parseFloat(tfPrecio.getText()));
		art.setCategoria(comboBoxCat.getSelectedItem().toString());
		return art;
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (comboBoxCat.getSelectedItem()!=null){
			if (comboBoxCat.getSelectedItem().equals("Agregar nueva categoria")){
				miCoordinador.nuevaCategoria();
			}
		}
	}

	public void vaciarCampos() {
		miCoordinador.actualizarLista();
		tfNombre.setText("");
		tfPrecio.setText("");
		comboBoxCat.setSelectedItem(0);
	}
}
