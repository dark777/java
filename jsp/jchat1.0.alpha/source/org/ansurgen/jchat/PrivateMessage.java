package org.ansurgen.jchat;

public class PrivateMessage extends Message{

   private User recipient;
                        
   public PrivateMessage(User _sender, String _content, User _recipient){
      super(_sender, _content);
      recipient = _recipient;
   }

   public User getRecipient(){
      return recipient;
   }

   public String getRecipientName(){
      return recipient.getName();
   }

   public String getRecipientEmail(){
      if (recipient.getEmail() != null && !recipient.getEmail().equals("")) {
         return recipient.getEmail();
      }
      return null;
   }


}
