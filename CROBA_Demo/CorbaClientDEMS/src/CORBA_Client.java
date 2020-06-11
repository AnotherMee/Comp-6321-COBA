import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;


import DEMSApp.interfaces;
import DEMSApp.interfacesHelper;



public class CORBA_Client 
{
	public static void main(String[] args) 
	{
	
		//ORB orb = ORB.init(args, null);
		
		while(true)
		{
			System.out.println("Please enter your UserID or enter 0 to exit");
			Scanner scan=new Scanner(System.in);
			String userID = scan.nextLine();
		    
			String user = userID.substring(3, 4);
			String serverNam = userID.substring(0, 3);
			String result = null;
			// Created User file
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			sf.setLenient(false);
			String fileName = userID;
			File file = new File("D:\\test_files\\" + fileName);
			
			if (file.exists() == false)
			{
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// Created QUE file
			String QUE_fileName = "QUE_Server";
			File QUE_file = new File("D:\\test_files\\" + QUE_fileName);
			
			if (QUE_file.exists() == false)
			{
				try {
					QUE_file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// Created MTL file
			String MTL_fileName = "MTL_Server";
			File MTL_file = new File("D:\\test_files\\" + MTL_fileName);
			
			if (MTL_file.exists() == false)
			{
				try {
					MTL_file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// Created SHE file
			
			String SHE_fileName = "SHE_Server";
			File SHE_file = new File("D:\\test_files\\" + SHE_fileName);
			
			if (SHE_file.exists() == false)
			{
				try {
					SHE_file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (user.equals("M"))
			{
				
				if (serverNam.equals("QUE"))
				{
					try
					{
						ORB orb = ORB.init(args, null);
					    org.omg.CORBA.Object objRef =   orb.resolve_initial_references("NameService");
					    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
					    interfaces que_obj = (interfaces) interfacesHelper.narrow(ncRef.resolve_str("QUE_Server"));
					  
					    System.out.println("Enter a to add an event, enter r to remove an event, enter ls to list all available event.");
					    String choice = scan.nextLine();
					
						if (choice.equals("a"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the BookingCapacity");
							String bookCap = scan.nextLine();						
							int cap = Integer.parseInt(bookCap);
							result = que_obj.addEvent(eventID, eventType, cap);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventID + eventType + cap + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (choice.equals("r"))
						{
							System.out.println("Please enter the eventID");
							String eventID1 = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType1 = scan.nextLine();
							result = que_obj.removeEvent(eventID1, eventType1);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventID1 + eventType1 + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (choice.contentEquals("ls"))
						{
							System.out.println("Please enter the eventType");
							String eventType2 = scan.nextLine();
							result = que_obj.listEventAvailability(eventType2);	
							System.out.println(result); 
							
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventType2 + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						// book event
						else if (choice.equals("b"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = que_obj.bookEvent(customerID, eventID, eventType);
							System.out.print(result + "/n");
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						// get book events
						else if (choice.equals("g"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = que_obj.getBookingSchedule(customerID);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						// cancel event
						else if (choice.contentEquals("c"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = que_obj.cancelEvent(customerID, eventID, eventType);
							System.out.println(result + "/n");
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (choice.contentEquals("s"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							System.out.println("Please enter the old eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the old eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the new eventID");
							String new_eventID = scan.nextLine();
							System.out.println("Please enter the new eventType");
							String new_eventType = scan.nextLine();
							result = que_obj.swapEvent(customerID, new_eventID, new_eventType, eventID, eventType);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + customerID + eventID + eventType + new_eventID + new_eventType +result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						
					}
					catch (Exception e) {
				          System.out.println("Client exception: " + e);
					  e.printStackTrace();
				       }
				}
				
				else if (serverNam.equals("MTL"))
				{
					try
					{

						ORB orb = ORB.init(args, null);
					    org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
					    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
					    interfaces mtl_obj = (interfaces) interfacesHelper.narrow(ncRef.resolve_str("MTL_Server"));
					
						System.out.println("Enter a to add an event, enter r to remove an event, enter ls to list all available event.");
						String mtl_choice = scan.nextLine();
						if (mtl_choice.equals("a"))
						{
							
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the BookingCapacity");
							String bookCap = scan.nextLine();
							int bc = Integer.parseInt(bookCap);
							result = mtl_obj.addEvent(eventID, eventType, bc);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + eventID + eventType + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}				
						}
						else if (mtl_choice.equals("r"))
						{
							System.out.println("Please enter the eventID");
							String eventID1 = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType1 = scan.nextLine();
							result = mtl_obj.removeEvent(eventID1, eventType1);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + eventID1 + eventType1 + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (mtl_choice.contentEquals("ls"))
						{
							System.out.println("Please enter the eventType");
							String eventType2 = scan.nextLine();
							result = mtl_obj.listEventAvailability(eventType2);	
							System.out.println(result); 
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + eventType2 + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						
						else if (mtl_choice.equals("b"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							//String server = eventID.substring(0,3);
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = mtl_obj.bookEvent(customerID, eventID, eventType);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + eventID + eventType + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						
						}
						else if (mtl_choice.equals("g"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = mtl_obj.getBookingSchedule(customerID);
							System.out.println(result);	
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (mtl_choice.contentEquals("c"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							//String server = eventID.substring(0,3);
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = mtl_obj.cancelEvent(customerID, eventID, eventType);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + customerID + eventID + eventType + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (mtl_choice.contentEquals("s"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							System.out.println("Please enter the old eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the old eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the new eventID");
							String new_eventID = scan.nextLine();
							System.out.println("Please enter the new eventType");
							String new_eventType = scan.nextLine();
							result = mtl_obj.swapEvent(customerID, new_eventID, new_eventType, eventID, eventType);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + customerID + eventID + eventType + new_eventID + new_eventType +result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					catch (Exception e) {
				          System.out.println("Client exception: " + e);
					  e.printStackTrace();
				       }
				}
				
				else if (serverNam.equals("SHE"))
				{
					try
					{
						ORB orb = ORB.init(args, null);
					    org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
					    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
					    interfaces she_obj = (interfaces) interfacesHelper.narrow(ncRef.resolve_str("SHE_Server"));
				
						System.out.println("Enter a to add an event, enter r to remove an event, enter ls to list all available event.");
						String she_choice = scan.nextLine();
				
						if (she_choice.equals("a"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the BookingCapacity");
							String bookCap = scan.nextLine();
							int bc = Integer.parseInt(bookCap);
							result = she_obj.addEvent(eventID, eventType, bc);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventID + eventType + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						
						}
						else if (she_choice.equals("r"))
						{
							System.out.println("Please enter the eventID");
							String eventID1 = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType1 = scan.nextLine();
							result = she_obj.removeEvent(eventID1, eventType1);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventID1 + eventType1 + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (she_choice.contentEquals("ls"))
						{
							System.out.println("Please enter the eventType");
							String eventType2 = scan.nextLine();
							result = she_obj.listEventAvailability(eventType2);	
							System.out.println(result); 
							
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventType2 + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (she_choice.equals("b"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = she_obj.bookEvent(customerID, eventID, eventType);
							System.out.println(result);
							
	
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						
						}
						else if (she_choice.equals("g"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = she_obj.getBookingSchedule(customerID);
							System.out.println(result);
	
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (she_choice.contentEquals("c"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = she_obj.cancelEvent(customerID, eventID, eventType);
							System.out.println(result);
							
	
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						
						else if (she_choice.contentEquals("s"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							System.out.println("Please enter the old eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the old eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the new eventID");
							String new_eventID = scan.nextLine();
							System.out.println("Please enter the new eventType");
							String new_eventType = scan.nextLine();
							result = she_obj.swapEvent(customerID, new_eventID, new_eventType, eventID, eventType);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + customerID + eventID + eventType + new_eventID + new_eventType +result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					catch (Exception e) {
				          System.out.println("Client exception: " + e);
					  e.printStackTrace();
				       }
				}
			}
			// Customer Client///////////////////////////////////////////////////////////////////////////////////////////
			else
			{
				// QUE
				if(serverNam.equals("QUE"))
				{
					try
					{
						ORB orb = ORB.init(args, null);
					    org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
					    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
					    interfaces que_obj = (interfaces) interfacesHelper.narrow(ncRef.resolve_str("QUE_Server"));
					
						System.out.println("Enter b to book an event, enter g to get your booking schedule, enter c to cancel event, enter s to swap events");
						String choice = scan.nextLine();
					
						// book event
						if (choice.equals("b"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = que_obj.bookEvent(customerID, eventID, eventType);
							System.out.print(result + "/n");
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						// get book events
						else if (choice.equals("g"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = que_obj.getBookingSchedule(customerID);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						// cancel event
						else if (choice.contentEquals("c"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = que_obj.cancelEvent(customerID, eventID, eventType);
							System.out.println(result + "/n");
							try 
							{
								FileWriter fw = new FileWriter(QUE_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (choice.contentEquals("s"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							System.out.println("Please enter the old eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the old eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the new eventID");
							String new_eventID = scan.nextLine();
							System.out.println("Please enter the new eventType");
							String new_eventType = scan.nextLine();
							result = que_obj.swapEvent(customerID, new_eventID, new_eventType, eventID, eventType);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "QUE_Server" + customerID + eventID + eventType + new_eventID + new_eventType +result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					catch (Exception e) {
				          System.out.println("Client exception: " + e);
					  e.printStackTrace();
				       }
				}	
				else if (serverNam.equals("MTL"))
				{
					
					try 
					{
						ORB orb = ORB.init(args, null);
					    org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
					    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
					    interfaces mtl_obj = (interfaces) interfacesHelper.narrow(ncRef.resolve_str("MTL_Server"));
					
						System.out.println("Enter b to book an event, enter g to get your booking schedule, enter c to cancel event.");
						String mtl_choice = scan.nextLine();
					
						if (mtl_choice.equals("b"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							//String server = eventID.substring(0,3);
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = mtl_obj.bookEvent(customerID, eventID, eventType);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + eventID + eventType + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						
						}
						else if (mtl_choice.equals("g"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = mtl_obj.getBookingSchedule(customerID);
							System.out.println(result);	
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (mtl_choice.contentEquals("c"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							//String server = eventID.substring(0,3);
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = mtl_obj.cancelEvent(customerID, eventID, eventType);
							System.out.println(result);
							
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + customerID + eventID + eventType + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (mtl_choice.contentEquals("s"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							System.out.println("Please enter the old eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the old eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the new eventID");
							String new_eventID = scan.nextLine();
							System.out.println("Please enter the new eventType");
							String new_eventType = scan.nextLine();
							result = mtl_obj.swapEvent(customerID, new_eventID, new_eventType, eventID, eventType);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "MTL_Server" + customerID + eventID + eventType + new_eventID + new_eventType +result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					catch (Exception e) {
				          System.out.println("Client exception: " + e);
					  e.printStackTrace();
				       }
				}		
				else if (serverNam.equals("SHE"))
				{
					try 
					{
						ORB orb = ORB.init(args, null);
					    org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
					    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
					    interfaces she_obj = (interfaces) interfacesHelper.narrow(ncRef.resolve_str("SHE_Server"));
				
						System.out.println("Enter b to book an event, enter g to get your booking schedule, enter c to cancel event.");
						String she_choice = scan.nextLine();
				
						if (she_choice.equals("b"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = she_obj.bookEvent(customerID, eventID, eventType);
							System.out.println(result);
							
	
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						
						}
						else if (she_choice.equals("g"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = she_obj.getBookingSchedule(customerID);
							System.out.println(result);
	
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else if (she_choice.contentEquals("c"))
						{
							System.out.println("Please enter the eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							result = she_obj.cancelEvent(customerID, eventID, eventType);
							System.out.println(result);
							
	
							try 
							{
								FileWriter fw = new FileWriter(SHE_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + eventID + eventType + customerID + result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						
						else if (she_choice.contentEquals("s"))
						{
							System.out.println("Please enter customerID");
							String customerID = scan.nextLine();
							System.out.println("Please enter the old eventID");
							String eventID = scan.nextLine();
							System.out.println("Please enter the old eventType");
							String eventType = scan.nextLine();
							System.out.println("Please enter the new eventID");
							String new_eventID = scan.nextLine();
							System.out.println("Please enter the new eventType");
							String new_eventType = scan.nextLine();
							result = she_obj.swapEvent(customerID, new_eventID, new_eventType, eventID, eventType);
							System.out.println(result);
							try 
							{
								FileWriter fw = new FileWriter(MTL_file,true);
								fw.write(sf.format(new Date()) + "SHE_Server" + customerID + eventID + eventType + new_eventID + new_eventType +result);
								fw.flush();
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					catch (Exception e) {
				          System.out.println("Client exception: " + e);
					  e.printStackTrace();
				       }
				}
			}
			try 
			{
				FileWriter fw = new FileWriter(file,true);
				fw.write(result);
				fw.flush();
				fw.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
