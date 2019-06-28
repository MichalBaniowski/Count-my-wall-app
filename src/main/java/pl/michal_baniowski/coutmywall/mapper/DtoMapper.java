package pl.michal_baniowski.coutmywall.mapper;

public interface DtoMapper<E,D> {
    E mapToEntity(D dtoObject);
    D mapToDto(E entityObject);
}
