package ru.redactor.patterns.grand.behavioral.visitor;

import java.util.Hashtable;

import ru.redactor.patterns.grand.splitting.composite.DocumentElementIF;

import javax.sound.sampled.Line;

public class TOCVisitor extends DocumentVisitor {

    private Hashtable tocStyles = new Hashtable();

    TOCVisitor(Document document) {
        super(document);
        TocLevel[] levels = document.getTocLevels();
        // Помещаем стили в хэш-таблицу.
        for (int i = 0; i < levels.length; i++) {
            tocStyles.put(levels[i].getStyle(), levels[i]);
        }
    }

    TOC buildTOC() {
        TOC toc = new TOC();
        Paragraph p;
        while ((p = getNextParagraph()) != null) {
            String styleName = p.getStyle();
            if (styleName != null) {
                TocLevel level;
                level = (TocLevel) tocStyles.get(styleName);
                if (level != null) {
                    LineOfText firstLine = null;
                    for (int i = 0; i < p.getChildCount(); i++) {
                        DocumentElementIF e = p.getChild(i);
                        if (e instanceof LineText) {
                            firstLine = (LineOfText) e;
                            break;
                        }
                    }
                }
            }
        }
        return toc;
    }

}
