package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Coordinador;
import modelo.Articulo;
import modelo.Conexion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;

public class NuevoArticulo extends JFrame implements ItemListener {

	private JPanel contentPane;
	private JTextField tfNombre;
	private JTextField tfPrecio;
	private Coordinador miCoordinador;
	private JComboBox<String> comboBoxCat = new JComboBox<String>();

	/**
	 * Create the frame.
	 */
	public NuevoArticulo() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
				actualizarComboBox();
			}
			public void windowLostFocus(WindowEvent arg0) {
			}
		});
	
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(NuevoArticulo.class.getResource("/vista/iconos/rose 64.png")));
		setTitle("Vivero Sue\u00F1o Verde");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 667, 292);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBorder(null);
		lblNewLabel.setBounds(516, 22, 128, 196);
		lblNewLabel.setIcon(new ImageIcon(NuevoArticulo.class.getResource("/vista/iconos/rose128.png")));
		contentPane.add(lblNewLabel);
		
		JLabel lblAgregarNuevoArticulo = new JLabel("Agregar nuevo articulo:");
		lblAgregarNuevoArticulo.setFont(new Font("Verdana", Font.PLAIN, 38));
		lblAgregarNuevoArticulo.setBounds(29, 11, 626, 64);
		contentPane.add(lblAgregarNuevoArticulo);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 73, 496, 25);
		contentPane.add(separator);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNombre.setBounds(46, 102, 121, 25);
		contentPane.add(lblNombre);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblPrecio.setBounds(46, 143, 121, 25);
		contentPane.add(lblPrecio);
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblCategoria.setBounds(46, 179, 121, 31);
		contentPane.add(lblCategoria);
		
		tfNombre = new JTextField();
		tfNombre.setFont(new Font("Tahoma", Font.PLAIN, 22));
		tfNombre.setBounds(199, 102, 294, 28);
		contentPane.add(tfNombre);
		tfNombre.setColumns(10);
		
		tfPrecio = new JTextField();
		tfPrecio.setFont(new Font("Tahoma", Font.PLAIN, 22));
		tfPrecio.setColumns(10);
		tfPrecio.setBounds(199, 141, 294, 28);
		contentPane.add(tfPrecio);
		
		
		comboBoxCat.setFont(new Font("Tahoma", Font.PLAIN, 22));
		comboBoxCat.setBounds(199, 179, 294, 31);
		contentPane.add(comboBoxCat);
		
		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Articulo art = obtenerArticulo();
				miCoordinador.guardarArticulo(art);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnNewButton.setIcon(new ImageIcon(NuevoArticulo.class.getResource("/vista/iconos/accept32.png")));
		btnNewButton.setBounds(84, 221, 188, 40);
		contentPane.add(btnNewButton);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setIcon(new ImageIcon(NuevoArticulo.class.getResource("/vista/iconos/cancel32.png")));
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnCancelar.setBounds(358, 221, 188, 40);
		contentPane.add(btnCancelar);
		actualizarComboBox();
	}
	
	public void actualizarComboBox() {
		comboBoxCat.removeAllItems();
		comboBoxCat.addItemListener(this);
		Conexion con = new Conexion();
		try {
			con.conectar();
			PreparedStatement st = con.getConnection().prepareStatement("Select nombreCat from categorias order by nombreCat;");
			ResultSet rs = st.executeQuery();
			String cadena;
			while (rs.next()){
				cadena = rs.getString("nombreCat");
				comboBoxCat.addItem(cadena);
			}
			comboBoxCat.addItem(new String ("Agregar categoria"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar el ComboBox");
			e.printStackTrace();
		} finally {
			con.cerrar();
		}
	}
	public Articulo obtenerArticulo() {
		Articulo art = new Articulo();
		art.setNombre(tfNombre.getText());
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
			if (comboBoxCat.getSelectedItem().equals("Agregar categoria")){
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
