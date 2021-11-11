import java.awt.BorderLayout;
import com.fazecast.jSerialComm.*;
import java.awt.EventQueue;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Server extends JFrame implements Runnable {

	/**
	 * Launch the application.
	 */
	private Server frame;
	private Thread t;
	private TextToSpeech tts;
	private SpeechRecognizerMain sr;
	private JTextField voiceText;
	boolean lightStatus=false;
	boolean fanStatus=false;
	boolean flashlightStatus=false;
	boolean sosStatus=false;
	boolean cflStatus=false;
	boolean chromeStatus=false;
	boolean incomingCallStatus=false;
	private boolean isValueSet=false;
	private String voiceString;
	private Runtime r=Runtime.getRuntime();
	Process chromeProcess;
	JButton lightButton;
	JButton fanButton;
	JButton flashlightButton;
	JButton sosButton;
	SerialPort comPort;
	public network n;
	private OutputStream out;
	byte l[]= {49};
	byte fan[]= {50};
	byte flash[]= {51};
	byte sos[]= {52};
	public final String LIGHT_ON="light on";
	public final String LIGHT_OFF="light off";
	public final String FAN_ON="fan on";
	public final String FAN_OFF="fan off";
	public final String FLASHLIGHT_ON="flashlight on";
	public final String FLASHLIGHT_OFF="flashlight off";
	public final String SOS_ON="sos on";
	public final String SOS_OFF="sos off";
	public JTextField phoneStatusLabel;
	private JTextField incomingCallField;
	//....................................run method...............................
	public void run()
	{
		try
		{
			Thread.sleep(6000);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "error while starting the thread in server");
		}
		while(true)
		{
			
		    voiceString=sr.getString();
		    //System.out.println("value is "+voiceString);
			voiceText.setText(voiceString);
			
			/*if(voiceString.equals("ligths on") || voiceString.equals("lights off"))
			{
				System.out.println("hellow world");
				frame.toggleLight();
				//isValueSet=false;
				sr.speechRecognitionResult="null";
			}
			else if(voiceString.equals("fan on") || voiceString.equals("fan off"))
			{
				System.out.println("hellow world");
				frame.toggleFan();
				//isValueSet=false;
				sr.speechRecognitionResult="null";
			}
			//isValueSet=false;
			//sr.speechRecognitionResult="null";*/
			
			if(voiceString.equals("jarvis") || voiceString.equals("hello jarvis") || voiceString.equals("jarvis are you listening"))
			{
				if(voiceString.equals("jarvis"))
				{
					tts.speak("yes sir", 1.0f, false, true);
					try
					{
						Thread.sleep(3000);
					}catch(Exception ex) {JOptionPane.showMessageDialog(null, "error occured while executing thread.sleep");}
					for(int i=0;i<5;i++)
					{
						voiceString=sr.getString();
						voiceText.setText(voiceString);
						
						
						switch(voiceString)
						{
						case "lights on" :frame.toggleLight(false);
							              sr.speechRecognitionResult="null";
							              break;
						case "lights off" :frame.toggleLight(false);
										   sr.speechRecognitionResult="null";
										   break;
						case "fan on" :frame.toggleFan(false);
			                              sr.speechRecognitionResult="null";
			                               break;
		                case "fan off" :frame.toggleFan(false);
						                   sr.speechRecognitionResult="null";
										   break;
		                case "flash light on" :frame.toggleFlashlight(false);
		                                    sr.speechRecognitionResult="null";
						                    break;
		                case "flash light off" :frame.toggleFlashlight(false);
		                					 sr.speechRecognitionResult="null";
		                					 break;
		                case "introduce yourself" :tts.speak("my name is jarvis", 1.0f, false, true);
		                                           tts.speak("and i am your assistant", 1.0f, false, true);
		                                           tts.speak("i can do some cool stuff like controlling this room", 1.0f, false, true);
		                                           tts.speak("i am completely made up of java", 1.0f, false, true);
		                                           sr.speechRecognitionResult="null";
		                                           break;
		                case "switch off everything":frame.switchOffEveryThing();
		                							 sr.speechRecognitionResult="null";
		                							 break;
		                case "open chrome" :frame.openChrome(true);
		                					sr.speechRecognitionResult="null";
		                					break;
		                case "close chrome" :frame.openChrome(false);
		                				     sr.speechRecognitionResult="null";
		                				     break;
						}
					}
					sr.speechRecognitionResult="null";
				}
				else if(voiceString.equals("hello jarvis"))
				{
					tts.speak("hello sir how are you", 1.0f, false, true);
					sr.speechRecognitionResult="null";
				}
				
			}
			else if(voiceString.equals("introduce yourself"))
			{
				tts.speak("my name is jarvis", 1.0f, false, true);
				tts.speak("i am a hrithik's assistant", 1.0f, false, true);
				tts.speak("speed eight tera byte memory one petabyte", 1.0f, false, true);
				tts.speak("thats all", 1.0f, false, true);
				sr.speechRecognitionResult="null";
			}
		}
	}
	
	//.............................................................................
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//.......................................FUNCTIONS...............................
	void toggleLight(boolean client)
	{
		try
		{
			if(lightStatus==false)
			{
				out.write(l);
				out.flush();
				lightStatus=true;
				lightButton.setBackground(Color.RED);
				lightButton.setText("ON");
				if(n.isClientConnected && !client)
				{
					n.out.writeUTF(this.LIGHT_ON);
				}
				tts.speak("lights on", 1.0f, false, true);
			}
			else if(lightStatus==true)
			{
				out.write(l);
				out.flush();
				lightStatus=false;
				lightButton.setBackground(Color.GREEN);
				lightButton.setText("OFF");
				if(n.isClientConnected && !client)
				{
					n.out.writeUTF(this.LIGHT_OFF);
				}
				tts.speak("lights off", 1.0f, false, true);
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "error sending toggle light command");
		}
	}
	
	void toggleFan(boolean client)
	{
		try
		{
			if(fanStatus==false)
			{
				out.write(fan);
				out.flush();
				fanStatus=true;
				fanButton.setBackground(Color.RED);
				fanButton.setText("ON");
				if(n.isClientConnected && !client)
				{
					n.out.writeUTF(this.FAN_ON);
				}
				tts.speak("fan on", 1.0f, false, true);
			}
			else if(fanStatus==true)
			{
				out.write(fan);
				out.flush();
				fanStatus=false;
				fanButton.setBackground(Color.GREEN);
				fanButton.setText("OFF");
				if(n.isClientConnected && !client)
				{
					n.out.writeUTF(this.FAN_OFF);
				}
				tts.speak("fan off", 1.0f, false, true);
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "error sending toggle fan command");
		}
	}
	
	void toggleFlashlight(boolean client)
	{
		try
		{
			if(flashlightStatus==false)
			{
				out.write(flash);
				out.flush();
				flashlightStatus=true;
				flashlightButton.setBackground(Color.RED);
				flashlightButton.setText("ON");
				if(n.isClientConnected && !client)
				{
					n.out.writeUTF(this.FLASHLIGHT_ON);
				}
				tts.speak("flashlight on", 1.0f, false, true);
			}
			else if(flashlightStatus==true)
			{
				out.write(flash);
				out.flush();
				flashlightStatus=false;
				flashlightButton.setBackground(Color.GREEN);
				flashlightButton.setText("OFF");
				if(n.isClientConnected && !client)
				{
					n.out.writeUTF(this.FLASHLIGHT_OFF);
				}
				tts.speak("flashlight off", 1.0f, false, true);
			}
		}
		catch(Exception ee)
		{
			JOptionPane.showMessageDialog(null, "error while toggling the computer");
		}
	}
	
	void toggleSos(boolean client)
	{
		try
		{
			if(sosStatus==false)
			{
				out.write(sos);
				out.flush();
				sosStatus=true;
				sosButton.setBackground(Color.RED);
				sosButton.setText("ON");
				this.sosEmergencySignal();
				if(n.isClientConnected && !client)
				{
					n.out.writeUTF(this.SOS_ON);
				}
				tts.speak("emergency signal on", 1.0f, false, true);
			}
			else if(sosStatus==true)
			{
				out.write(sos);
				out.flush();
				sosStatus=false;
				sosButton.setBackground(Color.GREEN);
				sosButton.setText("OFF");
				if(n.isClientConnected && !client)
				{
					n.out.writeUTF(this.SOS_OFF);
				}
				tts.speak("emergency signal turned off", 1.0f, false, true);
			}
		}
		catch(Exception ee)
		{
			JOptionPane.showMessageDialog(null, "error while toggling the computer");
		}
	}
	
	void switchOffEveryThing()
	{
		if(lightStatus)
			toggleLight(false);
		if(fanStatus)
			toggleFan(false);
		if(flashlightStatus)
			toggleFlashlight(false);
		if(sosStatus)
			toggleSos(false);
	}
	
	void openChrome(boolean status)
	{
		if(status==true && chromeStatus==false)
		{
			try
			{
				chromeProcess=r.exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
			}
			catch(IOException ee)
			{
				JOptionPane.showMessageDialog(null, "error occured while opening chrome");
			}
			chromeStatus=true;
			tts.speak("google chrome launched", 1.0f, false, true);
		}
		else if(status==false && chromeStatus==true)
		{
			chromeProcess.destroy();
			chromeStatus=false;
			tts.speak("chrome Closed", 1.0f, false, true);
		}
	}
	
	public void sosEmergencySignal()
	{
		new Thread(new Runnable() {
			public void run()
			{
				while(sosStatus==true)
				{
					tts.speak("it is an emergency signal", 1.0f, false, true);
				}
			}
		}).start();
	}
	
	public void toggleIncomingCall(String number)
	{
		incomingCallStatus=true;
		incomingCallField.setText(number);
		incomingCallAlert();
	}
	
	public void incomingCallAlert()
	{
		new Thread(new Runnable() {
			public void run()
			{
				while(incomingCallStatus)
				{
					tts.speak("Hrithik your phone is ringing", 1.0f, false, true);
				}
			}
		}).start();
	}
	
	public void connectedAlert()
	{
		incomingCallStatus=false;
		incomingCallField.setText("call connected");
		tts.speak("call connected", 1.0f, false, true);
	}
	
	public void disconnectedAlert()
	{
		incomingCallStatus=false;
		incomingCallField.setText("call disconnected");
		tts.speak("call disconnected", 1.0f, false, true);
	}
	
	
	//...............................................................................
	
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    frame = this;
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Server() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		frame=this;
		tts=new TextToSpeech();
		n=new network(frame);
		sr=new SpeechRecognizerMain();
		sr.recognizingMode(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1045, 716);
		getContentPane().setLayout(null);
		
		JLabel voiceTextLabel = new JLabel("Voice Recognition Result");
		voiceTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		voiceTextLabel.setBounds(43, 95, 214, 44);
		getContentPane().add(voiceTextLabel);
		
		voiceText = new JTextField();
		voiceText.setFont(new Font("Arial", Font.PLAIN, 24));
		voiceText.setBounds(268, 95, 418, 44);
		getContentPane().add(voiceText);
		voiceText.setColumns(10);
		
		JLabel lightLabel = new JLabel("Light");
		lightLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lightLabel.setBounds(66, 189, 142, 44);
		getContentPane().add(lightLabel);
		
		JLabel fanLabel = new JLabel("Fan");
		fanLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		fanLabel.setBounds(66, 253, 142, 44);
		getContentPane().add(fanLabel);
		
		JLabel flashlightLable = new JLabel("Flashlight");
		flashlightLable.setFont(new Font("Tahoma", Font.PLAIN, 24));
		flashlightLable.setBounds(66, 320, 142, 44);
		getContentPane().add(flashlightLable);
		
		JLabel sosLabel = new JLabel("SOS");
		sosLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		sosLabel.setBounds(66, 385, 142, 44);
		getContentPane().add(sosLabel);
		
		lightButton = new JButton("OFF");
		lightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.toggleLight(false);
			}
		});
		lightButton.setBackground(Color.GREEN);
		lightButton.setFont(new Font("Arial", Font.PLAIN, 17));
		lightButton.setBounds(254, 189, 142, 39);
		getContentPane().add(lightButton);
		
		fanButton = new JButton("OFF");
		fanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.toggleFan(false);
			}
		});
		fanButton.setBackground(Color.GREEN);
		fanButton.setFont(new Font("Arial", Font.PLAIN, 17));
		fanButton.setBounds(254, 253, 142, 39);
		getContentPane().add(fanButton);
		
		flashlightButton = new JButton("OFF");
		flashlightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleFlashlight(false);
			}
		});
		flashlightButton.setBackground(Color.GREEN);
		flashlightButton.setFont(new Font("Arial", Font.PLAIN, 17));
		flashlightButton.setBounds(254, 325, 142, 39);
		getContentPane().add(flashlightButton);
		
		sosButton = new JButton("OFF");
		sosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleSos(false);
			}
		});
		sosButton.setBackground(Color.GREEN);
		sosButton.setFont(new Font("Arial", Font.PLAIN, 17));
		sosButton.setBounds(254, 385, 142, 39);
		getContentPane().add(sosButton);
		
		JLabel header = new JLabel("Jarvis Server Controll Panel");
		header.setFont(new Font("Tahoma", Font.PLAIN, 40));
		header.setBounds(235, 10, 545, 75);
		getContentPane().add(header);
		
		phoneStatusLabel = new JTextField();
		phoneStatusLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
		phoneStatusLabel.setText(" Phone Not Connected");
		phoneStatusLabel.setBounds(652, 189, 234, 44);
		getContentPane().add(phoneStatusLabel);
		phoneStatusLabel.setColumns(10);
		
		incomingCallField = new JTextField();
		incomingCallField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		incomingCallField.setBounds(529, 253, 357, 49);
		getContentPane().add(incomingCallField);
		incomingCallField.setColumns(10);
		
		comPort=SerialPort.getCommPorts()[0];
		comPort.openPort();
		comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		out=comPort.getOutputStream();
		
		t=new Thread(this,"recognizing result");
		t.start();
	}
}
