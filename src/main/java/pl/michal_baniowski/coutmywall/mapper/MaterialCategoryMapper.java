package pl.michal_baniowski.coutmywall.mapper;

import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.dto.MaterialCategoryDto;
import pl.michal_baniowski.coutmywall.entity.MaterialCategory;

@Service
public class MaterialCategoryMapper implements DtoMapper<MaterialCategory, MaterialCategoryDto> {

    @Override
    public MaterialCategory mapToEntity(MaterialCategoryDto dtoObject) {
        MaterialCategory category = new MaterialCategory();
        category.setId(dtoObject.getId());
        category.setName(dtoObject.getName());
        category.setDescription(dtoObject.getDescription());
        return category;
    }

    @Override
    public MaterialCategoryDto mapToDto(MaterialCategory entityObject) {
        MaterialCategoryDto categoryDto = new MaterialCategoryDto();
        categoryDto.setId(entityObject.getId());
        categoryDto.setName(entityObject.getName());
        categoryDto.setDescription(entityObject.getDescription());
        return categoryDto;
    }
}
