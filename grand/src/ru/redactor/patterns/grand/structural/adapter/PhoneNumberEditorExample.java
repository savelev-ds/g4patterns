package ru.redactor.patterns.grand.structural.adapter;

public class PhoneNumberEditorExample {

    private Person person;

    public PhoneNumberEditorExample(Person person) {
        this.person = person;
    }

    public void example() {

        PhoneNumberEditor voiceNumber = new PhoneNumberEditor(new PhoneNumberIF() {

            @Override
            public String getPhoneNumber() {
                return person.getOfficeNumber();
            }

            @Override
            public void setPhoneNumber(String newValue) {
                person.setOfficeNumber(newValue);
            }

        });

        PhoneNumberEditor faxNumber = new PhoneNumberEditor(new PhoneNumberIF() {
            @Override
            public String getPhoneNumber() {
                return person.getFaxNumber();
            }

            @Override
            public void setPhoneNumber(String newValue) {
                person.setFaxNumber(newValue);
            }
        });

    }

}
