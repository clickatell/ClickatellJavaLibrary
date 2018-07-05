package com.clickatell.platform.test;

import com.clickatell.platform.ClickatellRest;
import com.clickatell.platform.data.Message;

/**
 * @date July 3, 2018
 * @author Michelan Arendse <michelan.arendse@clickatell.com>
 */
public class TestClickatellRest {
    public static void main(String [] args) throws Exception {
        ClickatellRest rest = new ClickatellRest("");
        Message[] messages = rest.sendMessage("Testing message", "");

        for(Message message : messages) {
            System.out.println(message.toString());
        }
    }
}