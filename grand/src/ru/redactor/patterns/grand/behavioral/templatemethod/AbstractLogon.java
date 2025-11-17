package ru.redactor.patterns.grand.behavioral.templatemethod;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public abstract class AbstractLogon {

    /**
     * Этот метод аутентифицирует пользователя
     * @param frame - Родительское окно диалогов, которые выводит на экран этот метод
     * @param programName - Имя программы
     */
    public void logon(Frame frame, String programName) {
        Object authenticationToken;
        LogonDialog logonDialog;
        logonDialog = new LogonDialog(frame, "Log on to " + programName);
        JDialog waitDialog = createWaitDialog(frame);

        while (true) {
            waitDialog.setVisible(false);
            logonDialog.setVisible(true);
            waitDialog.setVisible(true);
            try {
                String userID = logonDialog.getUserID();
                String password = logonDialog.getPassword();
                authenticationToken = authenticationToken(userID, password);
                break;
            } catch (Exception e) {
                // Сообщает пользователю, что аутентификация закончилась неудачей
                JOptionPane.showConfirmDialog(frame, e.getMessage(), "Auth Failure", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Аутентификация прошла успешно
        waitDialog.setVisible(false);
        logonDialog.setVisible(false);
        notifyAuthentication(authenticationToken);
    }

    private JDialog createWaitDialog(Frame frame) {
        return null;
    }

    protected abstract Object authenticationToken(String userID, String password);

    /**
     * Аутентфицирует пользователя, исходя из предоставленных ID пользователя и пароля.
     * @param userID Переданное имя пользователя.
     * @param password Переданный пароль.
     * @return Возвращает объект, инкапсулирующий доказательство аутентификации
     * @throws Exception
     */
    abstract protected Object authenticate(String userID, String password) throws Exception;

    /**
     * Извещает остальную часть программы о том, что пользвоатель аутентифицирован
     * @param authenticationToken
     */
    abstract protected void notifyAuthentication(Object authenticationToken);

}
