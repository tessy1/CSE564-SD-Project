
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



public class M1EmailAd {
static HashMap<Integer,Integer> MiddleAgedM1Discounts = new HashMap<Integer, Integer>();
    static HashMap<Integer,String> M1DiscountEligibleCust = new HashMap<Integer, String>();
     static HashMap<Integer,String> M2DiscountEligibleCust  = new HashMap<Integer, String>();
     static int maxdisc=0;
public M1EmailAd() {
// TODO Auto-generated constructor stub
}

   
   public static void main (String [] args){
       {
           try{
           
           String fileName= "C://Users/TSeby/Downloads/M1cust.csv";
           String fileName1= "C://Users/TSeby/Downloads/M2cust.csv";
           String storeFile= "C://Users/TSeby/Downloads/store.csv";
                   File M1file = new File(fileName);                    
                   File file1 = new File(fileName1);
                   File sf = new File(storeFile);
                   BufferedReader M1CustRdr = new BufferedReader(new FileReader(M1file));
                 
                   BufferedReader sbufreader = new BufferedReader(new FileReader(sf));
                   String line = null;String line1=null; int cnt=0;     int M1custcnt=0;
          
                  
                  while((line = sbufreader.readLine()) != null) 
                   {
                           
                           String str[] = line.split(",");
                        if(str[0].equals("M1")&& Integer.parseInt(str[2])>=20 && Integer.parseInt(str[3])<=45){                            
                           
                      MiddleAgedM1Discounts .put(cnt, Integer.parseInt(str[6])); //saving the discount
                            cnt++;
                           }
                                             
                   }
                 
                sbufreader.close();
                  //read Customer email id's of age between 20 and 45                   
                   while((line1 = M1CustRdr.readLine()) != null) 
                   {
                       
                           String str[] = line1.split(",");
                        if(Integer.parseInt(str[1])>=20 && Integer.parseInt(str[1])<=45){                            
                          
                      M1DiscountEligibleCust.put(M1custcnt,str[4]); ///storing the email ids
                        M1custcnt++;
                       }
                       
                          
                   }
                 M1CustRdr.close();
               SendMail();    
                   
                   
           }catch(Exception e){}
           
           
           //Calculating Max Discount for Middle Aged
           
         
       Map.Entry<Integer, Integer> maxEntry = null;

    for (Map.Entry<Integer, Integer> entry : MiddleAgedM1Discounts.entrySet())
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
         for (Map.Entry<Integer, String> entry : M1DiscountEligibleCust.entrySet())
   {

      // msg.setRecipients(Message.RecipientType.TO, recipient);
         msg.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress(entry.getValue()));
       
       msg.setSubject("DISCOUNT !!! MACYS TEMPE");
       msg.setText("As a Macy's Tempe Branch Customer, u are eligible for the Middle Age Discount of 20%");
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
