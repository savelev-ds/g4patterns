package ru.redactor.patterns.grand.splitting.composite;

import java.util.Vector;

/**
 * Абстрактный суперкласс для всех элементов документа, содержащих другие элементы документа
 */
public abstract class CompositeDocumentElement extends AbstractDocumentElement {

    private Vector<AbstractDocumentElement> children = new Vector<>();

    /**
     * Значение, помещённое в кэш, после предыдущего вызова метода getCharLength или -1,
     * если charLength не содержит помещённого в кэш значения
     */
    private int cachedCharLength = -1;

    public DocumentElementIF getChild(int index) {
        return children.elementAt(index);
    }

    /**
     * Синхронизированный снаружи и внутри, так как меняет и контейнер, и потомка
     * @param child
     */
    public synchronized void addChild(DocumentElementIF child) {
        synchronized (child) {
            children.add((AbstractDocumentElement) child);
            ((AbstractDocumentElement)child).setParent(this);
            changeNotification();
        }
    }

    /**
     * Синхронизированный снаружи и внутри, так как меняет и контейнер, и потомка
     * @param child
     */
    public synchronized void removeChild(AbstractDocumentElement child) {
        synchronized (child) {
            if (this == child.getParent()) {
                child.setParent(null);
            }
            children.removeElement(child);
            changeNotification();
        }
    }

    /**
     * Вызов этого метода означает, что один из потомков был изменён таким образом, что он
     * делает недействительными все данные о потомках, которые этот объект может помещать в кэш
     */
    public void changeNotification() {
        cachedCharLength = -1;
        if (getParent() != null) {
            getParent().changeNotification();
        }
    }

    public int getCharLength() {
        int len = 0;
        for (int i = 0; i < children.size(); i++) {
            AbstractDocumentElement thisChild;
            thisChild = (AbstractDocumentElement) children.elementAt(i);
            len += thisChild.getCharLength();
        }
        cachedCharLength = len;
        return len;
    }

}
