package pl.michal_baniowski.coutmywall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal_baniowski.coutmywall.entity.CompositeMaterial;
import pl.michal_baniowski.coutmywall.entity.CompositeType;

import java.util.List;

@Data
@NoArgsConstructor
public class CompositeDto {
    private Long id;
    private CompositeType compositeType;
    private String name;
    private String description;
    private List<CompositeMaterial> compositeMaterials;
    private Double compositeHeatTransferCoefficient;
    private Double compositeDiffusionResistance;
}
