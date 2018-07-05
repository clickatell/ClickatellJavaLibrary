Clickatell Java Library - Master branch
=====================

You can see our other libraries and more documentation at the [Clickatell APIs and Libraries Project](http://clickatell.github.io/).

------------------------------------

This is a basic library to demonstrate the use of the Clickatell Rest and HTTP APIs.

Usage
-----

The class files can be copied directly into your project, then reference the json-simple-1.1.1.jar library. and then use as follows:

To initialize:

```
ClickatellHttp click = new ClickatellHttp(USERNAME, APIID, PASSWORD);
ClickatellRest clickRest = new ClickatellRest(API_KEY);
```

To send one message:

```
ClickatellHttp.Message response = click.sendMessage("27821234567",
                    "Hello, this is a test message!");
ClickatellRest.Message response = clickRest.sendMessage("27821234567",
                    "Hello, this is a test message!");
```

To get the status of a message:

```
int status = click.getMessageStatus("b305c3445e37626ffabb21edc9320e1e");
```

To get the cost of a message:

```
ClickatellHttp.Message reply = click.getMessageCharge("b305c3445e37626ffabb21edc9320e1e");
System.out.println("Charge: " + reply.charge);
System.out.println("Status: " + reply.status);
```

To get the cost and status of message in REST:

```
ClickatellRest.Message msg = clickRest.getMessageStatus(response.message_id);
System.out.println("ID:" + msg.message_id);
System.out.println("Status:" + msg.status);
System.out.println("Status Description:" + msg.statusString);
System.out.println("Charge:" + msg.charge);
```

To do a coverage check:

```
double reply = click.getCoverage("27820909090");
```

To do a message stop request:

```
click.stopMessage("b305c3445e37626ffabb21edc9320e1e");
```

To check your balance:

```
double balance = click.getBalance();
```

Testing Sample Code
-------------------

Compile:

```
javac -cp json-simple-1.1.1.jar ClickatellRest.java ClickatellHttp.java Runner.java
```

Run:

```
java -cp .:json-simple-1.1.1.jar Runner
```

Clickatell Java Lib - Platform branch
=============================

This SDK is based on the new clickatell platform.
Instead of checking out this code base you can download the clickatell-java-sdk.jar file from https://github.com/clickatell/clickatell-java/tree/platform/bin/artifacts/clickatell_java_sdk_jar.

Testing
-------
Testing can be done by editing the classes TestClickatellHttp and TestClickatellRest. You will need to supply your apiKey along with the number you would like to send the message.
