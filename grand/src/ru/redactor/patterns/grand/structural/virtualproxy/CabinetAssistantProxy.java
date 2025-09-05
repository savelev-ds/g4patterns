package ru.redactor.patterns.grand.structural.virtualproxy;

import java.lang.reflect.Constructor;

public class CabinetAssistantProxy implements CabinetAssistantIF {

    private CabinetAssistantIF assistant = null;

    // Для конструктора объекта консультанта
    private String myParam;

    public CabinetAssistantProxy(String s) {
         myParam = s;
    }

    /**
     *  Получаем объект CabinetAssistant, который используется
     *  для реализации операций. Если он еще не существует, этот
     *  метод создаёт его.
     */
    private CabinetAssistantIF getCabinetAssistant() {
        if (assistant == null) {
            try {
                // Получаем объект класса, который представляет класс Assistant
                Class clazz = Class.forName("CabinetAssistant");

                // Получаем объект конструктора для доступа к конструктору класса CabinetAssistant,
                // принимающему единственный аргумент
                Constructor constructor;

                // Получаем объект конструктора для создания объекта CabinetAssistant
                Class[] formalArgs = new Class[]{ String.class };
                constructor = clazz.getConstructor(formalArgs);

                // Используем объект конструктора
                Object actuals = new Object[] { myParam };
                assistant = (CabinetAssistantIF) constructor.newInstance(actuals);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (assistant == null) {
                throw new RuntimeException();
            }
        }
        return null;
    }

    @Override
    public void op1() {

    }

    @Override
    public void op2() {

    }

}
