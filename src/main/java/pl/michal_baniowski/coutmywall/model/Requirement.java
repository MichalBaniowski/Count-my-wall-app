package pl.michal_baniowski.coutmywall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Requirement {
    private String propertyName;
    private Double value;
    private LocalDate obligatoryDate;

    public Requirement(String propertyName, Double value) {
        this.propertyName = propertyName;
        this.value = value;
    }
}
