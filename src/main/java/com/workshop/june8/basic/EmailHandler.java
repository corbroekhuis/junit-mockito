package com.workshop.june8.basic;

public class EmailHandler {

    private int sent = 0;

    public void sendMail( String message, String emailAddress) throws Exception{

        if( emailAddress.indexOf("@") < 0){
            throw new Exception("Not a valid email address");
        }

        byte[] encrypted = encryptMail(message);

        sent ++;
        // Send the email id '@' is in the emailaddress

    }

    private byte[] encryptMail( String message){
        return message.getBytes();
    }

    public int getSent(){
        return sent;
    }

}
