package com.clickatell.platform.util;

import com.clickatell.platform.data.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;

/**
 * This is a util to create a Message object from the response string
 *
 * @date July 4, 2018
 *  * @author Michelan Arendse <michelan.arendse@clickatell.com>
 */
public class MessageUtil {

    public static final String CLICKATELL_HTTP_BASE_URL = "https://platform.clickatell.com/";

    public static Message[] stringToMessage(String response) throws Exception{
        JSONObject obj = (JSONObject) JSONValue.parse(response);
        checkForError(obj);
        ArrayList<Message> messages = new ArrayList<Message>();
        JSONArray msgArray = (JSONArray) obj.get("messages");
        for (int i = 0; i < msgArray.size(); i++) {
            Message msg = new Message();
            JSONObject firstMsg = (JSONObject) msgArray.get(i);
            msg.setNumber((String) firstMsg.get("to"));
            if (!((boolean) firstMsg.get("accepted"))) {
                try {
                    checkForError(firstMsg);
                } catch (Exception e) {
                    msg.setError(e.getMessage());
                }
            } else {
                msg.setMessage_id((String) firstMsg.get("apiMessageId"));
            }
            messages.add(msg);
        }
        return messages.toArray(new Message[0]);
    }

    /**
     * This is an internal function used to shorten other functions. Checks for
     * an error object, and throws it.
     *
     * @param obj
     *            The object that needs to be checked.
     * @throws Exception
     *             The exception that was found.
     */
    private static void checkForError(JSONObject obj) throws Exception {
        String objError = (String) obj.get("error");
        if (objError != null) {
            throw new Exception((String) obj.get("errorDescription"));
        }
    }
}
