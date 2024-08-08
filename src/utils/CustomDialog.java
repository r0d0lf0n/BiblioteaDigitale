package utils;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomDialog {
    JButton btnOne;
    JButton btnTwo;
	
	public JDialog buildDialog(JFrame view, String title, String msg, String btnOneText, String btnTwoText, 
			int xCoordinate, int yCoordinate, int width, int height) {
		JDialog d = new JDialog(view, title);
		d.setLocation(xCoordinate, yCoordinate);
	    JLabel l = new JLabel(msg);

	    btnOne = new JButton(btnOneText);
	    btnTwo = new JButton(btnTwoText);

	    JPanel p = new JPanel();
	    p.add(btnOne);
	    p.add(btnTwo);
	    p.add(l);

	    d.add(p);
	    d.setSize(width, height);
	    return d;
	}
	
	public JButton getButtonOne() {
		return btnOne;
	}
	
	public JButton getButtonTwo() {
		return btnTwo;
	}
}