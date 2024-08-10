package views.users;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controllers.views.LandingPageController;
import controllers.views.RegisteredUserController;
import models.users.Utente;
import utils.Observer;
import views.Loan.LoanView;
import views.Loan.LoanViewForUser;

public class RegisteredUserView extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;
    private final RegisteredUserController controller;
    //private final LoansController loansController;
    //private DefaultComboBoxModel<String> comboGenereModel = null;
	//public LoansDetailView loansDetailView = null;
	private Utente user = null;

    
    public RegisteredUserView(LandingPageController landingPageController, Utente user) {
    	 this.controller = new RegisteredUserController();
        // this.loansController = new LoansController();
         //this.comboGenereModel = new DefaultComboBoxModel<>();
         this.user = user;

         setTitle("Gestione Biblioteca - Utente Registrato");
         setSize(500, 500); 
         setLayout(new BorderLayout());

         
         JPanel userDataPanel = new JPanel(new GridLayout(4, 2, 10, 5));
         userDataPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

         JLabel lblNome = new JLabel("Nome:");
         JTextField txtNome = new JTextField(user.getNome());
         txtNome.setEditable(false);
         userDataPanel.add(lblNome);
         userDataPanel.add(txtNome);

         JLabel lblCognome = new JLabel("Cognome:");
         JTextField txtCognome = new JTextField(user.getCognome());
         txtCognome.setEditable(false);
         userDataPanel.add(lblCognome);
         userDataPanel.add(txtCognome);

         JLabel lblCodiceFiscale = new JLabel("Codice Fiscale:");
         JTextField txtCodiceFiscale = new JTextField(user.getCodiceFiscale());
         txtCodiceFiscale.setEditable(false);
         userDataPanel.add(lblCodiceFiscale);
         userDataPanel.add(txtCodiceFiscale);

         JLabel lblNumeroTessera = new JLabel("Numero Tessera:");
         JTextField txtNumeroTessera = new JTextField(String.valueOf(user.getIdTessera()));
         txtNumeroTessera.setEditable(false);
         userDataPanel.add(lblNumeroTessera);
         userDataPanel.add(txtNumeroTessera);

         // Pannello per i campi di ricerca
         JPanel searchPanel = new JPanel(new GridBagLayout());
         searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(5, 5, 5, 5);
         gbc.fill = GridBagConstraints.HORIZONTAL;
         gbc.weightx = 1;
         gbc.gridx = 0; 

         // ISBN
         JLabel isbnLabel = new JLabel("ISBN:");
         gbc.gridy = 0;
         searchPanel.add(isbnLabel, gbc);

         JTextField isbnField = new JTextField(15);
         gbc.gridx = 1;
         searchPanel.add(isbnField, gbc);

         // Autore
         JLabel authorLabel = new JLabel("Autore:");
         gbc.gridx = 0;
         gbc.gridy = 1;
         searchPanel.add(authorLabel, gbc);

         JTextField authorField = new JTextField(15);
         gbc.gridx = 1;
         searchPanel.add(authorField, gbc);

         // Titolo
         JLabel titleLabel = new JLabel("Titolo:");
         gbc.gridx = 0;
         gbc.gridy = 2;
         searchPanel.add(titleLabel, gbc);

         JTextField titleField = new JTextField(15);
         gbc.gridx = 1;
         searchPanel.add(titleField, gbc);

         // Casa Editrice
         JLabel publisherLabel = new JLabel("Casa Editrice:");
         gbc.gridx = 0;
         gbc.gridy = 3;
         searchPanel.add(publisherLabel, gbc);

         JTextField publisherField = new JTextField(15);
         gbc.gridx = 1;
         searchPanel.add(publisherField, gbc);

         // Anno
         JLabel yearLabel = new JLabel("Anno:");
         gbc.gridx = 0;
         gbc.gridy = 4;
         searchPanel.add(yearLabel, gbc);

         JTextField yearField = new JTextField(15);
         gbc.gridx = 1;
         searchPanel.add(yearField, gbc);

         // Pulsante "CERCA"
         JButton searchButton = new JButton("CERCA");
         searchButton.addActionListener(e -> {
             controller.search(
                 isbnField.getText(),
                 authorField.getText(),
                 titleField.getText(),
                 publisherField.getText(),
                 yearField.getText(),
                 user
             );
         });
         gbc.gridx = 0;
         gbc.gridy = 5;
         gbc.gridwidth = 2;
         gbc.fill = GridBagConstraints.NONE; 
         gbc.anchor = GridBagConstraints.CENTER; 
         searchPanel.add(searchButton, gbc);

         JButton manageLoansButton = new JButton("Gestione Prestiti");
         manageLoansButton.addActionListener(e -> {
             //if (loansDetailView == null)
             LoanViewForUser loansView = new LoanViewForUser(landingPageController, user.getIdTessera());
             loansView.setVisible(true);
         });

         JButton closeButton = new JButton("Chiudi");
         closeButton.addActionListener(e -> {
             landingPageController.openLandingPanel();
         });

         JPanel buttonPanel = new JPanel();
         buttonPanel.add(manageLoansButton);
         buttonPanel.add(closeButton);

         add(userDataPanel, BorderLayout.NORTH);
         add(searchPanel, BorderLayout.CENTER);
         add(buttonPanel, BorderLayout.SOUTH);

         setLocationRelativeTo(null); // Centra la finestra nello schermo
         setResizable(false);
         setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

         controller.addObserver(this);
         landingPageController.addObserver(this);
    }


	@Override
    public void update(String type, Object arg) {
        
		if(type.equals("OPEN_SEARCH")) {
			this.setVisible(true);
		}
		
		if(type.equals("CLOSE_SEARCH")){
			this.setVisible(false);
		}
    }


}
