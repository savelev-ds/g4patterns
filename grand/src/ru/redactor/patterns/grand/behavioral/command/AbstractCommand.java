package ru.redactor.patterns.grand.behavioral.command;

public abstract class AbstractCommand {

    public final static CommandManager manager = new CommandManager();

    /**
     * Выполняет команду, инкапсулированную в этом объекте.
     * @return Возвращает true при успешном выполнении и возможности отмены
     */
    public abstract boolean doIt();

    /**
     * Отменяет последний вызов метода doIt.
     * @return Возвращает true, если отмена прошла успешно.
     */
    public abstract boolean undoIt();

}
