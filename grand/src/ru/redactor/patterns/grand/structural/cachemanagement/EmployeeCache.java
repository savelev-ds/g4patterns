package ru.redactor.patterns.grand.structural.cachemanagement;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import static ru.redactor.patterns.grand.structural.cachemanagement.EmployeeProfile.EmployeeID;

public class EmployeeCache {

    /**
     * Для определения наименее использовавшегося за последнее время профиля служащего применяется
     * связный список. Сам по себе кэш реализуется при помощи объекта Hashtable. Значения Hashtable
     * представляют собой объекты связного списка, которые ссылаются на реальный объект EmployeeProfile
     */
    private HashMap<EmployeeID, LinkedList> cache = new HashMap<>();

    /**
     * Это головной узел связного списка, который ссылается на наиболее использовавшийся в последнее
     * время объект EmployeeProfile
     */
    LinkedList mostRecentUsed = null;

    /**
     * Это последний узел связного списка, который ссылается на наименее использовавшийся за последнее
     * время объект EmployeeProfile.
     */
    LinkedList leastRecentUsed = null;

    /**
     * Максимальное количество объектов EmployeeProfile, которое может находиться в кэше.
     */
    private final int MAX_CACHE_SIZE = 80;

    /**
     * Количество объектов EmployeeProfile, находящихся в данный момент в кэше.
     */
    private int currentCacheSize = 0;

    /**
     * Этот объект управляет очисткой после того, как программа сборки мусора решает, что пришло время освободить
     * память, занимаемую объектом EmployeeProfile
     */
    private CleanupQueue myCleanup = new CleanupQueue();

    /**
     * Этому методу передаются объекты, предназначенные для добавления в кэш. Однако этот метод на самом деле может
     * не добавить объект в кэш, если это противоречит его политике добавления объектов. Этот метод может также удалять
     * находящиеся в кэше объекты, чтобы освободить место для новых.
     */
    public void addEmployee(EmployeeProfile emp) {
        EmployeeID id = emp.getID();
        if (cache.get(id) == null) { // если нет в кэше
            // добавляем профиль в кэш,
            // делая его наиболее используемым за последнее время.
            if (currentCacheSize == 0) {
                // рассматриваем пустой кэш, как особый случай.
                leastRecentUsed = mostRecentUsed = new LinkedList(emp);
            } else {
                LinkedList newLink;
                if (currentCacheSize >= MAX_CACHE_SIZE) {
                    // Удаляем наименее использовавщшийся за последнее время объект EmployeeProfile из кэша
                    newLink = leastRecentUsed;
                    leastRecentUsed = leastRecentUsed.previous;
                    cache.remove(id);
                    currentCacheSize--;
                    leastRecentUsed.next = null;
                    newLink.setProfile(emp);
                } else {
                    newLink = new LinkedList(emp);
                }
                newLink.next = mostRecentUsed;
                mostRecentUsed.previous = newLink;
                newLink.previous = null;
                mostRecentUsed = newLink;
            }
            //  Помещаем профиль служащего, наиболее часто использовавшийся в последнее время, в кэш
            cache.put(id, mostRecentUsed);
            currentCacheSize++;
        } else { // Профиль уже в кэше
            // Метод addEmployee не должен вызываться, если объект
            // уже находится в кэше. Если это случиться, то производится выборка, чтобы объект стал наиболее
            // используемым за последнее время
            fetchEmployee(id);
        }
    }

    /**
     * Возвращает объект EmployeeProfile, связанный с данным EmployeeID, или null,
     * если не найден такой EmployeeProfile
     */
    EmployeeProfile fetchEmployee(EmployeeID id) {
        // Удаляем из кэша любой EmployeeID, соответствуюший объект EmployeeProfile которого был удалён при сборке
        // мусора
        myCleanup.cleanup();

        LinkedList foundLink = cache.get(id);
        if (foundLink == null) {
            return null;
        }
        if (mostRecentUsed != foundLink) {
            if (foundLink == leastRecentUsed) {
                leastRecentUsed = foundLink.previous;
                leastRecentUsed.next = null;
            }
            if (foundLink.previous != null) {
                foundLink.previous.next = foundLink.next;
            }
            if (foundLink.next != null) {
                foundLink.next.previous = foundLink.previous;
            }
            mostRecentUsed.previous = foundLink;
            foundLink.previous = null;
            foundLink.next = mostRecentUsed;
            mostRecentUsed = foundLink;
        }
        return foundLink.getProfile();
    }

    /**
     * Удаляет объект EmployeeProfile, связанный с данным EmployeeID, находящимся в кэше
     */
    void removeEmployee(EmployeeID id) {
        LinkedList foundLink = cache.get(id);
        if (foundLink != null) {
            if (mostRecentUsed == foundLink) {
                mostRecentUsed = foundLink.next;
            }
            if (foundLink == leastRecentUsed) {
                leastRecentUsed = foundLink.previous;
            }
            if (foundLink.previous != null) {
                foundLink.previous.next = foundLink.next;
            }
        }
        if (foundLink.next != null) {
            foundLink.next.previous = foundLink.previous;
        }
    }

    /**
     * Закрытый класс двусвязного списка для управления списком наиболее использовавшихся за последнее
     * время профилей служаших. Этот класс реализует интерфейс CleanupIF, поэтому его экземпляры могут
     * быть извещены после того, как программа сборки мусора решит удалить объект EmployeeProfile.
     */
    private class LinkedList implements CleanupIF {
        SoftReference profileReference;
        LinkedList previous;
        LinkedList next;

        LinkedList(EmployeeProfile profile) {
            setProfile(profile);
        }

        void setProfile(EmployeeProfile profile) {
            profileReference = myCleanup.createSoftReference(profile, this);
        }

        EmployeeProfile getProfile() {
            return (EmployeeProfile) profileReference.get();
        }

        /**
         * При вызове этого метода предполагается, что объект, который его реализует, удаляет
         * сам себя из любой структуры данных, частью которой он является
         */
        public void extricate() {
            EmployeeProfile profile;
            profile = (EmployeeProfile) profileReference.get();
            removeEmployee(profile.getID());
        }
    }

}












