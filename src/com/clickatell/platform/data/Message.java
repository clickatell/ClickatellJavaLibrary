package com.clickatell.platform.data;

public class Message {
	private String error = null;
	private String number = null;
	private String charge = null;
	private String status = null;
	private String content = null;
	private String message_id = null;
	private String statusString = null;

	public Message(String message_id) {
		this.setMessage_id(message_id);
	}

	public Message() {
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public String toString() {
		if (getMessage_id() != null) {
			return getNumber() + ": " + getMessage_id();
		}
		return getNumber() + ": " + getError();
	}
}

