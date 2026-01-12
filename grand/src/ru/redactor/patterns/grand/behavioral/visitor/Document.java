package ru.redactor.patterns.grand.behavioral.visitor;

import ru.redactor.patterns.grand.splitting.composite.DocumentElementIF;

public class Document {

    public int getChildCount() {
        return 0;
    }

    public DocumentElementIF getChild(int docIndex) {
        return null;
    }

    public TocLevel[] getTocLevels() {
        return null;
    }

}
