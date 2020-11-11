package GUI;

import java.awt.BorderLayout;
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


public class Gui extends JFrame implements WindowListener,ActionListener {
	JButton reset = new JButton("Reset");
	JButton send = new JButton("Send");
	JTextField tf = new JTextField(10); // accepts upto 10 characters
	Java2sAutoTextField tf2 ;
    Java2sAutoComboBox combox2;

	
	JTextArea ta = new JTextArea();
	String[] typeStrings = {"Choisir un type", "Technique", "Enchainement", "Générique"};
	//Create the combo box
	JComboBox<String> typeList = new JComboBox<String>(typeStrings);
	JCheckBox checkbox_Ech = new JCheckBox("Echauffement");
	JCheckBox checkbox_Ed = new JCheckBox("Educatifs");
	JCheckBox checkbox_Et = new JCheckBox("Etirement");
	JCheckBox checkbox_Tech = new JCheckBox("Techniques");
	JCheckBox checkbox_Ench = new JCheckBox("Enchainement");

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
		typeList.addActionListener(this);
		
		// tf not used anymore for now
		tf.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
			    warn();
			  }
			  public void removeUpdate(DocumentEvent e) {
			    warn();
			  }
			  public void insertUpdate(DocumentEvent e) {
			    warn();
			  }

			  public void warn() {
				  String keyWord = tf.getText();
					if (!keyWord.isEmpty()) {
						String[] splitLine = keyWord.split("[ \\t\\n\\,\\?\\;\\.\\:\\!]") ;
						String firstWord = splitLine[0];
						firstWord = firstWord.trim();
						if (firstWord.equals("Poomsae") || firstWord.equals("poomsae")) {
							checkbox_Ench.setSelected(false);
							checkbox_Ench.setEnabled(false);
							checkbox_Ed.setSelected(false);
							checkbox_Ed.setEnabled(false);
							checkbox_Et.setSelected(false);
							checkbox_Et.setEnabled(false);
							checkbox_Ech.setSelected(false);
							checkbox_Ech.setEnabled(false);
						}
					}
			  }
			});
		tf.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mouseClicked(MouseEvent e) {
			    tf.setText("");
			  }
			});
		
		
		DAO<Item> ItemDAO;
		ItemDAO = Factory.getItemDAO();
		this.PotentialString = ((ItemDAOJdbcImpl) ItemDAO).getAllNames();
		this.PotentialString.add("-");
		this.PotentialString.sort(Comparator.comparing( String::toString ));
		combox2 = new Java2sAutoComboBox(this.PotentialString);
		
		panel.add(typeList);
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
		panel2.add(checkbox_Et);
		panel2.add(checkbox_Tech);
		panel2.add(checkbox_Ench);
		
		bigpanel.setLayout(new BoxLayout(bigpanel, BoxLayout.Y_AXIS));
		bigpanel.add(panel);
		bigpanel.add(panel2);
		

		
		
//		tf2 = new Java2sAutoTextField(PotentialString);
//		tf2.addMouseListener(new MouseAdapter() {
//			  @Override
//			  public void mouseClicked(MouseEvent e) {
//			    tf2.setText("");
//			  }
//			});
//		
//		bigpanel.add(combox2);
//		bigpanel.add(tf2);
		
		// Setting the properties of central text area
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setMargin( new Insets(10,10,10,10) );

		//Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.NORTH, bigpanel); // frame.getContentPane().add(BorderLayout.NORTH, panel);
		frame.getContentPane().add(BorderLayout.CENTER, ta);
		frame.setVisible(true);    
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if (source == reset) {
			tf.setText("");
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
		if (source == typeList) {
			checkbox_Ech.setEnabled(true);
			checkbox_Ed.setEnabled(true);
			checkbox_Tech.setEnabled(true);
			checkbox_Et.setEnabled(true);
			checkbox_Ench.setEnabled(true);
			
//			checkbox_Ech.setSelected(false);
//			checkbox_Ed.setSelected(false);
//			checkbox_Tech.setSelected(false);
//			checkbox_Et.setSelected(false);
//			checkbox_Ench.setSelected(false);
			
			String typeSelected = typeStrings[typeList.getSelectedIndex()];
			//if (typeSelected.equals("Technique")) {
				//checkbox_Tech.setSelected(true);
				//checkbox_Tech.setEnabled(false); // PB si echauffement ou autre
			//}
		}
		if (source == send) {
			String typeSelected = typeStrings[typeList.getSelectedIndex()];
			this.keyWord = this.PotentialString.get(combox2.getSelectedIndex());
			DAO<Item> ItemDAO;
			ItemDAO = Factory.getItemDAO();
			Controler cont = new Controler();
			int idKeyWord = -1;
			String type2KeyWord = "";
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
			
			if (type2KeyWord.equals("Echauffement")){
				checkbox_Ech.setSelected(true);
				checkbox_Ech.setEnabled(false);
			}
			if (type2KeyWord.equals("Etirement")){
				checkbox_Et.setSelected(true);
				checkbox_Et.setEnabled(false);
			}
			if (type2KeyWord.equals("Educatif")){
				checkbox_Ed.setSelected(true);
				checkbox_Ed.setEnabled(false);
			}
			
			if (type2KeyWord.equals("Technique")){
				checkbox_Tech.setSelected(true);
				checkbox_Tech.setEnabled(false);
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
			try {
				String res = cont.excecuteRequest(keyWord, Details, typeSelected, stemSaves);
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