package pl.michal_baniowski.coutmywall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "composites")
public class Composite {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private CompositeType compositeType;
    @Column(nullable = false)
    private String name;
    private String description;
    @ManyToMany
    private List<CompositeMaterial> compositeMaterials;

    private Double compositeHeatTransferCoefficient;
    private Double compositeDiffusionResistance;
}
