package ru.redactor.patterns.grand.composite;

import java.awt.Font;

/**
 * Должен реализовываться ВСЕМИ классами, образующими документ
 */
public interface DocumentElementIF {

    /**
     * Возвращает родителя этого объекта или null, если родитель отсутствует
     */
    public CompositeDocumentElement getParent();

    /**
     * Возвращает шрифт, связанный с элементом
     * @return
     */
    public Font getFont();

    /**
     * Связывает шрифт с этим объектом
     * @param font
     */
    public void setFont(Font font);

    /**
     * Возвращает количество символов, содержащихся в этом объекте
     * @return
     */
    public int getCharLength();

}
