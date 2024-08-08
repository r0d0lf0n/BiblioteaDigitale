package views.Catalog;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class InsertBookView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldTitle;
	private JTextField textFieldAuthor;
	private JLabel lblAuthor;
	private JTextField textYear;
	private JLabel lblYear;
	private JLabel lblDescription;
	private JTextField textFieldDescription;
	private JButton btnSave;
	private JButton btnClose;
	//private static JFrame mainFrame;
	//static LandingPage landing;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
		//landing = new LandingPage();
		mainFrame = new InsertBookView();
		mainFrame.setVisible(true);
		mainFrame.setLocationByPlatform(isDefaultLookAndFeelDecorated());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
//	        	System.out.println("Window is closing...");
				//landing.setVisible(true);
//	            // Action to perform when the window is closing
//	            int confirmed = JOptionPane.showConfirmDialog(
//	                mainFrame,
//	                "Are you sure you want to exit?",
//	                "Exit Confirmation",
//	                JOptionPane.YES_NO_OPTION
//	            );
//	
//	            if (confirmed == JOptionPane.YES_OPTION) {
//	                // Perform additional actions here if needed
//	                System.out.println("Window is closing...");
//	                mainFrame.dispose();
//	            }
				
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public InsertBookView() {
		setTitle("Insert a new Book");
		setBounds(100, 100, 450, 300);
        setLocationRelativeTo(null);


		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JLabel lblTitle = new JLabel("Title:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblTitle, 20, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblTitle, 10, SpringLayout.WEST, contentPane);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTitle);

		textFieldTitle = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldTitle, 6, SpringLayout.SOUTH, lblTitle);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldTitle, 0, SpringLayout.WEST, lblTitle);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldTitle, -277, SpringLayout.EAST, contentPane);
		contentPane.add(textFieldTitle);
		textFieldTitle.setColumns(10);

		textFieldAuthor = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldAuthor, 0, SpringLayout.NORTH, textFieldTitle);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldAuthor, 55, SpringLayout.EAST, textFieldTitle);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldAuthor, -69, SpringLayout.EAST, contentPane);
		textFieldAuthor.setColumns(10);
		contentPane.add(textFieldAuthor);

		lblAuthor = new JLabel("Author:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblAuthor, 0, SpringLayout.NORTH, lblTitle);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblAuthor, -174, SpringLayout.EAST, contentPane);
		lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAuthor);

		textYear = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.WEST, textYear, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textYear, 0, SpringLayout.EAST, textFieldTitle);
		textYear.setColumns(10);
		contentPane.add(textYear);

		lblYear = new JLabel("Year:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, textYear, 6, SpringLayout.SOUTH, lblYear);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblYear, 28, SpringLayout.SOUTH, textFieldTitle);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblYear, 0, SpringLayout.WEST, lblTitle);
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblYear);

		lblDescription = new JLabel("Description:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblDescription, 0, SpringLayout.NORTH, lblYear);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblDescription, 179, SpringLayout.EAST, lblYear);
		lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblDescription);

		textFieldDescription = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldDescription, 0, SpringLayout.NORTH, textYear);
		sl_contentPane.putConstraint(SpringLayout.WEST, textFieldDescription, 0, SpringLayout.WEST, textFieldAuthor);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textFieldDescription, -42, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textFieldDescription, -30, SpringLayout.EAST, contentPane);
		textFieldDescription.setColumns(10);
		contentPane.add(textFieldDescription);

		btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnSave, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnSave, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnSave);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnClose, 0, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, contentPane);
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WindowEvent windowClosing = new WindowEvent(InsertBookView.this, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowClosing);
            }
        });
		contentPane.add(btnClose);
		
		 this.addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	             dispose();
	         }
	     });
		 
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSave) {
			textFieldDescription.setText("");
			textYear.setText("");
			textFieldAuthor.setText("");
			textFieldTitle.setText("");
			JOptionPane.showMessageDialog(null, "Book saved successfully!", "Book saved", JOptionPane.PLAIN_MESSAGE);
//			this.dispose();
		}
	}
	
}
