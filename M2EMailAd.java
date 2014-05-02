import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.swing.text.html.HTMLDocument.Iterator;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class M2EMailAd {

	static HashMap<Integer,Integer> M2Products = new HashMap<Integer, Integer>();
	static HashMap<Integer,Integer> M2Products1 = new HashMap<Integer, Integer>();
    static HashMap<Integer,String> EligibleCust = new HashMap<Integer, String>();
     static HashMap<Integer,String> M2EligibleCust  = new HashMap<Integer, String>();
     static int maxdisc=0;
public M2EMailAd() {
// TODO Auto-generated constructor stub
}

   
   public static void main (String [] args){
       {
           try{
             
           String fileName= "C://Users/TSeby/Downloads/M1cust.csv";;
           String fileName1= "C://Users/TSeby/Downloads/M2cust.csv";
           String storeFile= "C://Users/TSeby/Downloads/store.csv";                   
                   File file1 = new File(fileName1);
                   File sf = new File(storeFile);
                   //Reading from files
                   BufferedReader M2CustRdr = new BufferedReader(new FileReader(file1));
                   BufferedReader sbufreader = new BufferedReader(new FileReader(sf));
                   String line = null;String line1=null; int cnt=0; int cnt1=0;    
                   int M2custcnt=0; int M2custcnt1 = 0;
                  
              while((line = sbufreader.readLine()) != null) 
                {         String str[] = line.split(",");            
                           if(str[0].equals("M2")&& str[5].equals("M")){   
                      M2Products .put(cnt, Integer.parseInt(str[6]));
                            cnt++;   }
                           if(str[0].equals("M2")&& str[5].equals("F")){   
                               M2Products1 .put(cnt1, Integer.parseInt(str[6])); 
                                     cnt1++; }                                                               
                   }
                  sbufreader.close(); 
                                
                   while((line1 = M2CustRdr.readLine()) != null) 
                   {
                       
                           String str[] = line1.split(",");
                                                 
                           if(str[2].equals("M")){
                        	   
                      M2EligibleCust.put(M2custcnt,str[4]); ///storing the email ids of Male Customers
                        M2custcnt++;
                       }
                           
                           
                           if(str[2].equals("F")){
                        	   
                               EligibleCust.put(M2custcnt1,str[4]); ///storing the email ids of Female Customers
                                 M2custcnt1++;
                                }
                       
                          
                   }
                 M2CustRdr.close();
               SendMail();    
                   
                   
           }catch(Exception e){}
           
           
         
           
         
       Map.Entry<Integer, Integer> maxEntry = null;

    for (Map.Entry<Integer, Integer> entry : M2Products.entrySet())
    {
       if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
       {
           maxEntry = entry;
       }
    }
   
    maxdisc=maxEntry.getValue();
   }
 
       
       
    
}
   public static void SendMail( )  {
   
   
       String username = "tessy2113@gmail.com";
       String password = "satt1321";
       String recipient = "tsebast2@asu.edu";

       Properties props = new Properties();

       props.put("mail.smtp.host", "smtp.gmail.com");
       props.put("mail.from", "tessy2113@gmail.com");
       props.put("mail.smtp.starttls.enable", "true");
       props.put("mail.smtp.port", "587");
       props.setProperty("mail.debug", "true");
       try{
       Session session = Session.getInstance(props, null);
       MimeMessage msg = new MimeMessage(session);
        for (Map.Entry<Integer, String> entry : M2EligibleCust.entrySet())
   {

      // msg.setRecipients(Message.RecipientType.TO, recipient);
         msg.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress(entry.getValue()));
       
       msg.setSubject("MEN'S CLOTHING NEW ARRIVALS !!! MACYS CHICAGO");
       msg.setText("MEN'S POLO SHIRTS AVAILABLE IN BLACK/BLUE/RED/GREEN COLORS only for $ 5");
    BodyPart messageBodyPart = new MimeBodyPart();
       Transport transport = session.getTransport("smtp");

       transport.connect(username, password);
       transport.sendMessage(msg, msg.getAllRecipients());
       
       transport.close();
   }
         
         for (Map.Entry<Integer, String> entry : EligibleCust.entrySet())
         {

            // msg.setRecipients(Message.RecipientType.TO, recipient);
               msg.addRecipient(Message.RecipientType.TO,
                                       new InternetAddress(entry.getValue()));
             
             msg.setSubject("WOMEN'S CLOTHING AND ACCESSORIES NEW ARRIVALS !!! MACYS CHICAGO");
             msg.setText("WOMEN'S SKIRTS AVAILABLE IN BLACK/BLUE/RED/GREEN COLORS only for $ 15\n" + "WOMEN'S BULOVA 26mm DIAL WATCH FOR JUST $50 ");
          BodyPart messageBodyPart = new MimeBodyPart();
             Transport transport = session.getTransport("smtp");

             transport.connect(username, password);
             transport.sendMessage(msg, msg.getAllRecipients());
             
             transport.close();
         }
         
         
         
   }catch (MessagingException mex) {
        mex.printStackTrace();
     } 


    
}

}

