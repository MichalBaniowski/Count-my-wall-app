package pl.michal_baniowski.coutmywall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class CompositeDto {
    private Long id;
    @NotNull
    private String compositeType;
    @NotBlank
    private String name;
    private String description;
    @NotEmpty
    private List<CompositeMaterialDto> compositeMaterials;
    @NotNull
    private String author;
    private Double compositeSumOfHeatResistance;
    private Double compositeHeatTransferCoefficient;
    private Double compositeDiffusionResistance;
}
