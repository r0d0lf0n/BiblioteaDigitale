package views.users;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controllers.views.ExternalUserController;
import controllers.views.LandingPageController;
import models.users.Utente;
import utils.Observer;

public class ExternalUserView extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;
    private ExternalUserController controller = null;
    private Utente user = null;

    public ExternalUserView(LandingPageController landingPageController, Utente user) {
    	this.user = user;
        controller = new ExternalUserController();

        setTitle("Ricerca Libri - Utente esterno");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 300); 
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
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

        // Bottone di ricerca
        JButton searchButton = new JButton("Cerca");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.search(
                    isbnField.getText(),
                    authorField.getText(),
                    titleField.getText(),
                    publisherField.getText(),
                    yearField.getText(),
                    user
                );
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER; 
        searchPanel.add(searchButton, gbc);

        add(searchPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null); 
        setResizable(false); 

        landingPageController.addObserver(this);
        controller.addObserver(this);
        
      }


 
    @Override
    public void update(String type, Object arg) {

        if(type.equals("SEARCH_RESULTS")) {
        	System.out.println("SEARCH RESULTS"); //TODO
        }
        
		if(type.equals("OPEN_SEARCH")) {
			this.setVisible(true);
		}
		if(type.equals("CLOSE_SEARCH")){
			this.setVisible(false);
		}
    }
}
