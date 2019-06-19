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
@Table(name = "building_materials")
public class BuildingMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double thermalConductivity;

    @Column(nullable = false)
    private Double steamTransferCoefficient;

    @ManyToMany
    private List<MaterialCategory> categories = new ArrayList<>();
}
