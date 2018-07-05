package com.clickatell.platform.data;

/**
 * This is a util to create a Message object from the response string
 *
 * @date July 4, 2018
 *  * @author Michelan Arendse <michelan.arendse@clickatell.com>
 */

public class MessageRequest{
	    String fromNumber;
	    String[] toNumbers;
	    String message;

	    public void setFromNumber(String fromNumber){
	        this.fromNumber = fromNumber;
        }

        public String getFromNumber(){
	        return fromNumber;
        }

        public void setToNumbers(String... toNumbers){
	        this.toNumbers = toNumbers;
        }

        public String[] getToNumbers(){
	        return toNumbers;
        }

        public void setMessage(String message){
	        this.message = message;
        }

        public String getMessage(){
	        return message;
        }
    }