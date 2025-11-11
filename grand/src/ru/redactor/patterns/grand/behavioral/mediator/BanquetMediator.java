package ru.redactor.patterns.grand.behavioral.mediator;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

public class BanquetMediator {

    private static final int PEOPLE_COUNT_DEFAULT = 100;
    private static final int MIN_PEOPLE = 1;
    private static final int MAX_PEOPLE = 200;
    private JButton okButton;
    private JTextComponent dateField;
    private JTextComponent startField;
    private JTextComponent endField;
    private JButton buffetButton;
    private JButton tableServiceButton;
    private JTextComponent peopleCountField;
    private JList foodList;

    private ItemAdapter itemAdapter = new ItemAdapter();

    private boolean busy = false;

    BanquetMediator() {
        WindowAdapter windowAdapter = new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                initialState();
            }
        };
        BanquetReservationDialog enclosingDialog = new BanquetReservationDialog();
        enclosingDialog.addWindowListener(windowAdapter);
    }

    private class ItemAdapter implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            enforceInvariants();
        }
    }

    public void registerOkButton(JButton ok) {
        okButton = ok;
    }

    public void registerPeopleCountField(JTextComponent field) {
        peopleCountField = field;
        DocumentAdapter docAdapter = new DocumentAdapter() {
            protected void parseDocument() {
                int count = PEOPLE_COUNT_DEFAULT;
                try {
                    String countText = field.getText();
                    count = Integer.parseInt(countText);
                } catch (NumberFormatException e) {}
                if (MIN_PEOPLE <= count && count <= MAX_PEOPLE) {
                    peopleCount = count;
                } else {
                    peopleCount = PEOPLE_COUNT_DEFAULT;
                }
            }
        };
        Document doc = field.getDocument();
        doc.addDocumentListener(docAdapter);
    }

    private void enforceInvariants() {
        if (busy) return;
        busy = true;
        protectedEnforceInvariants();
        busy = false;
    }

    private void protectedEnforceInvariants() {
        boolean enable = (peopleCount != PEOPLE_COUNT_DEFAULT);

        dateField.setEnabled(enable);
        startField.setEnabled(enable);
        endField.setEnabled(enable);
        buffetButton.setEnabled(enable);
        tableServiceButton.setEnabled(enable);

        if (enable) {
            enable = (buffetButton.isSelected() || tableServiceButton.isSelected());
            foodList.setEnabled(enable && endAtLeastOneHourAfterStart());
        } else {
            foodList.setEnabled(false);
            buffetButton.setSelected(false);
            tableServiceButton.setSelected(false);
        }
        okButton.setEnabled(foodList.isEnabled() && foodList.getMinSelectionIndex() > -1);
    }

    private boolean endAtLeastOneHourAfterStart() {
        Calendar startCalendar = getStartCalendar();
        if (startCalendar == null) return false;
        Calendar endCalendar = getEndCalendar();
        if (endCalendar == null) return false;
        startCalendar.add(Calendar.MINUTE, 59);
        return getEndCalendar().after(startCalendar);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private int peopleCount;
    private void initialState() {}
    private Calendar getStartCalendar() { return Calendar.getInstance(); }
    private Calendar getEndCalendar() { return Calendar.getInstance(); }
    private static class DocumentAdapter implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {

        }

        @Override
        public void removeUpdate(DocumentEvent e) {

        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }

}
