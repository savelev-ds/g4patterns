package ru.redactor.patterns.grand.behavioral.state;

/**
 * Класс представляет такое состояние, когда содержимое полей диалога не совпадает с содержимым файла
 * или значениями рабочих параметров.
 */
public class BothDirty extends DirtyState {

    /**
     * Отвечает на данное событие
     * @param event
     * @return Следующее состояние
     */
    public DirtyState nextState(int event) {
        switch (event) {
            case DIRTY_EVENT -> {
                return this;
            }
            case APPLY_EVENT -> {
                if (parameters.applyParam()) {
                    fileDirty.enter();
                    return fileDirty;
                }
            }
            case SAVE_EVENT -> {
                if (parameters.saveParam()) {
                    paramDirty.enter();
                    return paramDirty;
                }
            }
            case REVERT_EVENT -> {
                if (parameters.revertParam()) {
                    paramDirty.enter();
                    return paramDirty;
                }
            }
        }
        String msg = "Unexpected event " + event;
        throw new IllegalArgumentException(msg);
    }

    @Override
    protected void enter() {
        apply.setEnabled(true);
        revert.setEnabled(true);
        save.setEnabled(true);
    }
}
