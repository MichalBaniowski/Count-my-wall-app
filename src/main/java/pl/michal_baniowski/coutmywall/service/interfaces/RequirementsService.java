package pl.michal_baniowski.coutmywall.service.interfaces;

public interface RequirementsService<T> {
    void checkRequirements(T elementToCheck);
}
