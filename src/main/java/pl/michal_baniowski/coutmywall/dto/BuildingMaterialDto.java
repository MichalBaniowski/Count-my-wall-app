package pl.michal_baniowski.coutmywall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal_baniowski.coutmywall.entity.MaterialCategory;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BuildingMaterialDto {
    private Long id;
    private String name;
    private Double thermalConductivity;
    private Double steamTransferCoefficient;
    private List<MaterialCategory> categories = new ArrayList<>();
}
