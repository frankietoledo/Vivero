package vista;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controlador.Coordinador;
import modelo.Conexion;
import modelo.Constantes;

public class DetalleCategorias extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private Coordinador miCoordinador;
	private JButton btnBorrarCategoria = new JButton("Borrar Categoria");
	private JButton btnEditarCategoria = new JButton("Editar Categoria");
	private boolean estado=false;
	
	public DetalleCategorias() {
		btnBorrarCategoria.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que queres borrarlo?", getTitle(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if(valor==0){
					miCoordinador.borrarCategoria((String)modelo.getValueAt(table.getSelectedRow(),0));
				}
			}
		});
				
		btnBorrarCategoria.setEnabled(false);
		btnEditarCategoria.addActionListener(new ActionListener() {
			@Override
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
				if (e.getClickCount()==2) {
					miCoordinador.editarCategoria((String)modelo.getValueAt(table.getSelectedRow(),0));
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
			@Override
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
			@Override
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
		//miCoordinador.cargarTablaCategorias(table);
		Conexion con= new Conexion();
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		try {
			con.conectar();
			PreparedStatement st = con.getConnection().prepareStatement(Constantes.Select_NombreCat_from_Categorias_orderByNombre);
			ResultSet rs = st.executeQuery();	
			//Mediante una estructura con un while se va cargando la tabla elemento a elemento y atributo a atributo
			while (rs.next()){
				////Creamos un Objeto con tantos parámetros como datos retorne cada fila 
                // de la consulta
				Object[] fila = new Object[1];
				fila[0]=rs.getString(Constantes.NOMBRE_DE_CATEGORIA); 
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
