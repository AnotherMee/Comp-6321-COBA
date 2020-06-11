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




public class QUE_Server 
{
	static QUE_ServerImp que_obj;
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
		//ORB orb = ORB.init(args, null);		
		try
		{
		      POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		      rootpoa.the_POAManager().activate();
		 
		      // create servant and register it with the ORB
		      que_obj = new QUE_ServerImp();
		      que_obj.setORB(orb); 
		      
		      // get object reference from the servant
		      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(que_obj);
		      interfaces href = interfacesHelper.narrow(ref);
		 
		      org.omg.CORBA.Object objRef =  orb.resolve_initial_references("NameService");
		      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		 
		      NameComponent path[] = ncRef.to_name( "QUE_Server" );
		      ncRef.rebind(path, href);
		 
		      System.out.println("QUE Server ready and waiting ...");
		      
		      orb.run();

		}
		
		catch (Exception e) 
		{
	        System.err.println("ERROR: " + e);
	        e.printStackTrace(System.out);
	    }
		
		System.out.println("QUE_Server Exiting ...");    
	}
	public static void methodTwo()
	{
		//QUE_ServerImp que_Inters = new QUE_ServerImp();
		String getInfo;
		String sendInfo = null;
		DatagramSocket aSocket = null;
	    try{
				aSocket = new DatagramSocket(6000);
	        	while(true)
		        {
	        		byte[] buffer = new byte[1000];
	        		byte [] m;
	        		System.out.println("QUE_UDP");
		        	DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		        	aSocket.receive(request);
		        	getInfo = new String(request.getData()).trim();
		        	if (getInfo.contains("list"))
		        	{
		        		String[] info = getInfo.split(",");
		        		System.out.println(getInfo);
		        		String eventType = info[0];
		        		System.out.println(eventType);
		        		sendInfo = que_obj.getOwnlistEevntAvailability(eventType);	
		        		System.out.println(sendInfo);
		        	}
		        	else if (getInfo.contains("book"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		String eventID = info[1];
		        		String eventType = info[2];
		        		sendInfo = que_obj.bookEvent(customerID, eventID, eventType);
		        	}
		        	else if (getInfo.contains("cshashmap"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		sendInfo = que_obj.getBookingSchedule(customerID);
		        	}
		        	else if (getInfo.contains("cancel"))
		        	{
		        		String[] info = getInfo.split(",");
		        		String customerID = info[0];
		        		String eventID = info[1];
		        		String eventType = info[2];
		        		sendInfo = que_obj.cancelEvent(customerID, eventID, eventType);
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
