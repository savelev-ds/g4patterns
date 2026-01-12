package ru.redactor.patterns.grand.behavioral.visitor;

public class ReorgVisitor extends DocumentVisitor {

    private TocLevel[] levels;

    ReorgVisitor(Document document, int level) {
        super(document);
        this.levels = document.getTocLevels();
        Paragraph p;
        while ((p = getNextParagraph()) != null) {
            // ...
        }
    }

}
