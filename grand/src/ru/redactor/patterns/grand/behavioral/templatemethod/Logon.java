package ru.redactor.patterns.grand.behavioral.templatemethod;

public class Logon extends AbstractLogon {

    @Override
    protected Object authenticationToken(String userID, String password) {
        return null;
    }

    @Override
    protected Object authenticate(String userID, String password) throws Exception {
        if (userID.equals("admin") && password.equals("admin")) {
            return userID;
        }
        throw new Exception("Bad user id");
    }

    @Override
    protected void notifyAuthentication(Object authenticationToken) {

    }

}
