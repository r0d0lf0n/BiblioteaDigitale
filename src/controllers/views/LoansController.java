package controllers.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bibliotecaDigitale.CatalogMain;
import database.Loan;
import models.bl.LoanModel;
import views.LoanView;

public class LoansController {
	private List<Loan> loans;
	private LoanView loanView;
	private DefaultTableModel model;
	private JTable loanTable;
	private JLabel noLoansBtn;
	private LoanModel loanModel;
	private JDialog d;

	public LoansController(LoanView view, LoanModel loanModel) {
		loanView = view;
		this.loanModel = loanModel;
		getLoans();
		getComponents();	
		
		if (loans.size() > 0) {
			configView();
		} else {
			showNoLoansLabel(true);
			showDialog();
		}
	}
	
	private void configView() {
		showNoLoansLabel(false);
		showView();
		InitialiazeTable();
	}
	
	private void showView() {
		loanView.setVisible(true);
	}
	
	private void getComponents() {
		loanTable = loanView.getLoanTable();
		noLoansBtn = loanView.getNoLoanBtn();
	}
	
	private void getLoans() {
		loans = loanModel.getAllLoans();
	}

	private void showNoLoansLabel(boolean flag) {
		if (flag) {
			noLoansBtn.setVisible(true);
		} else {
			noLoansBtn.setVisible(false);
			showDialog();
		}
	}
	
	private void InitialiazeTable() {
		Object[] columns = { "id", "User", "Book" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		for(int i = 1; i < loans.size(); i++) {
//			System.out.println(loans.get(i)[0]);
				model.addRow(new Object[] {i, loans.get(i).getUser_id(), loans.get(i).getBook_id()});
		}

		loanTable.setModel(model);
		loanView.setLoanTable(loanTable);
	}
	
	private void showDialog() {
		d = new JDialog(loanView, "Warning!");

        JLabel l = new JLabel("There are no loand yet!");

        JButton newLoan = new JButton("New Loan");
        JButton cancel = new JButton("Cancel");

        newLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New loan!");
			}
		});
        
        cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Closing dialog!");
				d.setVisible(false);
			}
		});

        // create a panel
        JPanel p = new JPanel();

        p.add(newLoan);
        p.add(cancel);
        p.add(l);

        // add panel to dialog
        d.add(p);
        d.setSize(280, 100);
        d.setVisible(true);
	}
}