package ru.redactor.patterns.grand.structural.cachemanagement;

import java.util.Locale;
import java.util.Objects;

public class EmployeeProfile {

    private EmployeeID id;
    private Locale locale;
    private boolean supervisor;
    private String name;

    public EmployeeProfile(EmployeeID id, Locale locale, boolean supervisor, String name) {
        this.id = id;
        this.locale = locale;
        this.supervisor = supervisor;
        this.name = name;
    }

    public EmployeeID getID() {
        return id;
    }

    public Locale getLocale() {
        return locale;
    }

    public boolean isSupervisor() {
        return supervisor;
    }

    public static class EmployeeID {

        private String id;

        public EmployeeID(String id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EmployeeID that = (EmployeeID) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

    }

}
