package pl.michal_baniowski.coutmywall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal_baniowski.coutmywall.validation.CompleteEntityValidationGroup;
import pl.michal_baniowski.coutmywall.validation.CompositeCalculateValidationGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class CompositeDto {
    private Long id;
    @NotNull(groups = {CompositeCalculateValidationGroup.class, CompleteEntityValidationGroup.class})
    private String compositeType;
    @NotBlank(groups = CompleteEntityValidationGroup.class)
    private String name;
    private String description;
    @NotEmpty(groups = {CompositeCalculateValidationGroup.class, CompleteEntityValidationGroup.class})
    private List<CompositeMaterialDto> compositeMaterials;
    @NotNull(groups = CompleteEntityValidationGroup.class)
    private String author;
    private Double compositeSumOfHeatResistance;
    private Double compositeHeatTransferCoefficient;
    private Double compositeDiffusionResistance;
}
