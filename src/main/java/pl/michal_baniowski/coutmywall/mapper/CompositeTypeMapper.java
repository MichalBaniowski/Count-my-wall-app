package pl.michal_baniowski.coutmywall.mapper;

import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.dto.CompositeTypeDto;
import pl.michal_baniowski.coutmywall.entity.CompositeType;

@Service
public class CompositeTypeMapper implements DtoMapper<CompositeType, CompositeTypeDto>{

    @Override
    public CompositeType mapToEntity(CompositeTypeDto dtoObject) {
        CompositeType compositeType = new CompositeType();
        compositeType.setId(dtoObject.getId());
        compositeType.setName(dtoObject.getName());
        compositeType.setDescription(dtoObject.getDescription());
        return compositeType;
    }

    @Override
    public CompositeTypeDto mapToDto(CompositeType entityObject) {
        CompositeTypeDto compositeTypeDto = new CompositeTypeDto();
        compositeTypeDto.setId(entityObject.getId());
        compositeTypeDto.setName(entityObject.getName());
        compositeTypeDto.setDescription(entityObject.getDescription());
        return compositeTypeDto;
    }
}
