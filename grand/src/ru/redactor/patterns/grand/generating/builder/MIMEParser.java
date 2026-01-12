package ru.redactor.patterns.grand.generating.builder;

public class MIMEParser {

    private MIMEMessage msg;
    private MessageBuilder builder;

    OutboundMessageIF parse() {

        builder = MessageBuilder.getInstance(getDestination());

        MessagePart hdr = nextHeader();
        while (hdr != null) {
            if (hdr.getName().equals("to")) {
                builder.to(hdr.getValue());
            } else if (hdr.getName().equals("from")) {
                builder.from(hdr.getValue());
            }
            //...
            hdr = nextHeader();
        }

        MessagePart bdy = nextBodyPart();
        while (bdy != null) {
            String name = bdy.getName();
            if (name.equals("text/plain")) {
                builder.plainText(bdy.getValue());
            } else if (name.equals("image/jpeg")) {
                builder.jpegImage(bdy.getValue());
            }
            //...
            bdy = nextHeader();
        }

        return builder.getOutboundMsg();

    }

    private MessagePart nextHeader() {
        return new MessagePart();
    }

    private MessagePart nextBodyPart() {
        return new MessagePart();
    }

    private String getDestination() {
        return "";
    }

    private class MessagePart {

        String getValue() {
            return "";
        }

        String getName() {
            return "";
        }

    }
}
