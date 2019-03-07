package com.clickatell.platform.util

import com.clickatell.platform.data.Message
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.JSONValue

import java.util.ArrayList

/**
 * This is a util to create a Message object from the response string
 *
 * @date July 4, 2018
 * * @author Michelan Arendse <michelan.arendse></michelan.arendse>@clickatell.com>
 */
object MessageUtil {

    val CLICKATELL_HTTP_BASE_URL = "https://platform.clickatell.com/"

    @Throws(Exception::class)
    fun stringToMessage(response: String): Array<Message> {
        val obj = JSONValue.parse(response) as JSONObject
        checkForError(obj)
        val messages = ArrayList<Message>()
        val msgArray = obj.get("messages") as JSONArray
        for (i in 0 until msgArray.size) {
            val msg = Message()
            val firstMsg = msgArray.get(i) as JSONObject
            msg.number = firstMsg.get("to") as String?
            if (!(firstMsg.get("accepted") as Boolean)) {
                try {
                    checkForError(firstMsg)
                } catch (e: Exception) {
                    msg.error = e.message
                }

            } else {
                msg.message_id = firstMsg.get("apiMessageId") as String?
            }
            messages.add(msg)
        }
        return messages.toTypedArray()
    }

    /**
     * This is an internal function used to shorten other functions. Checks for
     * an error object, and throws it.
     *
     * @param obj
     * The object that needs to be checked.
     * @throws Exception
     * The exception that was found.
     */
    @Throws(Exception::class)
    private fun checkForError(obj: JSONObject) {
        val objError = obj.get("error") as String
        if (objError != null) {
            throw Exception(obj.get("errorDescription") as String)
        }
    }
}
