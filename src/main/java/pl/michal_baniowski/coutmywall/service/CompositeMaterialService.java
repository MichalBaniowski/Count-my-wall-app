package pl.michal_baniowski.coutmywall.service;

import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.dto.CompositeMaterialDto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class CompositeMaterialService {
    public void setMaterialHeatResistance(CompositeMaterialDto compositeMaterial) {
        Double materialHeatResistance = BigDecimal.valueOf(compositeMaterial.getThickness())
                .divide(BigDecimal.valueOf(compositeMaterial.getBuildingMaterial().getThermalConductivity()), MathContext.DECIMAL32)
                .setScale(4, RoundingMode.HALF_DOWN)
                .doubleValue();
        compositeMaterial.setMaterialHeatResistance(materialHeatResistance);
    }

    public void setMaterialDiffusionResistance(CompositeMaterialDto compositeMaterial) {
        Double materialDiffusionResistance = BigDecimal.valueOf(compositeMaterial.getThickness())
                .divide(BigDecimal.valueOf(compositeMaterial.getBuildingMaterial().getSteamTransferCoefficient()), MathContext.DECIMAL32)
                .setScale(4, RoundingMode.HALF_DOWN)
                .doubleValue();
        compositeMaterial.setMaterialDiffusionResistance(materialDiffusionResistance);
    }
}
