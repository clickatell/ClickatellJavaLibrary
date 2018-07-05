package com.clickatell.platform;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.clickatell.platform.data.Message;
import com.clickatell.platform.data.MessageRequest;
import com.clickatell.platform.util.MessageUtil;

/**
 * This is an example of how to use the Clickatell REST API. NOTE: this is not
 * the only way, this is just an example. This class can also be used as a
 * library if you wish.
 *
 * @date July 2, 2018
 * @author Michelan Arendse <michelan.arendse@clickatell.com>
 */
public class ClickatellRest {

	private static final String POST = "POST";

	/**
	 * @var The private variable to use for authentication.
	 */
    private String apiKey, token;

    /**
     * Create a REST object
     */
    public ClickatellRest(String apiKey) {
        this.apiKey = apiKey;
    }

    public Message[] sendMessage(String message, String... numbers)
            throws Exception{
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessage(message);
        messageRequest.setToNumbers(numbers);

        return sendMessage(messageRequest);
    }

    /**
	 * This is to send the same message to multiple people.
	 *
	 * @param messageRequest
	 *            The object of sms messages that are to be sent to.
	 *
	 * @return An Array of Message objects which will contain the information
	 *         from the request.
	 *
	 * @throws Exception
	 *             This gets thrown on auth errors.
	 */
    public Message[] sendMessage(MessageRequest messageRequest)
            throws Exception {
        String number = messageRequest.getToNumbers()[0];
        for (int x = 1; x < messageRequest.getToNumbers().length; x++) {
            number += "\",\"" + messageRequest.getToNumbers()[x];
        }

        String fromNumber = messageRequest.getFromNumber() != null ? messageRequest.getFromNumber() : "";

        // Send Request:
        String response = this.execute("messages", POST, "{\"to\":[\"" + number
                + "\"],\"content\":\"" + messageRequest.getFromNumber() + "\", \"from\":\""+fromNumber+"\"}");

        return MessageUtil.stringToMessage(response);
    }

	/**
	 * This executes a POST query with the given parameters.
	 *
	 * @param resource
	 *            The URL that should get hit.
	 *            The data you want to send via the POST.
	 *
	 * @return The content of the request.
	 * @throws UnknownHostException
	 */
	private String execute(String resource, String method, String dataPacket) throws UnknownHostException {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(MessageUtil.CLICKATELL_HTTP_BASE_URL + resource);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("X-Version", "1");
			connection.setRequestProperty("Authorization", apiKey);
			String l = "0";
			if (dataPacket != null) {
				l = Integer.toString(dataPacket.getBytes().length);
			}
			connection.setRequestProperty("Content-Length", "" + l);
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(dataPacket != null);

			// Send request
			if (dataPacket != null) {
				DataOutputStream wr = new DataOutputStream(
						connection.getOutputStream());
				wr.writeBytes(dataPacket);
				wr.flush();
				wr.close();
			}

			// Get Response
			connection.getResponseCode();
			InputStream stream = connection.getErrorStream();
			if (stream == null) {
				stream = connection.getInputStream();
			}
			BufferedReader rd = new BufferedReader(
					new InputStreamReader(stream));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\n');
			}
			rd.close();
			return response.toString().trim();
		} catch (UnknownHostException e) {
			throw e;
		} catch (Exception e) {
			return "";
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}