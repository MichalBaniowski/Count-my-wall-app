package pl.michal_baniowski.coutmywall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "composites")
public class Composite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private CompositeType compositeType;
    @Column(nullable = false)
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<CompositeMaterial> compositeMaterials = new ArrayList<>();
    private Double compositeSumOfHeatResistance;
    private Double compositeHeatTransferCoefficient;
    private Double compositeDiffusionResistance;
}
