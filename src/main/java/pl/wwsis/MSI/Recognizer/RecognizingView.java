package pl.wwsis.MSI.Recognizer;
import java.awt.Color;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

/*
 *Class RecognizingView
 */
public class RecognizingView extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//size variables for window
	private static int WIDTH  = 500;
	private static int HEIGHT = 400;
	
	//elements for JPanel
	@SuppressWarnings("unused")
	private JScrollBar 	scroll;
	private JButton		recognizeButton;
	private JLabel		image_input;
	
	//elements for Menu
	@SuppressWarnings("unused")
	private JMenu 		file;
	private JMenuItem 	open;
	private JMenuItem 	recognize;
	private JMenuItem 	exit;
	
	/*
	 *Constructor for RecognizingView class
	 *@param name Used to set the title on the JFrame
	 */
// ---------------------------------------------------------------------------------------------------------------	
	public RecognizingView(String name)
	{
		//set the title of the JFrame
		super(name);
		
		//Menubar
		JMenuBar menu = new JMenuBar();
		
		JMenu file = new JMenu("File");	file.setMnemonic('F');
		open = new JMenuItem("Open"); open.setMnemonic('O'); file.add(open);
		recognize = new JMenuItem("Recognize"); recognize.setMnemonic('R'); file.add(recognize);
		file.addSeparator();
		exit = new JMenuItem("Exit"); exit.setMnemonic('x'); file.add(exit);
		
		menu.add(file);
		setJMenuBar(menu);
		
		// display rules
		setResizable(true);						//allow window to be resized: true?false
		setBackground(Color.lightGray);			//background color of window: Color(int,int,int) or Color.name
		setLocation(100,100);					//location on the screen to display window
        setDefaultCloseOperation(EXIT_ON_CLOSE);//what to do on close operation: exit, do_nothing, etc
        setSize(WIDTH,HEIGHT);					//set the size of the window
        setVisible(true);						//show the window: true?false
	}
	
	/*
	 *@return The menu item 'Open'
	 */
// ---------------------------------------------------------------------------------------------------------------	
	public JMenuItem	getOpen()		{ return open;			}
	/*
	 *@return The menu item 'Recognize'
	 */
// ---------------------------------------------------------------------------------------------------------------	
	public JMenuItem	getRecognize()	{ return recognize;			}
	/*
	 *@return The menu item 'Exit'
	 */
// ---------------------------------------------------------------------------------------------------------------	
	public JMenuItem	getExit()		{ return exit;				}
	/*
	 *@return The JLabel containing the image to recognize text from
	 */
// ---------------------------------------------------------------------------------------------------------------	
	public JLabel		getImageInput()	{ return image_input;		}
	/*
	 *@return The JPanel displaying the Open View - open panel
	 */
// ---------------------------------------------------------------------------------------------------------------	
	public JPanel		getOpenPanel()	{ return new Open_Panel();	}
	/*
	 *@return The JPanel displaying the Recognize View - image panel
	 */	
// ---------------------------------------------------------------------------------------------------------------	
	public JPanel		getImagePanel()	{ return new Image_Panel();	}
	/*
	 *@return The Recognize button
	 */
// ---------------------------------------------------------------------------------------------------------------	
	public JButton		getRButton()	{ return recognizeButton;		}
	
	/*
	 *Class Open_Panel
	 */
// ---------------------------------------------------------------------------------------------------------------	
	@SuppressWarnings("serial")
	private class Open_Panel extends JPanel
	{
		
		public Open_Panel()
		{
	    	//set basic display
			setBackground(Color.lightGray);
			setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		}
	}
	
	/*
	 *Class Image_Panel
	 */
// ---------------------------------------------------------------------------------------------------------------	
	@SuppressWarnings("serial")
	private class Image_Panel extends JPanel
	{
		/*
		 *Constructor for displaying an image to be decoded
		 */
		public Image_Panel()
		{
			//setup GridBagLayout
			GridBagLayout layout = new GridBagLayout(); 
			GridBagConstraints layoutConstraints = new GridBagConstraints(); 
			setLayout(layout);
			
			image_input = new JLabel();
			layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 0; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,0,0,0); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 50.0;
			JScrollPane scroll = new JScrollPane(image_input,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
			layout.setConstraints(scroll,layoutConstraints);
			scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			image_input.setHorizontalAlignment(JLabel.CENTER);
	    	add(scroll);
	    	
	    	recognizeButton = new JButton("Recognize Now");
	    	layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 1; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,-5,-5,-5); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 1.0;
			layout.setConstraints(recognizeButton,layoutConstraints);
	    	add(recognizeButton);
	    	
	    	//set basic display
			setBackground(Color.lightGray);
			setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
	    }
	 }
	
	/*
	 *Main Method for testing
	 */
// ---------------------------------------------------------------------------------------------------------------	
	public static void main(String args[])
	{
		new RecognizingView("Recognizing");
	}
}