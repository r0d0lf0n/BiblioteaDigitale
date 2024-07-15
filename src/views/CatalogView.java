package views;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CatalogView extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable catalogTable;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CatalogView frame = new CatalogView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public CatalogView() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  WindowEvent windowClosing = new WindowEvent(CatalogView.this, WindowEvent.WINDOW_CLOSING);
	              Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowClosing);
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnClose, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnClose);

		JButton btnLoadData = new JButton("Load data");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnLoadData, 20, SpringLayout.NORTH, contentPane);
		btnLoadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnLoadData, 0, SpringLayout.WEST, btnClose);
		contentPane.add(btnLoadData);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnRefresh, 17, SpringLayout.SOUTH, btnLoadData);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnRefresh, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnRefresh, 0, SpringLayout.EAST, btnLoadData);
		contentPane.add(btnRefresh);

		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 36, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 85, SpringLayout.EAST, btnLoadData);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -20, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, contentPane);
		contentPane.add(scrollPane);

		catalogTable = new JTable();
		scrollPane.setViewportView(catalogTable);
		sl_contentPane.putConstraint(SpringLayout.WEST, catalogTable, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, catalogTable, -211, SpringLayout.NORTH, btnClose);
		sl_contentPane.putConstraint(SpringLayout.EAST, catalogTable, -420, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, catalogTable, 85, SpringLayout.SOUTH, btnRefresh);
		
		this.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {

	             dispose();
	         }
	     });
	}

	public JTable getCatalogTable() {
		return catalogTable;
	}

	public void setCatalogTable(JTable catalogTable) {
		this.catalogTable = catalogTable;
	}
}
