package pl.wwsis.MSI.Recognizer;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

/*
 *RecognizingController Class
 */
public class RecognizingController
{
	//Program Variables
	private RecognizingView	view;
	private Recognizing		model;
	
	//Panel Displays
	private JPanel		recognize_panel;
	private JPanel		open_panel;
	//Panel Variables
	private JButton		recognizeButton;
	private JLabel		image_input;
	//Menu Variables
	private JMenuItem 	open;
	private JMenuItem 	recognize;
	private JMenuItem 	exit;
	//action event classes
	private Open			opn;
	private Recognize		rcg;
	private RecognizeButton	rcgButton;
	//recognize variable
	private String			stat_path = "";
	private String			stat_name = "";
	//loading image variable
	private File imageFile;
	
	/*
	 *Constructor to initialize view, model and environment variables
	 *@param aView  A GUI class, to be saved as view
	 *@param aModel A model class, to be saved as model
	 */
	public RecognizingController(RecognizingView aView, Recognizing aModel)
	{
		//program variables
		view  = aView;
		model = aModel;
		
		//assign View Variables
		//2 views
		open_panel		= view.getOpenPanel();
		recognize_panel	= view.getImagePanel();
		//2 data options
		image_input		= view.getImageInput();
		//2 buttons
		recognizeButton	= view.getRButton();
		//menu
		open			= view.getOpen();
		recognize		= view.getRecognize();
		exit			= view.getExit();
		
		//assign action events
		opn = new Open();
		open.addActionListener(opn);
		rcg = new Recognize();
		recognize.addActionListener(rcg);
		exit.addActionListener(new Exit());
		rcgButton = new RecognizeButton();
		recognizeButton.addActionListener(rcgButton);
		
		//open view as default
		open_view();
	}
	
	/*
	 *Updates the single panel to display the Open View.
	 */
	private void open_view()
	{
		update();
		view.setContentPane(open_panel);
		view.setVisible(true);
	}
	
	/*
	 *Updates the single panel to display the Recognize View.
	 */
	private void recognize_view()
	{
		update();
		view.setContentPane(recognize_panel);
		view.setVisible(true);
	}
	
	/*
	 *Opens file chooser to enter the image and display the recognize View.
	 */
	private void imageOpening()
	{
		//start path of displayed File Chooser
		JFileChooser chooser = new JFileChooser("./");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(new Image_Filter());
		int returnVal = chooser.showOpenDialog(view);
		if (returnVal == JFileChooser.APPROVE_OPTION){
			File directory = chooser.getSelectedFile();
			try{
				String image = directory.getPath();
				recognize_view();
				image_input.setIcon(new ImageIcon(ImageIO.read(new File(image))));
				this.setImage((new File(image)));
			}
			catch(Exception except) {
			//msg if opening fails
			JOptionPane.showMessageDialog(view, "The File cannot be opened!", 
				"Error!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	/*
	 * 
	 */
	private void setImage(File file) {
		imageFile = file;
	}
	
	/*
	 * 
	 */
	private File getImage(){
		return imageFile;
	}

	/*
	 *Open Class - handles the Open menu item
	 */
	private class Open implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			imageOpening(); //opens and displays the image
		}
	}
	
	/*
	 *Recognize Class - handles the chars recognizing
	 */
	private class Recognize implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			if (getImage() == null){
				imageOpening();
			}else{
//			model.getRecognitionResult(getImage());
			}
		}
	}
	/*
	 *Exit Class - handles the Exit menu item
	 */
	private class Exit implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0); //exit the program
		}
	}
	
	/*
	 *Recognize Button Class - handles the Recognize Button item
	 */
	private class RecognizeButton implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
//			new ImageRecognition().getRecognitionResult(getImage());
			model.getRecognitionResult(getImage());
		}
	}
	
	/*
	 *Updates the variables to an initial state
	 */
	public void update()
	{
		image_input.setIcon(null);	//clear image
		stat_path = "";				//clear path
		stat_name = "";				//clear name
	}
	
	/*
	 *Main Method for testing
	 */
	public static void main(String args[]) throws IOException
	{
		new RecognizingController(
									new RecognizingView("Recognizing"),
									new Recognizing()
									);
	}
}