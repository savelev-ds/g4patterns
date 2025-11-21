package ru.redactor.patterns.grand.behavioral.concurrency.lockobject;

import java.util.ArrayList;
import java.util.List;

public class GameCharacter extends AbstractGameObject {

    private List<Weapon> myWeapons = new ArrayList<>();

    public void dropAllWeapons() {
        synchronized (getLockObject()) {
            for (int i = myWeapons.size() - 1; i >= 0; i--) {
                myWeapons.get(i).setGlowing(true);
            }
        }
    }

}

