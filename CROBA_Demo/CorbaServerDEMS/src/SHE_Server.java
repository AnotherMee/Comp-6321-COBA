import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import DEMSApp.interfaces;
import DEMSApp.interfacesHelper;

public class SHE_Server 
{
	static SHE_ServerImp she_obj;
	public static void main(String args[]) throws Exception
	{
		ORB orb = ORB.init(args, null);
		Thread threadOne = new Thread(new Runnable() 
		{
	        public void run() 
	        {
	            methodOne(orb);
	        }
		});
		     
	    Thread threadTwo = new Thread(new Runnable() 
	    {
	        public void run() 
	        {
	            methodTwo();
	        }
	    });
		
	    
	    threadOne.start();
	    threadTwo.start();
	    
	    Thread.sleep(1000);
	}
	public static void methodOne(ORB orb)
	{
		try
		{
		      POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		      rootpoa.the_POAManager().activate();
		 
		      // create servant and register it with the ORB
		      she_obj = new SHE_ServerImp();
		      she_obj.setORB(orb); 
		 
		      // get object reference from the servant
		      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(she_obj);
		      interfaces href = interfacesHelper.narrow(ref);
		 
		      org.omg.CORBA.Object objRef =  orb.resolve_initial_references("NameService");
		      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		 
		      NameComponent path[] = ncRef.to_name( "SHE_Server" );
		      ncRef.rebind(path, href);
		 
		      System.out.println("SHE Server ready and waiting ...");
		      she_obj.addEvent("SHEE110620", "Conferences", 0);
		      she_obj.addEvent("SHEE080620", "Conferences", 1);
		      
		      she_obj.bookEvent("QUEC1234","SHEE080620", "Conferences");

		      orb.run();
		     
   
		}
		catch (Exception e) 
		{
	        System.err.println("ERROR: " + e);
	        e.printStackTrace(System.out);
	    }
		
		System.out.println("SHE_Server Exiting ...");
	}
	public static void methodTwo()
	{
		String getInfo;
		String sendInfo = null;
		
		DatagramSocket aSocket = null;
	    try{
				aSocket = new DatagramSocket(6002);
	        	while(true)
		        {
		        	byte[] buffer = new byte[1000];
		        	byte [] m;
	        		System.out.println("SHE_UDP");
	        		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		        	aSocket.receive(request);
		        	getInfo = new String(request.getData()).trim();
		        	if (getInfo.contains("list"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String eventType = info[0];
		        		sendInfo = she_obj.getOwnlistEevntAvailability(eventType);	
		        	}
		        	else if (getInfo.contains("book"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		String eventID = info[1];
		        		String eventType = info[2];
		        		sendInfo = she_obj.bookEvent(customerID, eventID, eventType);
		        	}
		        	else if (getInfo.contains("cshashmap"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		sendInfo = she_obj.getBookingSchedule(customerID);
		        	}
		        	else if (getInfo.contains("cancel"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		String eventID = info[1];
		        		String eventType = info[2];
		        		sendInfo = she_obj.cancelEvent(customerID, eventID, eventType);
		        	}
		        	m = sendInfo.getBytes();
		        	DatagramPacket reply = new DatagramPacket(m, m.length, request.getAddress(), request.getPort());
		        	aSocket.send(reply);
		        }
	        }
	        catch(SocketException e){
	        	System.out.println("Socket: " + e.getMessage());
	        }
	        catch(IOException e) {
	        	System.out.println("IO: " + e.getMessage());
	        }
	}
		
	
		
		
}
