package com.clickatell.platform.test;

import com.clickatell.platform.ClickatellHttp;
import com.clickatell.platform.data.Message;

/**
 * @date July 3, 2018
 * @author Michelan Arendse <michelan.arendse@clickatell.com>
 */
public class TestClickatellHttp {

    public static void main(String [] args) throws Exception {
        ClickatellHttp http = new ClickatellHttp("");
        Message[] messages = http.sendMessage("", "testing");

        for(Message message : messages) {
            System.out.println(message.toString());
        }
    }
}