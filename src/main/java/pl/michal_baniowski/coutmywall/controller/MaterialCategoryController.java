package pl.michal_baniowski.coutmywall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.michal_baniowski.coutmywall.dto.MaterialCategoryDto;
import pl.michal_baniowski.coutmywall.service.MaterialCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/material-categories")
public class MaterialCategoryController {
    @Autowired
    private MaterialCategoryService categoryService;

    @GetMapping("")
    public List<MaterialCategoryDto> getAllMaterialCategories() {
        return categoryService.getAllMaterialCategoriesDto();
    }
    @GetMapping("/{categoryId}")
    public MaterialCategoryDto getMaterialCategoryById(@PathVariable Long categoryId) {
        return categoryService.getMaterialCategoryDtoById(categoryId);
    }
}
