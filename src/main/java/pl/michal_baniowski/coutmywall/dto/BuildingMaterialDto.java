package pl.michal_baniowski.coutmywall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BuildingMaterialDto {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    @Min(0)
    private Double thermalConductivity;
    @NotNull
    @Min(0)
    private Double steamTransferCoefficient;
    @NotEmpty
    private List<String> materialCategoryNames = new ArrayList<>();
    @NotNull
    private String authorName;
}
