package org.ansurgen.jchat;

import java.util.*;

/**
<pre>
org.ansurgen.jchat.User
The User class encapsulates all of the properties
of a chat user and represents an independent jchat entity

@author ttimoteo@ansurgen.org
@version 1.0 09-NOV-1998

</pre>
*/
public class User{

   private int    id;
   private int    max_latent_time;
   private String name;
   private String email;
   private String channel_name;
   private long   last_action;
   
   public User(int _id,
               String _name,
               String _email,
               String _channel_name,
               int _max_latent_time){
      id              = _id;
      name            = _name;
      email           = _email;
      channel_name    = _channel_name;
      max_latent_time = _max_latent_time;
      Date date       = new Date();
      last_action     = date.getTime();
   }

   /**
   refresh()
   @return boolean - true if user has not been inactive for more than
                     max_latent_time minutes, false otherwise
   */
   public boolean refresh(){
      Date date          = new Date();
      long current_action = date.getTime();
      int latent_time    = (int)((current_action - last_action)/60000);

      if (latent_time > max_latent_time) {
         return false;
      }
      last_action = current_action;
      return true;
   }

   public int getId(){
      return id;
   }

   public String getName(){
      return name;
   }

   public String getEmail(){
      if (!email.equals("")) {
         return email;
      }
      return null;
   }

   public String getChannelName(){
      return channel_name;
   }

}
