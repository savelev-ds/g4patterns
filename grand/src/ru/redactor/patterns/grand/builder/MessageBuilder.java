package ru.redactor.patterns.grand.builder;

public abstract class MessageBuilder {

    static MessageBuilder getInstance(String dest) {
        MessageBuilder builder = null;
        //...
        return builder;
    }

    abstract void plainText(String text);
    abstract void jpegImage(String text);
    abstract void to(String text);
    abstract void from(String text);

    abstract OutboundMessageIF getOutboundMsg();

}
