import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartingFrame {

	private JFrame frame;
	private JTextField username;
	private JPasswordField password;
	private final String user="hrithik";
	private final String pass="1234";
	TextToSpeech tts;
	private StartingFrame ob;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartingFrame window = new StartingFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartingFrame() {
		initialize();
		ob=this;
		tts=new TextToSpeech();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setBounds(100, 100, 946, 605);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel header = new JLabel("Jarvis Server");
		header.setFont(new Font("Calibri Light", Font.BOLD, 42));
		header.setBounds(336, 54, 532, 87);
		frame.getContentPane().add(header);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Arial", Font.PLAIN, 29));
		usernameLabel.setBounds(225, 215, 177, 54);
		frame.getContentPane().add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Arial", Font.PLAIN, 29));
		passwordLabel.setBounds(225, 274, 185, 65);
		frame.getContentPane().add(passwordLabel);
		
		username = new JTextField();
		username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		username.setForeground(Color.LIGHT_GRAY);
		username.setBackground(Color.DARK_GRAY);
		username.setBounds(447, 226, 177, 43);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		password.setForeground(Color.LIGHT_GRAY);
		password.setBackground(Color.DARK_GRAY);
		password.setBounds(447, 290, 177, 37);
		frame.getContentPane().add(password);
		
		JButton signIn = new JButton("SingIn");
		signIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String u=username.getText();
				String p=new String(password.getPassword());
				if(u.equals(user) && p.equals(pass))
				{
					tts.speak("access granted", 1.0f, false, true);
					//JOptionPane.showMessageDialog(null, "access granted");
					Server frame1=new Server();
					frame1.setVisible(true);
					frame.dispose();
				}
				else
				{
					tts.speak("access denied", 1.0f, false, true);
				}
			}
		});
		signIn.setBackground(Color.BLACK);
		signIn.setForeground(Color.LIGHT_GRAY);
		signIn.setFont(new Font("Tahoma", Font.PLAIN, 19));
		signIn.setBounds(370, 391, 112, 37);
		frame.getContentPane().add(signIn);
	}
}
