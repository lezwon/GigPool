package test.lezwon.firstapp;

import java.util.Date;

/**
 * Created by Lezwon on 07-06-2016.
 */
class Message {

    private String message;
    private Date time;
    private boolean read;

    Message(String message){
        this.message = message;
        time = new Date();
        read = false;
    }


    String getMessage() {
        return message;
    }

    public Date getTime() {
        return time;
    }

    public boolean isRead() {
        return read;
    }
}
