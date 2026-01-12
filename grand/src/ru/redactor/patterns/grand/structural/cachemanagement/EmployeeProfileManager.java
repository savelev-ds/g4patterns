package ru.redactor.patterns.grand.structural.cachemanagement;

public class EmployeeProfileManager {

    private EmployeeCache cache = new EmployeeCache();
    private EmployeeProfileFetcher server = new EmployeeProfileFetcher();

    /**
     * Из внутреннего кэша или сервера учета рабочего времени (если нет во внутреннем кэше) извлекаем
     * профиль служащего, соответствующий данному идентификационному номеру
     *
     * @return Возвратить профиль служащего или null, если параметры не найдены
     */

    EmployeeProfile fetchEmployee(EmployeeProfile.EmployeeID id) {
        EmployeeProfile profile = cache.fetchEmployee(id);
        if (profile == null) {
            profile = server.fetchEmployee(id);
            if (profile != null) {
                cache.addEmployee(profile);
            }
        }
        return profile;
    }

}
