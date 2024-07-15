/**
 * 
 */
package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controllers.ExternalUserController;
import models.Observer;

public class ExternalUserView extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExternalUserController controller = null;
	
    public ExternalUserView() {
        setTitle("Ricerca Libri");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(400, 300);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Pannello per i controlli della ricerca
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(4, 2, 10, 10)); // GridLayout con 4 righe, 2 colonne
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margine esterno

        // Combo box per i generi letterari
        String[] generiLetterari = {"Romanzo", "Fantasy", "Giallo", "Storico", "Fantascienza"};
        JComboBox<String> genreComboBox = new JComboBox<>(generiLetterari);
        searchPanel.add(new JLabel("Genere:"));
        searchPanel.add(genreComboBox);

        // Campi di testo per autore e titolo
        JTextField authorField = new JTextField();
        searchPanel.add(new JLabel("Autore:"));
        searchPanel.add(authorField);

        JTextField titleField = new JTextField();
        searchPanel.add(new JLabel("Titolo:"));
        searchPanel.add(titleField);

        // Pulsante Cerca
        JButton searchButton = new JButton("Cerca");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logica per la ricerca dei libri
                String selectedGenre = (String) genreComboBox.getSelectedItem();
                String author = authorField.getText();
                String title = titleField.getText();

                // Simulazione della ricerca (stampiamo i parametri per ora)
                System.out.println("Genere selezionato: " + selectedGenre);
                System.out.println("Autore inserito: " + author);
                System.out.println("Titolo inserito: " + title);

                // Qui puoi implementare la logica per mostrare i risultati
                // ad esempio, in una nuova finestra o in un'altra parte dell'applicazione
            }
        });
        searchPanel.add(searchButton);

        contentPane.add(searchPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null); // Centra la finestra nello schermo
        setResizable(false); // Impedisce il ridimensionamento della finestra
        
        controller = new ExternalUserController();
        controller.addObserver(this);
    }


	@Override
	public void update(Object arg) {
		// TODO Auto-generated method stub
		System.out.println("Observer Notification");
	}

}

