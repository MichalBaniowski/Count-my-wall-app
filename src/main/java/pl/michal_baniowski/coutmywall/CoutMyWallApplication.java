package pl.michal_baniowski.coutmywall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.michal_baniowski.coutmywall.entity.BuildingMaterial;
import pl.michal_baniowski.coutmywall.repository.BuildingMaterialRepository;
import pl.michal_baniowski.coutmywall.repository.MaterialCategoryRepository;

import java.util.List;

@SpringBootApplication
public class CoutMyWallApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CoutMyWallApplication.class, args);
        BuildingMaterialRepository repositoryMat = run.getBean(BuildingMaterialRepository.class);
        MaterialCategoryRepository repoCat = run.getBean(MaterialCategoryRepository.class);
        List<BuildingMaterial> allByCategories = repositoryMat.findAllByCategories(repoCat.findById(1L).get());
        allByCategories.forEach(System.out::println);
    }

}
