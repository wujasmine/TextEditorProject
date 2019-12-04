import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import javax.swing.JScrollBar;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

public class Team_13_Text_Formatter {

	private JFrame frmTextFormatter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Team_13_Text_Formatter window = new Team_13_Text_Formatter();
					window.frmTextFormatter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Team_13_Text_Formatter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTextFormatter = new JFrame();
		frmTextFormatter.setTitle("Text Formatter");
		frmTextFormatter.setBounds(100, 100, 737, 498);
		frmTextFormatter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTextFormatter.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(21, 34, 352, 152);
		frmTextFormatter.getContentPane().add(textArea);
		JButton btnNewButton = new JButton("Open File...");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				openFile of  = new openFile() ; 
				try
				{
					of.fileReader();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				textArea.setText(of.sb.toString());
			}
		});
		
		btnNewButton.setBounds(21, 415, 97, 23);
		frmTextFormatter.getContentPane().add(btnNewButton);
		
		JLabel lblInputText = new JLabel("Input Text ");
		lblInputText.setBounds(160, 11, 97, 14);
		frmTextFormatter.getContentPane().add(lblInputText);
		
		JButton btnDownload = new JButton("Download File");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDownload.setBounds(383, 415, 192, 23);
		frmTextFormatter.getContentPane().add(btnDownload);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(405, 34, 300, 330);
		frmTextFormatter.getContentPane().add(textArea_2);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent r) {
				
			}
		});
		btnGenerate.setBounds(142, 415, 214, 23);
		frmTextFormatter.getContentPane().add(btnGenerate);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(21, 222, 352, 152);
		frmTextFormatter.getContentPane().add(textArea_1);
		
		JLabel lblError = new JLabel("Errors Found");
		lblError.setBounds(160, 197, 79, 14);
		frmTextFormatter.getContentPane().add(lblError);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(616, 415, 89, 23);
		frmTextFormatter.getContentPane().add(btnExit);
		
		JLabel lblOutputTextPreview = new JLabel("Output Text Preview");
		lblOutputTextPreview.setBounds(490, 11, 146, 14);
		frmTextFormatter.getContentPane().add(lblOutputTextPreview);
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
