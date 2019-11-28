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

public class Team_13_Text_Formatter {

	private JFrame frmTextFormatter;
	private JTextField txtInputText;
	private JTextField textField_2;
	private JTextField textField;

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
		
		JButton btnNewButton = new JButton("Open File...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(356, 34, 17, 152);
		frmTextFormatter.getContentPane().add(scrollBar);
		
		txtInputText = new JTextField();
		txtInputText.setBounds(21, 34, 352, 152);
		frmTextFormatter.getContentPane().add(txtInputText);
		txtInputText.setColumns(10);
		
		JScrollBar scrollBar_2 = new JScrollBar();
		scrollBar_2.setBounds(688, 34, 17, 340);
		frmTextFormatter.getContentPane().add(scrollBar_2);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		scrollBar_1.setBounds(356, 222, 17, 152);
		frmTextFormatter.getContentPane().add(scrollBar_1);
		btnNewButton.setBounds(21, 415, 97, 23);
		frmTextFormatter.getContentPane().add(btnNewButton);
		
		JLabel lblInputText = new JLabel("Input Text ");
		lblInputText.setBounds(160, 11, 53, 14);
		frmTextFormatter.getContentPane().add(lblInputText);
		
		JButton btnDownload = new JButton("Download File");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDownload.setBounds(383, 415, 192, 23);
		frmTextFormatter.getContentPane().add(btnDownload);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGenerate.setBounds(142, 415, 214, 23);
		frmTextFormatter.getContentPane().add(btnGenerate);
		
		textField_2 = new JTextField();
		textField_2.setBounds(21, 222, 352, 152);
		frmTextFormatter.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblError = new JLabel("Errors Found");
		lblError.setBounds(160, 197, 79, 14);
		frmTextFormatter.getContentPane().add(lblError);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(616, 415, 89, 23);
		frmTextFormatter.getContentPane().add(btnExit);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(383, 34, 322, 340);
		frmTextFormatter.getContentPane().add(textField);
		
		JLabel lblOutputTextPreview = new JLabel("Output Text Preview");
		lblOutputTextPreview.setBounds(490, 11, 99, 14);
		frmTextFormatter.getContentPane().add(lblOutputTextPreview);
	}
}
