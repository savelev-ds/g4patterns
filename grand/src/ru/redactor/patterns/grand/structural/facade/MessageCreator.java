package ru.redactor.patterns.grand.structural.facade;

import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MessageCreator {

    public static final int MIME = 1;
    public static final int MAPI = 2;

    private Map headerFields = new HashMap();
    private RichText messageBody;
    private Vector attachments = new Vector();
    private boolean signMessage;

    public MessageCreator(String to, String from, String subject) {
        this(to, from, subject, inferMessageType(to));
    }

    public MessageCreator(String to, String from, String subject, int type) {
        headerFields.put("to", to);
        headerFields.put("from", from);
        headerFields.put("subject", subject);
        // ...
    }

    public void setMessageBody(String messageBody) {
        setMessageBody(new RichText(messageBody));
    }

    public void setMessageBody(RichText richText) {
        this.messageBody = richText;
    }

    public void addAttachment(Object attachment) {
        attachments.add(attachment);
    }

    public void setSignMessage(boolean signFlag) {
        signMessage = signFlag;
    }

    public void setHeaderField(String name, String value) {
        headerFields.put(name.toLowerCase(), value);
    }

    public void send() {}

    private static int inferMessageType(String address) {
        int type = 0;
        //...
        return type;
    }

    private Security createSecurity() {
        Security s = null;
        // ...
        return s;
    }

    private void createMessageSender(Message msg) {
        // ...
    }
}
