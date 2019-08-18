package pl.michal_baniowski.coutmywall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal_baniowski.coutmywall.entity.auth.User;

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
    private Double steamTransferCoefficient;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<MaterialCategory> categories = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
