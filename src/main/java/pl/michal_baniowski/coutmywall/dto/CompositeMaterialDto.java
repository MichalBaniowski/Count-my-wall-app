package pl.michal_baniowski.coutmywall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal_baniowski.coutmywall.entity.BuildingMaterial;

@Data
@NoArgsConstructor
public class CompositeMaterialDto {
    private Long id;
    private BuildingMaterial buildingMaterial;
    private Double materialHeatTransferCoefficient;
    private Double materialDiffusionResistance;
    private Integer thickness;
}
