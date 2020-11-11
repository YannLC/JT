package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import bll.Controler;
import bo.Item;
import bo.Java2sAutoComboBox;
import bo.Java2sAutoTextField;
import dal.DAO;
import dal.Factory;
import dal.exceptions.DALException;
import dal.jdbc.ItemDAOJdbcImpl;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;


public class Gui extends JFrame implements WindowListener,ActionListener {
	JButton reset = new JButton("Reset");
	JButton send = new JButton("Send");
	Java2sAutoComboBox combox2;
	Java2sAutoComboBox precision1;
	Java2sAutoComboBox precision2;
	Java2sAutoComboBox precision3;

	JTextArea ta = new JTextArea();
	JCheckBox checkbox_Ech = new JCheckBox("Echauffement");
	JCheckBox checkbox_Ed = new JCheckBox("Educatifs");
	JCheckBox checkbox_Tech = new JCheckBox("Techniques");
	JCheckBox checkbox_Ench = new JCheckBox("Enchainement");
	JCheckBox checkbox_Et = new JCheckBox("Etirement");

	List<String> PotentialString = new ArrayList();
	String typeSelected = "";
	String keyWord = "";

	public Gui() throws DALException{
		//Creating the Frame
		JFrame frame = new JFrame("Request Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);


		addWindowListener(this);

		// Big Jpanel
		JPanel bigpanel = new JPanel();

		//Creating the panel at the top and adding components
		JPanel panel = new JPanel(); // the panel is not visible in output
		JLabel label = new JLabel("Enter Text");
		reset.addActionListener(this);
		send.addActionListener(this);
		//typeList.addActionListener(this);

		// tf not used anymore for now

		DAO<Item> ItemDAO;
		ItemDAO = Factory.getItemDAO();
		this.PotentialString = ((ItemDAOJdbcImpl) ItemDAO).getAllNames();
		this.PotentialString.add("-");
		this.PotentialString.sort(Comparator.comparing( String::toString ));
		combox2 = new Java2sAutoComboBox(this.PotentialString);

		combox2.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	// Getting the current keyword
		    	String keyWord_temp = PotentialString.get(combox2.getSelectedIndex());
		    	try {
		    		// Getting the type of the current keyword
					String typeKeyWord = ((ItemDAOJdbcImpl) ItemDAO).retrieveType(keyWord_temp.trim());
				} catch (DALException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
		    }
		});
		
		
		//panel.add(typeList);
		panel.add(label); // Components Added using Flow Layout
		panel.add(combox2);
		panel.add(send);
		panel.add(reset);

		// 
		JPanel panel2 = new JPanel(); // the panel is not visible in output
		JLabel label2 = new JLabel("Choisir les précisions : ");
		panel2.add(label2);
		panel2.add(checkbox_Ech);
		panel2.add(checkbox_Ed);
		panel2.add(checkbox_Tech);
		panel2.add(checkbox_Ench);
		panel2.add(checkbox_Et);

		// 

		JPanel panel3 = new JPanel(); // the panel is not visible in output
		JLabel label3 = new JLabel("Choisir les précisions : ");

		precision1 = new Java2sAutoComboBox(this.PotentialString);
		precision1.setPreferredSize( new Dimension( 50, 24 ) );
		precision2 = new Java2sAutoComboBox(this.PotentialString);
		precision3 = new Java2sAutoComboBox(this.PotentialString);

		JLabel labelp1 = new JLabel("Précision 1 : ");
		labelp1.setHorizontalAlignment(JTextField.CENTER);
		JLabel labelp2 = new JLabel("Précision 2 : ");
		labelp2.setHorizontalAlignment(JTextField.CENTER);
		JLabel labelp3 = new JLabel("Précision 3 : ");
		labelp3.setHorizontalAlignment(JTextField.CENTER);
		panel3.add(labelp1);
		panel3.add(precision1);
		panel3.add(labelp2);
		panel3.add(precision2);
		panel3.add(labelp3);
		panel3.add(precision3);

		panel3.setLayout(new GridLayout(3,2));


		bigpanel.setLayout(new BoxLayout(bigpanel, BoxLayout.Y_AXIS));
		bigpanel.setBorder(BorderFactory.createEmptyBorder(0, 10 , 0, 20));
		bigpanel.add(panel);
		bigpanel.add(panel2);
		bigpanel.add(panel3);



		// Setting the properties of central text area

		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setMargin( new Insets(10,10,10,10) );
		JScrollPane pane = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//pane.setPreferredSize(new Dimension( 600, 600 ));
		JPanel midpanel = new JPanel();
		midpanel.setLayout(new BorderLayout());
		midpanel.add(BorderLayout.CENTER,pane);


		//Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.NORTH, bigpanel); // frame.getContentPane().add(BorderLayout.NORTH, panel);
		frame.getContentPane().add(BorderLayout.CENTER, midpanel);
		frame.setVisible(true);    
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if (source == reset) {
			combox2.setSelectedIndex(0);
			ta.setText("");

			checkbox_Ech.setEnabled(true);
			checkbox_Ed.setEnabled(true);
			checkbox_Tech.setEnabled(true);
			checkbox_Et.setEnabled(true);
			checkbox_Ench.setEnabled(true);

			checkbox_Ech.setSelected(false);
			checkbox_Ed.setSelected(false);
			checkbox_Tech.setSelected(false);
			checkbox_Et.setSelected(false);
			checkbox_Ench.setSelected(false);

		}

		if (source == send) {
			this.keyWord = this.PotentialString.get(combox2.getSelectedIndex());
			DAO<Item> ItemDAO;
			ItemDAO = Factory.getItemDAO();
			String typeKeyWord = "";
			try {
				typeKeyWord = ((ItemDAOJdbcImpl) ItemDAO).retrieveType(this.keyWord.trim());
			} catch (DALException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			Controler cont = new Controler();
			int idKeyWord = -1;
			String type2KeyWord = "";
			if (typeKeyWord.equals("Item")) {
				try {
					idKeyWord = cont.genericRetrieveId(this.keyWord.trim());
				} catch (DALException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					type2KeyWord =((ItemDAOJdbcImpl) ItemDAO).retrieveType2(idKeyWord);
				} catch (DALException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			String stemSaves = "D:\\Documents\\TKD\\Cahier technique\\Database\\Fiches\\";
			List<String> Details = new ArrayList<String>(); 
			if (checkbox_Ech.isSelected()) {
				Details.add("Echauffement");
			}
			if (checkbox_Ed.isSelected()) {
				Details.add("Educatif");
			}
			if (checkbox_Et.isSelected()) {
				Details.add("Etirement");
			}
			if (checkbox_Tech.isSelected()) {
				Details.add("Technique");
			}
			if (checkbox_Ench.isSelected()) {
				Details.add("Enchainement");
			}
			
			if (!(precision1.getSelectedIndex() == 0)) {
				Details.add(this.PotentialString.get(precision1.getSelectedIndex()));
			}
			if (!(precision2.getSelectedIndex() == 0)) {
				Details.add(this.PotentialString.get(precision2.getSelectedIndex()));
			}
			if (!(precision3.getSelectedIndex() == 0)) {
				Details.add(this.PotentialString.get(precision3.getSelectedIndex()));
			}
			try {
				//String res = cont.excecuteRequest(keyWord, Details, typeSelected, stemSaves);
				String res = cont.excecuteRequestManager(keyWord, Details, stemSaves);
				this.displayResults(res);
			} catch (FileNotFoundException | DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}


	public void displayResults(String s) {
		ta.setText(s);
	}

	public String getKeyword() {
		return this.keyWord;
	}
}