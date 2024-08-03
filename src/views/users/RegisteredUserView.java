package views.users;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controllers.views.RegisteredUserController;
import models.users.Utente;
import utils.Observer;

public class RegisteredUserView extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;
    private final RegisteredUserController controller;
    private DefaultComboBoxModel<String> comboGenereModel = null;

    
    public RegisteredUserView(Utente utente) {
    	//new
        controller = new RegisteredUserController();
        comboGenereModel = new DefaultComboBoxModel<String>();

        // Impostazioni del layout per il pannello
        setLayout(new BorderLayout());
        setTitle("Utente Registrato"); // Titolo del bordo del pannello
        setSize(400, 300);

        // Pannello per i campi dati utente (non modificabili)
        JPanel userDataPanel = new JPanel(new GridLayout(4, 2, 10, 5));
        userDataPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Aggiunge margine

        // Campi dati utente
        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField(utente.getNome());
        txtNome.setEditable(false); // Non modificabile
        userDataPanel.add(lblNome);
        userDataPanel.add(txtNome);

        JLabel lblCognome = new JLabel("Cognome:");
        JTextField txtCognome = new JTextField(utente.getCognome());
        txtCognome.setEditable(false); // Non modificabile
        userDataPanel.add(lblCognome);
        userDataPanel.add(txtCognome);

        JLabel lblCodiceFiscale = new JLabel("Codice Fiscale:");
        JTextField txtCodiceFiscale = new JTextField(utente.getCodiceFiscale());
        txtCodiceFiscale.setEditable(false); // Non modificabile
        userDataPanel.add(lblCodiceFiscale);
        userDataPanel.add(txtCodiceFiscale);

        JLabel lblNumeroTessera = new JLabel("Numero Tessera:");
        JTextField txtNumeroTessera = new JTextField(String.valueOf(utente.getIdTessera()));
        txtNumeroTessera.setEditable(false); // Non modificabile
        userDataPanel.add(lblNumeroTessera);
        userDataPanel.add(txtNumeroTessera);

        // Pannello per i campi di ricerca
        JPanel searchPanel = new JPanel(new GridLayout(4,1,10,5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Aggiunge margine

        searchPanel.add(new JLabel("Genere letterario:"));
        searchPanel.add(new JComboBox<String>(comboGenereModel)); 
        
        searchPanel.add(new JLabel("Autore:"));
        searchPanel.add(new JTextField(15)); // Esempio di JTextField per l'autore
        
        searchPanel.add(new JLabel("Titolo:"));
        searchPanel.add(new JTextField(15)); // Esempio di JTextField per il titolo

        // Pulsante "CERCA"
        JButton searchButton = new JButton("CERCA");
        searchButton.addActionListener(e -> {
            // Qui va la logica per eseguire la ricerca
            System.out.println("Esegui ricerca..."); // Esempio di messaggio di log
        });
        searchPanel.add(searchButton); // Aggiunge il pulsante al pannello di ricerca

        // Pulsante "Gestione Prestiti"
        JButton manageLoansButton = new JButton("Gestione Prestiti");
        // Aggiungi qui l'azione per il pulsante "Gestione Prestiti"

        // Pulsante di chiusura
        JButton closeButton = new JButton("Chiudi");
        closeButton.addActionListener(e -> {
            // Chiudi il pannello o l'applicazione
            dispose(); // Chiude la finestra
        });

        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(manageLoansButton);
        buttonPanel.add(closeButton);

        // Aggiungi i componenti al pannello principale con il layout BorderLayout
        add(userDataPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Centra la finestra nello schermo
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Aggiunge l'observer
        controller.addObserver(this);
        
        populateData();
    }

    private void populateData() {
		// TODO Auto-generated method stub
		controller.getGenereList();
	}

	@Override
    public void update(String type, Object arg) {
        // TODO Auto-generated method stub
        System.out.println("Observer Notification");
        
        if(type.equals("COMBOBOX_GENERI")) {
        	comboGenereModel.removeAllElements();
        	for (String item : (String[])arg) {
        		comboGenereModel.addElement(item); // Add updated items to the model
            }
        }
    }


}
