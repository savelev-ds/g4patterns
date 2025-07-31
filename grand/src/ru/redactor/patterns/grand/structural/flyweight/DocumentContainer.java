package ru.redactor.patterns.grand.structural.flyweight;

import java.awt.Font;
import java.util.Vector;

import javax.swing.text.Document;

/**
 * Определяет некоторые методы, наследуемые всеми контейнерными классами, применяемыми
 * для представления документа
 */
public abstract class DocumentContainer extends DocumentElement {

    private Vector children = new Vector();
    private Font font;
    DocumentContainer parent;

    public DocumentElement getChild(int index) {
        return (DocumentElement) children.get(index);
    }

    public synchronized void addChild(DocumentElement child) {
        synchronized (child) {
            children.addElement(child);
            if (child instanceof DocumentContainer) {
                ((DocumentContainer) child).parent = this;
            }
        }
    }

    public synchronized void removeChild(DocumentElement child) {
        synchronized (child) {
            if (child instanceof DocumentContainer && this == ((DocumentContainer)child).parent) {
                ((DocumentContainer)child).parent = null;
            }
            children.removeElement(child);
        }
    }

    public Font getFont() {
        if (font != null) {
            return font;
        }
        if (parent != null) {
            return parent.getFont();
        }
        return null;
    }

    public DocumentContainer getParent() {
        return parent;
    }

    public void setFont(Font font) {
        this.font = font;
    }

}
