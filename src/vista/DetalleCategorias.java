package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controlador.Coordinador;
import modelo.Conexion;

import java.awt.Font;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class DetalleCategorias extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private Coordinador miCoordinador;
	private JButton btnBorrarCategoria = new JButton("Borrar Categoria");
	private JButton btnEditarCategoria = new JButton("Editar Categoria");
	private Confirmacion confirmacion=new Confirmacion();
	private boolean estado=false;
	
	public DetalleCategorias() {
		btnBorrarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que queres borrarlo?", getTitle(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if(valor==0){
					miCoordinador.borrarCategoria((String)modelo.getValueAt(table.getSelectedRow(),0));
				}
			}
		});
		btnBorrarCategoria.setEnabled(false);
		btnEditarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCoordinador.editarCategoria((String)modelo.getValueAt(table.getSelectedRow(),0));
			}
		});
		btnEditarCategoria.setEnabled(false);
		setTitle("Vivero Sue\u00F1o Verde");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DetalleCategorias.class.getResource("/vista/iconos/ramo128.png")));
		setFont(new Font("Tahoma", Font.PLAIN, 12));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 515, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCategorias = new JLabel("Categorias:");
		lblCategorias.setBounds(5, 5, 230, 67);
		lblCategorias.setFont(new Font("Verdana", Font.PLAIN, 38));
		contentPane.add(lblCategorias);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(5, 74, 494, 36);
		contentPane.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(5, 83, 230, 333);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow()!=-1){
					btnBorrarCategoria.setEnabled(true);
					btnEditarCategoria.setEnabled(true);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(table.getSelectedRow()!=-1){
					btnBorrarCategoria.setEnabled(true);
					btnEditarCategoria.setEnabled(true);
				}
			}
		});

		table.setRowHeight(24);
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		modelo = new DefaultTableModel(){
			 @Override
			   public boolean isCellEditable(int fila, int columna) {
			       return false; //Con esto conseguimos que la tabla no se pueda editar
			   }
		};

		table.setModel(modelo);
		modelo.addColumn("Categoria");
		table.getTableHeader().setReorderingAllowed(false);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(render);
		table.setSelectionMode(NORMAL);
		
		cargarTabla();
		//Codigo autogenerado! 
		scrollPane.setViewportView(table);
		btnEditarCategoria.setIcon(new ImageIcon(DetalleCategorias.class.getResource("/vista/iconos/edit32.png")));
		btnEditarCategoria.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnEditarCategoria.setBounds(245, 274, 254, 40);
		contentPane.add(btnEditarCategoria);
		
		JButton btnNuevaCategoria = new JButton("Nueva Categoria");
		btnNuevaCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCoordinador.nuevaCategoria();
			}
		});
		btnNuevaCategoria.setIcon(new ImageIcon(DetalleCategorias.class.getResource("/vista/iconos/addGreen32.png")));
		btnNuevaCategoria.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNuevaCategoria.setBounds(245, 223, 254, 40);
		contentPane.add(btnNuevaCategoria);
		
		btnBorrarCategoria.setIcon(new ImageIcon(DetalleCategorias.class.getResource("/vista/iconos/delete32.png")));
		btnBorrarCategoria.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnBorrarCategoria.setBounds(245, 325, 254, 40);
		contentPane.add(btnBorrarCategoria);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(DetalleCategorias.class.getResource("/vista/iconos/ramo128.png")));
		lblNewLabel.setBounds(312, 84, 128, 128);
		contentPane.add(lblNewLabel);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			dispose();
			}
		});
		btnCerrar.setIcon(new ImageIcon(DetalleCategorias.class.getResource("/vista/iconos/cancel32.png")));
		btnCerrar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCerrar.setBounds(245, 376, 254, 40);
		contentPane.add(btnCerrar);
	}

	private void cargarTabla() {
		vaciarTabla();
		btnEditarCategoria.setEnabled(false);
		btnBorrarCategoria.setEnabled(false);
		Conexion con= new Conexion();
		try {
			con.conectar();
			PreparedStatement st = con.getConnection().prepareStatement("select "
					+
					"nombreCat from categorias order by nombreCat"
					//"nombreArt,precioArt,categorias.nombreCat FROM articulos JOIN categorias on articulos.categoriaArt=categorias.idCat order by nombreArt"
					+ ";");
			ResultSet rs = st.executeQuery();
			
			
			//Mediante una estructura con un while se va cargando la tabla elemento a elemento y atributo a atributo
			while (rs.next()){
				////Creamos un Objeto con tantos parámetros como datos retorne cada fila 
                // de la consulta
				Object[] fila = new Object[1];

				fila[0]=rs.getString("nombreCat"); //se repite nombre porque hace referencia al nombre de la tabla categoria
				modelo.addRow(fila);
			}				
			table.updateUI(); //actualizar la tabla por los cambios		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "No se cargaron los articulos de la base de datos correctamente.");		
		}finally {
			con.cerrar();
		}
	}

	private void vaciarTabla() {
		while (modelo.getRowCount()>0){
			modelo.removeRow(0);
		}
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}

	public void actualizarTablas() {
		cargarTabla();
	}
}
