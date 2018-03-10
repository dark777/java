package org.ansurgen.jchat;

import java.util.*;

/**
<pre>
org.ansurgen.jchat.Channel
The Channel class represents a unique channel that is
held by the JChat Servlet.  This class holds all of the
User, Message and Private Message objects neccessay to
conduct a chat session.

@author ttimoteo@ansurgen.org
@version 1.0 09-NOV-1998
@see org.ansurgen.jchat.User
@see org.ansurgen.jchat.Message
@see org.ansurgen.jchat.PrivateMessage
</pre>
*/

public class Channel{

   private String name;
   private Vector users, messages, pvt_messages;
   private int    max_msgs, max_users, max_pvt_msgs, max_latent_time;

   public Channel(String _name,
                  int _max_msgs,
                  int _max_users,
                  int _max_pvt_msgs,
                  int _max_latent_time){
      name            = _name;
      max_msgs        = _max_msgs;
      max_users       = _max_users;
      max_pvt_msgs    = _max_pvt_msgs;
      max_latent_time = _max_latent_time;
      users           = new Vector();
      messages        = new Vector();
      pvt_messages    = new Vector();
   }

   /**
   <pre>
   hasMaxUsers()
   @return boolean - true if this channel has max_user User
                     objects in the users Vector, false otherwise
   </pre>
   */
   public boolean hasMaxUsers(){
      if (users.size() >= max_users) {
         return true;
      }
      return false;
   }

   /**
   <pre>
   hasMaxMessages()
   @return boolean - true if this channel has max_msgs Message
                     objects in the messages Vector, false otherwise
   </pre>
   */
   public boolean hasMaxMessages(){
      if (messages.size() >= max_msgs) {
         return true;
      }
      return false;
   }

   /**
   <pre>
   hasMaxPrivateMessages()
   @return boolean - true if this channel has max_pvt_msgs PrivateMessage
                     objects in the pvt_messages Vector, false otherwise
   </pre>
   */
   public boolean hasMaxPrivateMessages(){
      if (pvt_messages.size() >= max_pvt_msgs) {
         return true;
      }
      return false;
   }

   public synchronized void addMessage(Message aMessage){
      refreshUsers();
      if (hasMaxMessages()) {
         messages.removeElement(messages.firstElement());
      }
      messages.addElement(aMessage);
   }

   public synchronized void addPrivateMessage(PrivateMessage aMessage){
      if (hasMaxPrivateMessages()) {
         pvt_messages.removeElement(pvt_messages.firstElement());
      }
      pvt_messages.addElement(aMessage);
   }

   public synchronized Vector getMessages(){
      refreshUsers();
      return messages;
   }

   public synchronized Vector getAllPrivateMessages(){
      return pvt_messages;
   }

   public synchronized Vector getPrivateMessages(User user){
      refreshUsers();
      Vector p_messages = new Vector();

      Enumeration enum = pvt_messages.elements();
      while (enum.hasMoreElements()) {
         PrivateMessage p_msg = (PrivateMessage)enum.nextElement();
         User rcpt = p_msg.getRecipient();
         User send = p_msg.getSender();

         if (user.getId() == rcpt.getId() || user.getId() == send.getId()) {
            p_messages.addElement(p_msg);
            //remove the message
            if (user.getId() == rcpt.getId()) {
               pvt_messages.removeElement(p_msg);
            }
         }
      }
      return p_messages;
   }

   public synchronized int removePrivateMessages(User user){
      int removed = 0;
      Enumeration enum = pvt_messages.elements();
      while (enum.hasMoreElements()) {
         PrivateMessage p_msg = (PrivateMessage)enum.nextElement();
         User rcpt = p_msg.getRecipient();
         if (user.getId() == rcpt.getId()) {
            pvt_messages.removeElement(p_msg);
            removed++;
         }
      }
      return removed;
   }

   public synchronized void removeAllPrivateMessages(){
      pvt_messages.removeAllElements();
   }

   public synchronized void addUser(User aUser)throws Exception{
      if (hasMaxUsers()) {
         throw new Exception("Channel " + name + " is full [" + max_users + " users]");
      }
      users.addElement(aUser);
   }

   public synchronized void removeUser(User aUser){
      users.removeElement(aUser);
   }

   public synchronized Vector getUsers(){
      refreshUsers();
      return users;
   }

   public void refreshUsers(){
      Enumeration enum = users.elements();

      while (enum.hasMoreElements()) {
         User user = (User)enum.nextElement();
         if (!user.refresh()) {
            removeUser(user);
            addMessage(new Message(new User(0,
                                            "JCHAT_SERVLET",
                                            null,
                                            name,
                                            max_latent_time),
                                            "REMOVED FOR INACTIVITY"));
         }
      }
   }


   /**
   <pre>
   size()
   @return int - the number of users logged into this channel
   </pre>
   */
   public synchronized int size(){
      return users.size();
   }

   /**
   <pre>
   getUser
   @param int - a user id
   @return org.ansurgen.jchat.User
   </pre>
   */
   public synchronized User getUser(int uid)throws Exception{
      Enumeration enum = users.elements();
      User user = null;
      while (enum.hasMoreElements()) {
         user = (User)enum.nextElement();
         if (user.getId() == uid) {
            //we found the user...
            break;
         }else{
            user = null;
         }
      }
      if (user == null) {
         throw new Exception("User not found [" + uid + "]");
      }
      return user;
   }

   public String getName(){
      return name;
   }


}
