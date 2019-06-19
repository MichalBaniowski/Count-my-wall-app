package pl.michal_baniowski.coutmywall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "composite_materials")
public class CompositeMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private BuildingMaterial buildingMaterial;

    private Double materialHeatTransferCoefficient;
    private Double materialDiffusionResistance;
    private Integer thickness;
}
