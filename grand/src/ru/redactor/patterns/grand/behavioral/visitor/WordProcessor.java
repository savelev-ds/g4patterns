package ru.redactor.patterns.grand.behavioral.visitor;

public class WordProcessor {

    // Редактируемый в данный момент документ
    private Document activeDocument;

    /**
     * Реорганизуем документ, представляя его в виде нескольких файлов
     * @param level
     */
    private void reorg(int level) {
        new ReorgVisitor(activeDocument, level);
    }

    /**
     * Строим оглавление
     */
    private TOC buildTOC() {
        return new TOCVisitor(activeDocument).buildTOC();
    }

}
