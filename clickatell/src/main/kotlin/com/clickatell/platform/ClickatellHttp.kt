package com.clickatell.platform

import com.clickatell.platform.data.Message
import com.clickatell.platform.util.MessageUtil

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.net.UnknownHostException

/**
 * This is an example of how to use the Clickatell HTTP API. NOTE: this is not
 * the only way, this is just an example. This class can also be used as a
 * library if you wish.
 *
 * @date July 2, 2018
 * @author Michelan Arendse <michelan.arendse></michelan.arendse>@clickatell.com>
 */
class ClickatellHttp
/**
 * Create a HTTP object, and set the auth, but not test the auth.
 */
    (
    /**
     * @var The private variable to use for authentication.
     */
    private val apiKey: String
) {

    /**
     * This sends a single message.
     *
     * @param number
     * The number that you wish to send to. This should be in
     * international format.
     * @param messageContent
     * The message you want to send,
     *
     * @throws Exception
     * This gets thrown on an auth failure.
     * @return An array of Message objects that contains the resulting information.
     */
    @Throws(Exception::class)
    fun sendMessage(number: String, messageContent: String): Array<Message> {
        // Build Parameters:
        val urlParameters = ("apiKey="
                + URLEncoder.encode(this.apiKey, "UTF-8") + "&to="
                + URLEncoder.encode(number, "UTF-8") + "&content="
                + URLEncoder.encode(messageContent, "UTF-8"))

        // Send Request:
        val result = this.excutePost(MessageUtil.CLICKATELL_HTTP_BASE_URL + "messages/http/send?", urlParameters)

        return MessageUtil.stringToMessage(result)
    }

    /**
     * This executes a POST query with the given parameters.
     *
     * @param targetURL
     * The URL that should get hit.
     * @param urlParameters
     * The data you want to send via the POST.
     *
     * @return The content of the request.
     * @throws UnknownHostException
     */
    @Throws(UnknownHostException::class)
    private fun excutePost(targetURL: String, urlParameters: String): String {
        val url: URL
        var connection: HttpURLConnection? = null
        try {
            // Create connection
            url = URL(targetURL + urlParameters)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")

            connection.useCaches = false
            connection.doInput = true

            // Get Response
            val `is` = connection.inputStream
            val rd = BufferedReader(InputStreamReader(`is`))
            var line: String
            val response = StringBuffer()
            //while ((line = rd.readLine()) != null) {
            for (line in rd.lines()) {
                response.append(line)
                response.append('\n')
            }
            rd.close()
            return response.toString()
        } catch (e: UnknownHostException) {
            throw e
        } catch (e: Exception) {
            return ""
        } finally {
            connection?.disconnect()
        }
    }
}