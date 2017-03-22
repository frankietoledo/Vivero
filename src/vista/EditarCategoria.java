package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Coordinador;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class EditarCategoria extends JFrame {

	private JPanel contentPane;
	private JTextField tfnombre;
	private Coordinador miCoordinador;
	private JLabel titulo = new JLabel("");
	private JLabel lblYaExisteUna = new JLabel("Ya existe una categoria con ese nombre");
	private String nombreViejo;
	/**
	 * Create the frame.
	 */
	public EditarCategoria() {
		lblYaExisteUna.setVisible(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditarCategoria.class.getResource("/vista/iconos/flowers128.png")));
		setResizable(false);
		setTitle("Vivero Sue\u00F1o Verde");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 195);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setBounds(0, 11, 634, 28);
		titulo.setFont(new Font("Verdana", Font.PLAIN, 22));
		contentPane.add(titulo);
		
		tfnombre = new JTextField();
		tfnombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepararParaActualizar(nombreViejo,tfnombre.getText());
			}
		});
		tfnombre.setFont(new Font("Tahoma", Font.PLAIN, 22));
		tfnombre.setBounds(133, 50, 337, 29);
		contentPane.add(tfnombre);
		tfnombre.setColumns(10);
		
		JButton button = new JButton("Guardar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepararParaActualizar(nombreViejo,tfnombre.getText());
			}
		});
		button.setIcon(new ImageIcon(EditarCategoria.class.getResource("/vista/iconos/accept32.png")));
		button.setFont(new Font("Tahoma", Font.PLAIN, 22));
		button.setBounds(94, 116, 188, 40);
		contentPane.add(button);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setIcon(new ImageIcon(EditarCategoria.class.getResource("/vista/iconos/cancel32.png")));
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnCancelar.setBounds(310, 116, 188, 40);
		contentPane.add(btnCancelar);
		
		lblYaExisteUna.setForeground(Color.RED);
		lblYaExisteUna.setHorizontalAlignment(SwingConstants.CENTER);
		lblYaExisteUna.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblYaExisteUna.setBounds(119, 78, 371, 34);
		contentPane.add(lblYaExisteUna);
	}

	protected void prepararParaActualizar(String nombreViejo, String nombreNuevo) {
		nombreNuevo=nombreNuevo.trim().toUpperCase();
		//Si verificar categoria da true significa que ya existe una con ese nombre
		if (miCoordinador.verificarCategoria(nombreNuevo)){
			lblYaExisteUna.setVisible(true);
		}else{
			miCoordinador.actualizarCategoria(nombreViejo, nombreNuevo);
			tfnombre.setText("");
			lblYaExisteUna.setVisible(false);
			dispose();
		}
	}

	public void setCoodinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}

	public void cargarLabel(String nombre) {
		nombreViejo=nombre;
		titulo.setText("¿Cual es el nuevo nombre para "+nombre+"?");
	}
}
