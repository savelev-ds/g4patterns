package ru.redactor.patterns.grand.behavioral.state;

import java.awt.Button;

public class DirtyState {

    public static final int DIRTY_EVENT = 1;
    public static final int APPLY_EVENT = 2;
    public static final int SAVE_EVENT = 3;
    public static final int REVERT_EVENT = 4;

    protected static BothDirty bothDirty;
    protected static FileDirty fileDirty;
    protected static ParamDirty paramDirty;
    protected static NotDirty notDirty;

    protected Parameters parameters;
    protected Button apply, save, revert;

    DirtyState() {
        if (bothDirty == null) {
            bothDirty = new BothDirty();
            fileDirty = new FileDirty();
            paramDirty = new ParamDirty();
            notDirty = new NotDirty();
        }
    }

    public static DirtyState start(
        Parameters p,
        Button apply,
        Button save,
        Button revert
    ) {
        DirtyState d = new DirtyState();
        d.parameters = p;
        d.apply = apply;
        d.save = save;
        d.revert = revert;
        d.notDirty.enter();
        return d.notDirty;
    }

    protected DirtyState nextState(int event) {
        throw new IllegalArgumentException();
    }

    /**
     * Отвечает на данное событие, определяя текущее состояние и переходя к нему, если оно оказывается
     * отличным от текущего.
     */
    public final DirtyState processEvent(int event) {
        DirtyState myNextState = nextState(event);
        if (this != myNextState) {
            myNextState.enter();
        }
        return myNextState;
    }

    /**
     * Если данный объект становится текущим состоянием, вызывается этот метод
     */
    protected void enter() {}

}
