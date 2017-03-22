package vista;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controlador.Coordinador;
import modelo.Articulo;
import modelo.Conexion;
import modelo.Constantes;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private JTextField tfBusqueda;
	private Coordinador miCoordinador;
	private JButton btnBorrarArticulo = new JButton("Borrar Articulo");
	private JButton btnEditarArticulo = new JButton("Editar Articulo");
	private JRadioButton rbtnCategoria = new JRadioButton("Categoria");
	private JRadioButton rbtnNombre = new JRadioButton("Nombre");
	private ButtonGroup grupoDeBotones = new ButtonGroup();
	private TableRowSorter TRF;
	/**
	 * Create the frame.
	 */
	public Principal() {
		
		rbtnNombre.setSelected(true);
		grupoDeBotones.add(rbtnCategoria);
		grupoDeBotones.add(rbtnNombre);
		
		btnBorrarArticulo.setEnabled(false);
		btnEditarArticulo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				miCoordinador.nuevoDetalleArticulo(obtenerArticuloDesdeTabla());
			}
		});
		btnEditarArticulo.setEnabled(false);
		addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				actualizarLista();
			}
			@Override
			public void windowLostFocus(WindowEvent e) {
			}
		});
		setTitle("Vivero Sue\u00F1o Verde");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/vista/iconos/rose 64.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 641);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblViveroSueoVerde = new JLabel("Vivero Sue\u00F1o Verde");
		lblViveroSueoVerde.setFont(new Font("Segoe Print", Font.BOLD, 50));
		lblViveroSueoVerde.setBounds(288, 11, 498, 75);
		contentPane.add(lblViveroSueoVerde);
		
		JLabel lblListaDeArticulos = new JLabel("Lista de articulos:");
		lblListaDeArticulos.setIcon(new ImageIcon(Principal.class.getResource("/vista/iconos/find32.png")));
		lblListaDeArticulos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblListaDeArticulos.setBounds(10, 109, 219, 43);
		contentPane.add(lblListaDeArticulos);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 176, 741, 426);
		contentPane.add(scrollPane);
		
		
		///TABLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		
		table = new JTable();
		modelo = new DefaultTableModel(){
			 @Override
			   public boolean isCellEditable(int fila, int columna) {
			       return false; //Con esto conseguimos que la tabla no se pueda editar
			   }
		};
		TRF=new TableRowSorter(modelo);
		table.setRowSorter(TRF);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(table.getSelectedRow()!=-1){
					btnBorrarArticulo.setEnabled(true);
					btnEditarArticulo.setEnabled(true);
				}
				if (arg0.getClickCount()==2){
					miCoordinador.nuevoDetalleArticulo(obtenerArticuloDesdeTabla());
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(table.getSelectedRow()!=-1){
					btnBorrarArticulo.setEnabled(true);
					btnEditarArticulo.setEnabled(true);
				}
			}
		});
		
		table.setRowHeight(24);
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		

		table.setModel(modelo);
		modelo.addColumn("Nombre");
		modelo.addColumn("Precio");
		modelo.addColumn("Categoria");
		table.getColumnModel().getColumn(0).setMaxWidth(350);
		table.getColumnModel().getColumn(0).setMinWidth(350);
		table.getColumnModel().getColumn(2).setMaxWidth(200);
		table.getColumnModel().getColumn(2).setMinWidth(200);
		table.getTableHeader().setReorderingAllowed(false);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(render);
		table.setSelectionMode(NORMAL);

		// TABLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		
		
		actualizarLista();
		scrollPane.setViewportView(table);
		tfBusqueda = new JTextField();
		tfBusqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int columna;
				if (rbtnNombre.isSelected()){
					columna=0;
				}else{
					columna=2;
				}
				if (tfBusqueda.getText().length()>=3){
					filtrar(tfBusqueda.getText().trim().toUpperCase(),columna);
					//miCoordinador.realizarBusqueda(tfBusqueda.getText());
				}else{
					filtrar("",columna);
				}
			}
		});
		tfBusqueda.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tfBusqueda.setBounds(223, 114, 528, 35);
		contentPane.add(tfBusqueda);
		
		JButton btnNuevoArticulo = new JButton("Nuevo Articulo");
		btnNuevoArticulo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				miCoordinador.nuevoArticulo();
			}
		});
		btnNuevoArticulo.setIcon(new ImageIcon(Principal.class.getResource("/vista/iconos/addGreen32.png")));
		btnNuevoArticulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNuevoArticulo.setBounds(779, 264, 219, 40);
		contentPane.add(btnNuevoArticulo);

		btnEditarArticulo.setIcon(new ImageIcon(Principal.class.getResource("/vista/iconos/edit32.png")));
		btnEditarArticulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnEditarArticulo.setBounds(779, 315, 219, 40);
		contentPane.add(btnEditarArticulo);
		
		btnBorrarArticulo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que queres borrarlo?", getTitle(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if(valor==0){
					miCoordinador.borrarArticulo((String)modelo.getValueAt(table.getSelectedRow(),0));
				}
			}
		});
		btnBorrarArticulo.setIcon(new ImageIcon(Principal.class.getResource("/vista/iconos/delete32.png")));
		btnBorrarArticulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnBorrarArticulo.setBounds(779, 366, 219, 40);
		contentPane.add(btnBorrarArticulo);
		
		JButton btnNuevaCategoria = new JButton("Nueva Categoria");
		btnNuevaCategoria.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				miCoordinador.nuevaCategoria();
			}
		});
		btnNuevaCategoria.setIcon(new ImageIcon(Principal.class.getResource("/vista/iconos/addBlue32.png")));
		btnNuevaCategoria.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNuevaCategoria.setBounds(779, 417, 219, 40);
		contentPane.add(btnNuevaCategoria);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Principal.class.getResource("/vista/iconos/tulipan64.png")));
		label.setBounds(10, 22, 64, 64);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Principal.class.getResource("/vista/iconos/flores64.png")));
		label_1.setBounds(934, 11, 64, 64);
		contentPane.add(label_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 97, 988, 2);
		contentPane.add(separator);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(Principal.class.getResource("/vista/iconos/bosque64.png")));
		label_2.setBounds(840, 22, 64, 64);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(Principal.class.getResource("/vista/iconos/semilla64.png")));
		label_3.setBounds(97, 11, 64, 64);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(Principal.class.getResource("/vista/iconos/flores2-64.png")));
		label_4.setBounds(185, 22, 64, 64);
		contentPane.add(label_4);
		
		JButton btnEditarCategorias = new JButton("Ver Categorias");
		btnEditarCategorias.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			miCoordinador.mostrarDetalleCategorias();
			}
		});
		btnEditarCategorias.setIcon(new ImageIcon(Principal.class.getResource("/vista/iconos/check32.png")));
		btnEditarCategorias.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnEditarCategorias.setBounds(779, 468, 219, 40);
		contentPane.add(btnEditarCategorias);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(761, 176, 2, 426);
		contentPane.add(separator_1);
		
		rbtnCategoria.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rbtnCategoria.setBounds(887, 129, 111, 23);
		contentPane.add(rbtnCategoria);
		
		rbtnNombre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rbtnNombre.setBounds(776, 129, 109, 23);
		contentPane.add(rbtnNombre);
		
		JLabel lblBuscarPor = new JLabel("Buscar por:");
		lblBuscarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscarPor.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBuscarPor.setBounds(761, 98, 219, 24);
		contentPane.add(lblBuscarPor);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(20, 163, 988, 2);
		contentPane.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(761, 104, 2, 48);
		contentPane.add(separator_3);
	}
	protected void filtrar(String text,int columna) {
		TRF.setRowFilter(RowFilter.regexFilter(text,columna));		
	}

	protected Articulo obtenerArticuloDesdeTabla() {
		Articulo art= new Articulo();
		art.setNombre((String)modelo.getValueAt(table.getSelectedRow(), 0));
		String s=(String)modelo.getValueAt(table.getSelectedRow(), 1);
		int indice=s.indexOf('.');
		if (indice!=-1){
			s=s.substring(0, indice)+s.substring(indice+1,s.length());
		}
		s=s.replace(',','.');
		art.setPrecio(Float.valueOf(s));
		art.setCategoria((String)modelo.getValueAt(table.getSelectedRow(), 2));
		return art;
	}
	public void actualizarLista() {
		vaciarTabla();
		btnBorrarArticulo.setEnabled(false);
		btnEditarArticulo.setEnabled(false);
		Conexion con= new Conexion();
		try {
			con.conectar();
			PreparedStatement st = con.getConnection().prepareStatement(Constantes.Select_all_join_articulosYcategorias);
			ResultSet rs = st.executeQuery();
			//codigo para ponerle 2 decimales a los float		
			DecimalFormat df = new DecimalFormat();
			df.setMinimumFractionDigits(2);
			df.setMaximumFractionDigits(2);
			while (rs.next()){
				Object[] fila = new Object[3];
				fila[0]=rs.getString(Constantes.NOMBRE_DE_ARTICULO);
				fila[1]=df.format(rs.getFloat(Constantes.PRECIO_DE_ARTICULO));
				fila[2]=rs.getString(Constantes.NOMBRE_DE_CATEGORIA); 
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
	public void vaciarTabla(){
		while (modelo.getRowCount()>0){
			modelo.removeRow(0);
		}
	}
	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}
}
