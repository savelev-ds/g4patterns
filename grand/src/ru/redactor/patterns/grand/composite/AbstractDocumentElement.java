package ru.redactor.patterns.grand.composite;

import java.awt.Font;

/**
 * Общая логика по управлению шрифтами  и родительским объектом
 */
public abstract class AbstractDocumentElement implements DocumentElementIF {

    /**
     * Шрифт, связанный с объектом. Если эта переменная null, то шрифт
     * наследуется от предка
     */
    private Font font;

    /**
     * Контейнер этого объекта
     */
    private CompositeDocumentElement parent;

    @Override
    public CompositeDocumentElement getParent() {
        return parent;
    }

    protected void setParent(CompositeDocumentElement parent) {
        this.parent = parent;
    }

    /**
     * Возвращает Font, связанный с данным объектом.
     * Если нет шрифта, связанного с данным объектом, то возвращает шрифт,
     * связанный с родителем этого объекта.
     * Если нет шрифта, связанного с родителем данного объекта, то возвращает null
     * @return
     */
    public Font getFont() {
        if (font != null) {
            return font;
        }
        if (parent != null) {
            return parent.getFont();
        }
        return null;
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public abstract int getCharLength();

}
