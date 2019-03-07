package com.clickatell.platform

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException

import com.clickatell.platform.data.Message
import com.clickatell.platform.data.MessageRequest
import com.clickatell.platform.util.MessageUtil

/**
 * This is an example of how to use the Clickatell REST API. NOTE: this is not
 * the only way, this is just an example. This class can also be used as a
 * library if you wish.
 *
 * @date July 2, 2018
 * @author Michelan Arendse <michelan.arendse></michelan.arendse>@clickatell.com>
 */
class ClickatellRest
/**
 * Create a REST object
 */
    (
    /**
     * @var The private variable to use for authentication.
     */
    private val apiKey: String
) {
    private val token: String? = null

    @Throws(Exception::class)
    fun sendMessage(message: String, vararg numbers: String): Array<Message> {
        val messageRequest = MessageRequest()
        messageRequest.message = message
        messageRequest.setToNumbers(*numbers)

        return sendMessage(messageRequest)
    }

    /**
     * This is to send the same message to multiple people.
     *
     * @param messageRequest
     * The object of sms messages that are to be sent to.
     *
     * @return An Array of Message objects which will contain the information
     * from the request.
     *
     * @throws Exception
     * This gets thrown on auth errors.
     */
    @Throws(Exception::class)
    fun sendMessage(messageRequest: MessageRequest): Array<Message> {
        var number = messageRequest.toNumbers[0]
        for (x in 1 until messageRequest.toNumbers.size) {
            number += "\",\"" + messageRequest.toNumbers[x]
        }

        val fromNumber = if (messageRequest.fromNumber != null) messageRequest.fromNumber else ""

        // Send Request:
        val response = this.execute(
            "messages", POST, "{\"to\":[\"" + number
                    + "\"],\"content\":\"" + messageRequest.fromNumber + "\", \"from\":\"" + fromNumber + "\"}"
        )

        return MessageUtil.stringToMessage(response)
    }

    /**
     * This executes a POST query with the given parameters.
     *
     * @param resource
     * The URL that should get hit.
     * The data you want to send via the POST.
     *
     * @return The content of the request.
     * @throws UnknownHostException
     */
    @Throws(UnknownHostException::class)
    private fun execute(resource: String, method: String, dataPacket: String?): String {
        val url: URL
        var connection: HttpURLConnection? = null
        try {
            // Create connection
            url = URL(MessageUtil.CLICKATELL_HTTP_BASE_URL + resource)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = method
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("X-Version", "1")
            connection.setRequestProperty("Authorization", apiKey)
            var l = "0"
            if (dataPacket != null) {
                l = Integer.toString(dataPacket.toByteArray().size)
            }
            connection.setRequestProperty("Content-Length", "" + l)
            connection.setRequestProperty("Content-Language", "en-US")

            connection.useCaches = false
            connection.doInput = true
            connection.doOutput = dataPacket != null

            // Send request
            if (dataPacket != null) {
                val wr = DataOutputStream(
                    connection.outputStream
                )
                wr.writeBytes(dataPacket)
                wr.flush()
                wr.close()
            }

            // Get Response
            connection.responseCode
            var stream: InputStream? = connection.errorStream
            if (stream == null) {
                stream = connection.inputStream
            }
            val rd = BufferedReader(
                InputStreamReader(stream!!)
            )
            var line: String
            val response = StringBuffer()
            //while ((line = rd.readLine()) != null) {
            for (line in rd.lines()) {
                response.append(line)
                response.append('\n')
            }
            rd.close()
            return response.toString().trim { it <= ' ' }
        } catch (e: UnknownHostException) {
            throw e
        } catch (e: Exception) {
            return ""
        } finally {
            connection?.disconnect()
        }
    }

    companion object {

        private val POST = "POST"
    }
}