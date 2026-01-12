package ru.redactor.patterns.grand.behavioral.visitor;

import ru.redactor.patterns.grand.splitting.composite.DocumentElementIF;

public abstract class DocumentVisitor {

    private Document document;
    private int docIndex = 0; // Индекс, используемый для навигации потомков документа.

    DocumentVisitor(Document document) {
        this.document = document;
    }

    protected Document getDocument() {
        return document;
    }

    /**
     * Возвращает следующий абзац, который является непосредственной частью документа
     */
    protected Paragraph getNextParagraph() {
        Document myDocument = document;
        while (docIndex < myDocument.getChildCount()) {
            DocumentElementIF docElement;
            docElement = myDocument.getChild(docIndex);
            docIndex++;
            if (docElement instanceof Paragraph) {
                return (Paragraph) docElement;
            }
        }
        return null;
    }

}
