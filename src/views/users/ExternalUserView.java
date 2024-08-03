package views.users;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controllers.views.ExternalUserController;
import utils.Observer;

public class ExternalUserView extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;
    private ExternalUserController controller = null;
    private DefaultComboBoxModel<String> comboGenereModel = null;

    public ExternalUserView() {
        comboGenereModel = new DefaultComboBoxModel<>();
        controller = new ExternalUserController();

        setTitle("Ricerca Libri");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel genreLabel = new JLabel("Genere:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(genreLabel, gbc);

        JComboBox<String> genreComboBox = new JComboBox<>(comboGenereModel);
        gbc.gridx = 1;
        gbc.gridy = 0;
        searchPanel.add(genreComboBox, gbc);

        JLabel authorLabel = new JLabel("Autore:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(authorLabel, gbc);

        JTextField authorField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        searchPanel.add(authorField, gbc);

        JLabel titleLabel = new JLabel("Titolo:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        searchPanel.add(titleLabel, gbc);

        JTextField titleField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        searchPanel.add(titleField, gbc);

        JButton searchButton = new JButton("Cerca");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.search(titleField.getText(), authorField.getText(), genreComboBox.getSelectedItem().toString());
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        searchPanel.add(searchButton, gbc);

        add(searchPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null); // Centra la finestra nello schermo
        setResizable(false); // Impedisce il ridimensionamento della finestra

        controller.addObserver(this);
        populatePanels();
    }

    private void populatePanels() {
        controller.getGenereList();
    }

    @Override
    public void update(String type, Object arg) {
        System.out.println("Observer Notification");

        if (type.equals("COMBOBOX_GENERI")) {
            comboGenereModel.removeAllElements();
            for (String item : (String[]) arg) {
                comboGenereModel.addElement(item); // Aggiungi gli elementi aggiornati al modello
            }
        }
        if(type.equals("SEARCH_RESULTS")) {
        	System.out.println("SEARCH RESULTS"); //TODO
        }
    }
}
