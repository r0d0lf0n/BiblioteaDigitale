package views.Landing;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controllers.bl.GestoreCatalogo;
import controllers.views.LandingPageController;
import utils.Observer;
import views.Catalog.CatalogView;

public class LandingPageView extends JFrame implements Observer{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JButton btnOpenLoans;
    private JButton btnOpenCatalog;
    private JButton btnOpenAdvancedSearch;
    private LandingPageController controller = null;

    public LandingPageView() {
    	
        controller = new LandingPageController();

    	
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 300, 200); 
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createTitledBorder("Biblioteca Digitale - Pannello di controllo"));
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        contentPane.setLayout(new BorderLayout()); // Utilizzo di BorderLayout

        // Listener per la chiusura della finestra
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Sei sicuro di voler chiudere l'applicazione?",
                        "Conferma Chiusura",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (response == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 5, 5)); 

        btnOpenLoans = new JButton("Gestore Prestiti");
        btnOpenLoans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Azione per il pulsante Open Loans
            	if(GestoreCatalogo.getInstance().isLoadingInProgress()) {
            		JOptionPane.showMessageDialog(LandingPageView.this, 
            				"Caricamento catalogo in corso, altre operazioni potrebbero portare inconsistenza sul sistema",
                            "ATTENZIONE",
                            JOptionPane.WARNING_MESSAGE);
            		
            	}
            	controller.openLoansPanel();
               // System.out.println("Open Loans clicked");
            }
        });
        buttonPanel.add(btnOpenLoans);

        btnOpenCatalog = new JButton("Gestore Catalogo");
        btnOpenCatalog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Azione per il pulsante Open Catalog
            	controller.openCatalogPanel();
            	
                //System.out.println("Open Catalog clicked");
            }
        });
        buttonPanel.add(btnOpenCatalog);

        btnOpenAdvancedSearch = new JButton("Ricerca Libri");
        btnOpenAdvancedSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Azione per il pulsante Advanced Search
            	if(GestoreCatalogo.getInstance().isLoadingInProgress()) {
            		JOptionPane.showMessageDialog(LandingPageView.this, 
            				"Caricamento catalogo in corso, altre operazioni potrebbero portare inconsistenza sul sistema",
                            "ATTENZIONE",
                            JOptionPane.WARNING_MESSAGE);
            		
            	}
            	controller.openSearchPanel();
               // System.out.println("Advanced Search clicked");
            }
        });
        buttonPanel.add(btnOpenAdvancedSearch);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
        
        controller.addObserver(this);
        
    }

	@Override
	public void update(String type, Object arg) {
		if(type.equals("OPEN_LANDING")) {
			this.setVisible(true);
		}
		if(type.equals("CLOSE_LANDING")){
			this.setVisible(false);
		}
	}

	public LandingPageController getLandingController() {
		return controller;
	}

}
