import java.net.*;

import javax.swing.JOptionPane;

import java.io.*;

public class network implements Runnable {
	Thread t;
	network n;
	private ServerSocket ss;
	private Socket s;
	private String cmd;
	private DataInputStream in;
	private Server ob;
	DataOutputStream out;
	boolean isClientConnected=false;
	
	//........................................functions.....................................
	
	String sendStatus(boolean val)
	{
		if(val==true)
			return "true";
		else
			return "false";
	}
	
	void connectServer()
	{
		try
		{
			
				ss=new ServerSocket(6666);
				s=ss.accept();
				isClientConnected=true;
				//JOptionPane.showMessageDialog(null, "client connected");
				ob.phoneStatusLabel.setText("Realme x Connected");
			
		}
		catch(Exception ex)
		{
			//JOptionPane.showMessageDialog(null, "error while connecting to clients");
			ob.phoneStatusLabel.setText("Phone Disconnected");
			//new network(ob);
			isClientConnected=false;
			try
			{
				ss.close();
				s=null;
			}
			catch(Exception ee)
			{
				//JOptionPane.showMessageDialog(null,"error while closing the socket");
				ob.phoneStatusLabel.setText("Phone Disconnected");
			}
		}
		try
		{
			in=new DataInputStream(s.getInputStream());
			out=new DataOutputStream(s.getOutputStream());
		}
		catch(Exception ex)
		{
			//JOptionPane.showMessageDialog(null, "error occured while assignig an input stream");
			ob.phoneStatusLabel.setText("Phone Disconnected");
	    }
	}
	
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	
	network(Server ob)
	{
		n=this;
		this.ob=ob;
		ob.n=this.n;
		/*try
		{
			ss=new ServerSocket(6666);
			s=ss.accept();
			JOptionPane.showMessageDialog(null, "client connected");
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "error while connecting to clients");
		}
		try
		{
			in=new DataInputStream(s.getInputStream());
			out=new DataOutputStream(s.getOutputStream());
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "error occured while assignig an input stream");
	    }*/
		t=new Thread(this,"socket");
		t.start();
	}
	
	public void run()
	{
		while(true) {
		connectServer();
		try
		{
			if(ob.lightStatus==true)
			{
				out.writeUTF(ob.LIGHT_ON);
				out.flush();
			}
			else
			{
				out.writeUTF(ob.LIGHT_OFF);
				out.flush();
			}
			
			if(ob.fanStatus==true)
			{
				out.writeUTF(ob.FAN_ON);
				out.flush();
			}
			else
			{
				out.writeUTF(ob.FAN_OFF);
				out.flush();
			}
			
			if(ob.flashlightStatus==true)
			{
				out.writeUTF(ob.FLASHLIGHT_ON);
				out.flush();
			}
			else
			{
				out.writeUTF(ob.FLASHLIGHT_OFF);
				out.flush();
			}
			
			if(ob.sosStatus==true)
			{
				out.writeUTF(ob.SOS_ON);
				out.flush();
			}
			else
			{
				out.writeUTF(ob.SOS_OFF);
				out.flush();
			}
		}
		catch(IOException ex)
		{
			//JOptionPane.showMessageDialog(null, "error while sending the initial states to the client");
			ob.phoneStatusLabel.setText("Phone Disconnected");
			isClientConnected=false;
			//ss=null;
			//s=null;
		}
		while(true && isClientConnected)
		{
			try
			{
				cmd=(String)in.readUTF();
				
				if(cmd.equalsIgnoreCase(ob.LIGHT_ON)||cmd.equalsIgnoreCase(ob.LIGHT_OFF))
				{
					if(cmd.equalsIgnoreCase(ob.LIGHT_ON) && ob.lightStatus==false)
					{
						ob.toggleLight(true);
					}
					else if(cmd.equalsIgnoreCase(ob.LIGHT_OFF) && ob.lightStatus==true)
					{
						ob.toggleLight(true);
					}
				}
				
				else if(cmd.equalsIgnoreCase(ob.FAN_ON)||cmd.equalsIgnoreCase(ob.FAN_OFF))
				{
					if(cmd.equalsIgnoreCase(ob.FAN_ON) && ob.fanStatus==false)
					{
						ob.toggleFan(true);
					}
					else if(cmd.equalsIgnoreCase(ob.FAN_OFF) && ob.fanStatus==true)
					{
						ob.toggleFan(true);
					}
				}
				
				else if(cmd.equalsIgnoreCase(ob.FLASHLIGHT_ON)||cmd.equalsIgnoreCase(ob.FLASHLIGHT_OFF))
				{
					if(cmd.equalsIgnoreCase(ob.FLASHLIGHT_ON) && ob.flashlightStatus==false)
					{
						ob.toggleFlashlight(true);
					}
					else if(cmd.equalsIgnoreCase(ob.FLASHLIGHT_OFF) && ob.flashlightStatus==true)
					{
						ob.toggleFlashlight(true);
					}
				}
				
				else if(cmd.equalsIgnoreCase(ob.SOS_ON)||cmd.equalsIgnoreCase(ob.SOS_OFF))
				{
					if(cmd.equalsIgnoreCase(ob.SOS_ON) && ob.sosStatus==false)
					{
						ob.toggleSos(true);
					}
					else if(cmd.equalsIgnoreCase(ob.SOS_OFF) && ob.sosStatus==true)
					{
						ob.toggleSos(true);
					}
				}
				
				else if(cmd.startsWith("incoming call from") && !cmd.endsWith("null"))
				{
					ob.toggleIncomingCall(cmd);
				}
				
				else if(cmd.equalsIgnoreCase("call connected"))
				{
					ob.connectedAlert();
				}
				
				else if(cmd.equalsIgnoreCase("call disconnected"))
				{
					ob.disconnectedAlert();
				}
					
			}
			catch(Exception ex)
			{
				//JOptionPane.showMessageDialog(null, "input output error");
				ob.phoneStatusLabel.setText("Phone Disconnected");
				isClientConnected=false;
			}
		}
	}
	
  }
}
