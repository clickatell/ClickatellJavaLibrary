package com.clickatell.platform.data

/**
 * This is a util to create a Message object from the response string
 *
 * @date July 4, 2018
 * * @author Michelan Arendse <michelan.arendse></michelan.arendse>@clickatell.com>
 */

class MessageRequest {
    lateinit var fromNumber: String
    internal lateinit var toNumbers: Array<String>
    lateinit var message: String

    fun setToNumbers(vararg toNumbers: String) {
        this.toNumbers = toNumbers as Array<String>
    }

    fun getToNumbers(): Array<String> {
        return toNumbers
    }
}