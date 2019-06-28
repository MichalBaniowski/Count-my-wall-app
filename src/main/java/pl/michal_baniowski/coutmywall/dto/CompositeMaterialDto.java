package pl.michal_baniowski.coutmywall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompositeMaterialDto {
    private Long id;
    private BuildingMaterialDto buildingMaterial;
    private Double thickness;
    private Double materialHeatResistance;
    private Double materialDiffusionResistance;
}
