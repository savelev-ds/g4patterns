package ru.redactor.patterns.grand.behavioral.command;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class InsertStringCommand extends AbstractCommand {

    private Document document;
    private int position;
    private String string;

    InsertStringCommand(Document document, int position, String string) {
        this.document = document;
        this.position = position;
        this.string = string;
        manager.invokeCommand(this);
    }

    /**
     * Выполняет команду, инкапуслированную в этом объекте.
     * @return Возвращает true, если этот вызов doCommand был успешным
     * и его можно отменять
     */
    @Override
    public boolean doIt() {
        try {
            document.insertString(position, string, null);
        } catch (BadLocationException e) {
            return false;
        }
        return true;
    }

    /**
     * Отменяет команду, инкапсулированную в этом объекте.
     * @return true, если отмена была успешной
     */
    @Override
    public boolean undoIt() {
        try {
            document.remove(position, string.length());
        } catch (BadLocationException e) {
            return false;
        }
        return true;
    }

}
