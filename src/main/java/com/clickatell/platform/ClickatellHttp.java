package com.clickatell.platform;

import com.clickatell.platform.data.Message;
import com.clickatell.platform.util.MessageUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

/**
 * This is an example of how to use the Clickatell HTTP API. NOTE: this is not
 * the only way, this is just an example. This class can also be used as a
 * library if you wish.
 *
 * @date July 2, 2018
 * @author Michelan Arendse <michelan.arendse@clickatell.com>
 */
public class ClickatellHttp {
	/**
	 * @var The private variable to use for authentication.
	 */
	private String apiKey;

	/**
	 * Create a HTTP object, and set the auth, but not test the auth.
	 */
	public ClickatellHttp(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * This sends a single message.
	 *
	 * @param number
	 *            The number that you wish to send to. This should be in
	 *            international format.
	 * @param messageContent
	 *            The message you want to send,
	 *
	 * @throws Exception
	 *             This gets thrown on an auth failure.
	 * @return An array of Message objects that contains the resulting information.
	 */
	public Message[] sendMessage(String number, String messageContent)
			throws Exception {
		// Build Parameters:
		String urlParameters = "apiKey="
				+ URLEncoder.encode(this.apiKey, "UTF-8") + "&to="
				+ URLEncoder.encode(number, "UTF-8") + "&content="
				+ URLEncoder.encode(messageContent, "UTF-8");

		// Send Request:
		String result = this.excutePost(MessageUtil.CLICKATELL_HTTP_BASE_URL + "messages/http/send?", urlParameters);

		return MessageUtil.stringToMessage(result);
	}

	/**
	 * This executes a POST query with the given parameters.
	 *
	 * @param targetURL
	 *            The URL that should get hit.
	 * @param urlParameters
	 *            The data you want to send via the POST.
	 *
	 * @return The content of the request.
	 * @throws UnknownHostException
	 */
	private String excutePost(String targetURL, String urlParameters)
			throws UnknownHostException {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL + urlParameters);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");

			connection.setUseCaches(false);
			connection.setDoInput(true);

			// Get Response
 			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\n');
			}
			rd.close();
			return response.toString();
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