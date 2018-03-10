package org.ansurgen.jchat;

import java.util.*;


public class Message{

   private User   sender;
   private String content;
   private Date   date;

   public Message(User _sender, String _content){
      sender    = _sender;
      content   = _content;
      date      = new Date();
   }

   public User getSender(){
      return sender;
   }

   public int getSenderId(){
      return sender.getId();
   }

   public String getSenderName(){
      return sender.getName();
   }

   public String getSenderEmail(){
      if (sender.getEmail() != null && !sender.getEmail().equals("")) {
         return sender.getEmail();
      }
      return null;
   }

   public String getContent(){
      return content;
   }

   public Date getDate(){
      return date;
   }

}
