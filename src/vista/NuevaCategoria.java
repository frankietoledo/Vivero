package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.Coordinador;

public class NuevaCategoria extends JFrame {

	private JPanel contentPane;
	private JTextField tfNombre;
	private Coordinador miCoordinador;
	private JLabel lblYaExisteUna = new JLabel("Ya existe una categoria con este nombre");

	/**
	 * Create the frame.
	 */
	public NuevaCategoria() {
		
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
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(NuevaCategoria.class.getResource("/vista/iconos/flowers128.png")));
		setResizable(false);
		setTitle("Vivero Sue\u00F1o Verde");
		setBounds(100, 100, 733, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblYaExisteUna.setVisible(false);
		JLabel lblAgregarNuevaCategoria = new JLabel("Agregar nueva categoria:");
		lblAgregarNuevaCategoria.setBounds(52, 11, 488, 62);
		lblAgregarNuevaCategoria.setFont(new Font("Verdana", Font.PLAIN, 38));
		contentPane.add(lblAgregarNuevaCategoria);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 71, 551, 2);
		contentPane.add(separator);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(NuevaCategoria.class.getResource("/vista/iconos/flowers128.png")));
		label.setBounds(595, 22, 128, 196);
		contentPane.add(label);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNombre.setBounds(52, 96, 98, 27);
		contentPane.add(lblNombre);
		
		tfNombre = new JTextField();
		tfNombre.setFont(new Font("Tahoma", Font.PLAIN, 22));
		tfNombre.setBounds(173, 96, 367, 27);
		contentPane.add(tfNombre);
		tfNombre.setColumns(10);
		
		JButton button = new JButton("Guardar");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = tfNombre.getText().trim().toUpperCase();
				System.out.println(nombre);
				//Verificar categoria te da true si existe la categoria en bd
				if (miCoordinador.verificarCategoria(nombre)){
					lblYaExisteUna.setVisible(true);
				}else{
					//si no existe la categoria se la envia , se borra el campo y se setea invisible el lblYaExisteUna
					miCoordinador.guardarCategoria(nombre);
					tfNombre.setText("");
					lblYaExisteUna.setVisible(false);
					dispose();
				}
			}
		});
		button.setIcon(new ImageIcon(NuevaCategoria.class.getResource("/vista/iconos/accept32.png")));
		button.setFont(new Font("Tahoma", Font.PLAIN, 22));
		button.setBounds(52, 161, 188, 40);
		contentPane.add(button);
		
		JButton button_1 = new JButton("Cancelar");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_1.setIcon(new ImageIcon(NuevaCategoria.class.getResource("/vista/iconos/cancel32.png")));
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		button_1.setBounds(320, 161, 188, 40);
		contentPane.add(button_1);
		
		lblYaExisteUna.setForeground(Color.RED);
		lblYaExisteUna.setHorizontalAlignment(SwingConstants.CENTER);
		lblYaExisteUna.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblYaExisteUna.setBounds(52, 121, 488, 40);
		contentPane.add(lblYaExisteUna);
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}
}
