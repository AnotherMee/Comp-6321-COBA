package DEMSApp;


/**
* DEMSApp/interfacesPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from DEMSApp.idl
* Wednesday, February 26, 2020 6:07:09 PM EST
*/

public abstract class interfacesPOA extends org.omg.PortableServer.Servant
 implements DEMSApp.interfacesOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("addEvent", new java.lang.Integer (0));
    _methods.put ("removeEvent", new java.lang.Integer (1));
    _methods.put ("listEventAvailability", new java.lang.Integer (2));
    _methods.put ("getOwnlistEevntAvailability", new java.lang.Integer (3));
    _methods.put ("bookEvent", new java.lang.Integer (4));
    _methods.put ("getBookingSchedule", new java.lang.Integer (5));
    _methods.put ("cancelEvent", new java.lang.Integer (6));
    _methods.put ("swapEvent", new java.lang.Integer (7));
    _methods.put ("shutdown", new java.lang.Integer (8));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // DEMSApp/interfaces/addEvent
       {
         String eventID = in.read_string ();
         String eventType = in.read_string ();
         int bookCap = in.read_long ();
         String $result = null;
         $result = this.addEvent (eventID, eventType, bookCap);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // DEMSApp/interfaces/removeEvent
       {
         String eventID = in.read_string ();
         String eventType = in.read_string ();
         String $result = null;
         $result = this.removeEvent (eventID, eventType);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // DEMSApp/interfaces/listEventAvailability
       {
         String eventType = in.read_string ();
         String $result = null;
         $result = this.listEventAvailability (eventType);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // DEMSApp/interfaces/getOwnlistEevntAvailability
       {
         String eventType = in.read_string ();
         String $result = null;
         $result = this.getOwnlistEevntAvailability (eventType);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 4:  // DEMSApp/interfaces/bookEvent
       {
         String customerID = in.read_string ();
         String eventID = in.read_string ();
         String eventType = in.read_string ();
         String $result = null;
         $result = this.bookEvent (customerID, eventID, eventType);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // DEMSApp/interfaces/getBookingSchedule
       {
         String customerID = in.read_string ();
         String $result = null;
         $result = this.getBookingSchedule (customerID);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 6:  // DEMSApp/interfaces/cancelEvent
       {
         String customerID = in.read_string ();
         String eventID = in.read_string ();
         String eventType = in.read_string ();
         String $result = null;
         $result = this.cancelEvent (customerID, eventID, eventType);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 7:  // DEMSApp/interfaces/swapEvent
       {
         String customerID = in.read_string ();
         String newEventID = in.read_string ();
         String newEventType = in.read_string ();
         String oldEventID = in.read_string ();
         String oldEventType = in.read_string ();
         String $result = null;
         $result = this.swapEvent (customerID, newEventID, newEventType, oldEventID, oldEventType);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 8:  // DEMSApp/interfaces/shutdown
       {
         this.shutdown ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:DEMSApp/interfaces:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public interfaces _this() 
  {
    return interfacesHelper.narrow(
    super._this_object());
  }

  public interfaces _this(org.omg.CORBA.ORB orb) 
  {
    return interfacesHelper.narrow(
    super._this_object(orb));
  }


} // class interfacesPOA