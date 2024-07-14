package Biblio;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import InsertBook.InsertBookView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class App {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LandingPageNew landing = new LandingPageNew();
		landing.setVisible(true);
	}
}
