package vista;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Coordinador;
import controlador.EditarCategoria;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Confirmacion extends JFrame {

	private JPanel contentPane;
	private Coordinador miCoordinador;
	private boolean estado=false;
	
	public Confirmacion() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Confirmacion.class.getResource("/vista/iconos/flowers128.png")));
		setTitle("Vivero Sue\u00F1o Verde");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 130);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblborrar = new JLabel("\u00BFBorrar?");
		lblborrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblborrar.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblborrar.setBounds(10, 11, 424, 27);
		contentPane.add(lblborrar);
		
		JButton btnSi = new JButton("Borrar");
		btnSi.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				estado=true;
				dispose();
			}
		});
		btnSi.setIcon(new ImageIcon(Confirmacion.class.getResource("/vista/iconos/accept32.png")));
		btnSi.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnSi.setBounds(66, 51, 153, 40);
		contentPane.add(btnSi);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				estado=false;
				dispose();
			}
		});
		btnCancelar.setIcon(new ImageIcon(Confirmacion.class.getResource("/vista/iconos/cancel32.png")));
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnCancelar.setBounds(229, 51, 153, 40);
		contentPane.add(btnCancelar);
	}
		public boolean confirmar(boolean estado){
			return estado;
		}
}
