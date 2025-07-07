package ru.redactor.patterns.grand.structural.adapter;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InnerClassAdapterExample {

    public InnerClassAdapterExample(String caption) {
        MenuItem exit = new MenuItem(caption);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
    }

    private void close() {}

}
