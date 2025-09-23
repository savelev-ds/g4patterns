package ru.redactor.patterns.grand.behavioral.command;

import java.util.LinkedHashSet;
import java.util.LinkedList;

public class CommandManager {

    // Максимальное количество команд, сохраняющихся в истории.
    private int maxHistoryLength = 100;
    private LinkedList history = new LinkedList();
    private LinkedList redoList = new LinkedList();

    /**
     * Вызывает команду и добавляет её в историю.
     */
    public void invokeCommand(AbstractCommand command) {
        if (command instanceof Undo) {
            undo();
            return;
        }
        if (command instanceof Redo) {
            redo();
            return;
        }
        if (command.doIt()) {
            // Метод doIt возвращает true, что означает возможность отмены команды
            addToHistory(command);
        } else {
            // Команда не может быть отменена, поэтому история команд очищается
            history.clear();
        }
        // После выполнения команды (кроме команд отмены или повтора), убеждаемся, что список команд повтора пуст.
        if (redoList.size() > 0) {
            redoList.clear();
        }
    }

    private void undo() {
        if (history.size() > 0) {
            AbstractCommand undoCmd = (AbstractCommand) history.removeFirst();
            undoCmd.undoIt();
            redoList.addFirst(undoCmd);
        }
    }

    private void redo() {
        if (redoList.size() > 0) {
            AbstractCommand redoCmd = (AbstractCommand) redoList.removeFirst();
            redoCmd.doIt();
            history.addFirst(redoCmd);
        }
    }

    /**
     * Добавляет команду в историю команд.
     */
    private void addToHistory(AbstractCommand command) {
        history.addFirst(command);
        // Если размер истории превысил значение maxHistoryLength,
        // удаляет из истории самую старую команду.
        if (history.size() > maxHistoryLength) {
            history.removeLast();
        }
    }

}
