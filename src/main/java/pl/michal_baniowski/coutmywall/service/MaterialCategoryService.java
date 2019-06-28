package pl.michal_baniowski.coutmywall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.dto.MaterialCategoryDto;
import pl.michal_baniowski.coutmywall.entity.MaterialCategory;
import pl.michal_baniowski.coutmywall.mapper.MaterialCategoryMapper;
import pl.michal_baniowski.coutmywall.repository.MaterialCategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaterialCategoryService {
    private MaterialCategoryRepository categoryRepository;
    private MaterialCategoryMapper mapper;
    @Autowired
    public MaterialCategoryService(MaterialCategoryRepository categoryRepository, MaterialCategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }
    public List<MaterialCategoryDto> getAllMaterialCategoriesDto() {
        return categoryRepository.findAll().stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
    public MaterialCategoryDto getMaterialCategoryDtoById(Long userId) {
        Optional<MaterialCategory> categoryOptional = categoryRepository.findById(userId);
        if(categoryOptional.isPresent()) {
            return mapper.mapToDto(categoryOptional.get());
        }
        return null;
    }
}
