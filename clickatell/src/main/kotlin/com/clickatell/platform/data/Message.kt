package com.clickatell.platform.data

class Message {
    var error: String? = null
    var number: String? = null
    var charge: String? = null
    var status: String? = null
    var content: String? = null
    var message_id: String? = null
    var statusString: String? = null

    constructor(message_id: String) {
        this.message_id = message_id
    }

    constructor() {}

    override fun toString(): String {
        return if (message_id != null) {
            "$number: $message_id"
        } else "$number: $error"
    }
}

