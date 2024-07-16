package views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import bibliotecaDigitale.CatalogMain;
import controllers.views.UserAdminController;
import utils.Observer;



public class UserAdminView extends JFrame implements Observer{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserAdminController controller = null;

	public UserAdminView(String firstName, String lastName, String codiceFiscale) {
        // Imposta il titolo della finestra
        setTitle("Pannello di amministrazione");

        JPanel contentPane = new JPanel();
        SpringLayout sl_contentPane = new SpringLayout();
        contentPane.setLayout(sl_contentPane);
        setContentPane(contentPane);

        JPanel userPanel = new JPanel(new GridLayout(3, 2));
        userPanel.setBorder(BorderFactory.createTitledBorder("Dati Utente"));
        sl_contentPane.putConstraint(SpringLayout.NORTH, userPanel, 10, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, userPanel, 10, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, userPanel, -10, SpringLayout.EAST, contentPane);
        
        userPanel.add(new JLabel("Nome:"));
        JTextField firstNameField = new JTextField(firstName);
        firstNameField.setEditable(false);
        userPanel.add(firstNameField);

        userPanel.add(new JLabel("Cognome:"));
        JTextField lastNameField = new JTextField(lastName);
        lastNameField.setEditable(false);
        userPanel.add(lastNameField);

        userPanel.add(new JLabel("Codice Fiscale:"));
        JTextField codiceFiscaleField = new JTextField(codiceFiscale);
        codiceFiscaleField.setEditable(false);
        userPanel.add(codiceFiscaleField);

        contentPane.add(userPanel);

        JButton btnInsertBook = new JButton("Open Insert Book Window");
        sl_contentPane.putConstraint(SpringLayout.NORTH, btnInsertBook, 20, SpringLayout.SOUTH, userPanel);
        sl_contentPane.putConstraint(SpringLayout.WEST, btnInsertBook, 10, SpringLayout.WEST, contentPane);
        btnInsertBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InsertBookView insertBook = new InsertBookView();
                insertBook.setVisible(true);
            }
        });
        contentPane.add(btnInsertBook);

        JButton btnShowCatalog = new JButton("Open Show Catalog Window");
        sl_contentPane.putConstraint(SpringLayout.NORTH, btnShowCatalog, 20, SpringLayout.SOUTH, btnInsertBook);
        sl_contentPane.putConstraint(SpringLayout.WEST, btnShowCatalog, 0, SpringLayout.WEST, btnInsertBook);
        btnShowCatalog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	CatalogMain catalog = new CatalogMain();
            }
        });
        contentPane.add(btnShowCatalog);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        controller = new UserAdminController();
        controller.addObserver(this);
    }


	@Override
	public void update(String type, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("Observer Notification");
		
	}

}
