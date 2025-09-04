package ru.redactor.patterns.grand.structural.virtualproxy;

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
            }
        }
    }

}
