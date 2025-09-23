package ru.redactor.patterns.grand.behavioral.command;

public class UndoCommand extends AbstractCommand implements Undo {

    /**
     * Эта реализация метода doIt на самом деле ничего не делает.
     * Логика для отмены находится в классе CommandManager
     * @return
     */
    public boolean doIt() {
        throw new NoSuchMethodError();
    }

    public boolean undoIt() {
        throw new NoSuchMethodError();
    }

}
