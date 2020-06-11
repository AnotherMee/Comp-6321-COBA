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

public class MTL_Server 
{
	static MTL_ServerImp mtl_obj;
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
		  //ORB orb = ORB.init(args, null);
		  POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
	      rootpoa.the_POAManager().activate();
	 
	      // create servant and register it with the ORB
	      mtl_obj = new MTL_ServerImp();
	      mtl_obj.setORB(orb); 
	      
	      // get object reference from the servant
	      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(mtl_obj);
	      interfaces href = interfacesHelper.narrow(ref);
	 
	      org.omg.CORBA.Object objRef =  orb.resolve_initial_references("NameService");
	      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	 
	      NameComponent path[] = ncRef.to_name( "MTL_Server" );
	      ncRef.rebind(path, href);
	      System.out.println("MTL Server ready and waiting ...");
	      
	      mtl_obj.addEvent("MTLA090620", "Conferences", 2);
	      mtl_obj.addEvent("MTLA080620", "Trade Shows", 2);
	      mtl_obj.addEvent("MTLE230620", "Seminars", 1);
	      mtl_obj.addEvent("MTLA150620", "Trade Shows", 12);
	      
	      mtl_obj.bookEvent("QUEC1234","MTLA090620", "Conferences");
	      mtl_obj.bookEvent("QUEC1234","MTLA080620", "Trade Shows");
	      mtl_obj.bookEvent("QUEC4114","MTLA080620", "Trade Shows");
		  
		  orb.run();
		}
		
		catch (Exception e) 
		{
	        System.err.println("ERROR: " + e);
	        e.printStackTrace(System.out);
	    }
		
		System.out.println("MTL_Server Exiting ...");
	}
	
	public static void methodTwo()
	{
		
		
		
		
		String getInfo = null;
		String sendInfo = null;
		DatagramSocket aSocket = null;
	    try{
				aSocket = new DatagramSocket(6001);
	        	while(true)
		        {
		        	byte[] buffer = new byte[1000];
		        	byte [] m;
	        		System.out.println("MTL UDP");
	        		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		        	aSocket.receive(request);
		        	getInfo = new String(request.getData()).trim();
		        	if (getInfo.contains("list"))
		        	{
		        		System.out.println("reach MTL list");
		        		String[] info = getInfo.split(",");
		        		String eventType = info[0];
		        		sendInfo = mtl_obj.getOwnlistEevntAvailability(eventType);
		        	}
		        	else if (getInfo.contains("book"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		String eventID = info[1];
		        		String eventType = info[2];
		        		sendInfo = mtl_obj.bookEvent(customerID, eventID, eventType);
		        	}
		        	else if (getInfo.contains("cshashmap"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		System.out.println(customerID + " MTLgetinfo");
		        		sendInfo = mtl_obj.getBookingSchedule(customerID);
		        		System.out.println(sendInfo + " sendInfo");
		        	}
		        	else if (getInfo.contains("cancel"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		String eventID = info[1];
		        		String eventType = info[2];
		        		sendInfo = mtl_obj.cancelEvent(customerID, eventID, eventType);
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
