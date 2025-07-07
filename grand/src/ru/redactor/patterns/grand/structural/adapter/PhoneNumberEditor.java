package ru.redactor.patterns.grand.structural.adapter;

public class PhoneNumberEditor {

    public PhoneNumberEditor(PhoneNumberIF phoneNumberIF) {
        phoneNumberIF.getPhoneNumber();
        phoneNumberIF.setPhoneNumber("123");
    }

}
