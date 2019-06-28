package pl.michal_baniowski.coutmywall.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.michal_baniowski.coutmywall.entity.Composite;
import pl.michal_baniowski.coutmywall.entity.CompositeMaterial;
import pl.michal_baniowski.coutmywall.entity.CompositeType;
import pl.michal_baniowski.coutmywall.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public class CompositeRepositoryApi {
    @Autowired
    private CompositeRepository repository;

    public List<Composite> findAll(){
        return repository.findAllByAuthorNull();
    }
    public List<Composite> findAllByCompositeType(CompositeType compositeType) {
        return repository.findAllByCompositeTypeAndAuthorNull(compositeType);
    }
    public List<Composite> findAllByName(String name) {
        return repository.findAllByNameLikeIgnoreCaseAndAuthorNull(name);
    }
    public List<Composite> findAllOfUsers(User user) {
        return repository.findAllByAuthorNullOrAuthor(user);
    }
    public List<Composite> findAllByCompositeType(CompositeType compositeType, User user) {
        return repository.findAllByCompositeTypeAndAuthorNullOrCompositeTypeAndAuthor(compositeType, compositeType, user);
    }
    public List<Composite> findAllByName(String name, User user) {
        return repository.findAllByNameLike(name, user);
    }
    public void deleteByCompositeMaterials(List<CompositeMaterial> compositeMaterials){
        repository.deleteByCompositeMaterialsIn(compositeMaterials);
    }
    public Optional<Composite> findById(Long compositeId) {
        return repository.findById(compositeId);
    }
    public Composite saveComposite(Composite composite) {
        return repository.save(composite);
    }
    public void deleteComposite(Long compositeId) {
        repository.deleteById(compositeId);
    }
    public boolean isPresent(Long compositeId){
        return repository.existsById(compositeId);
    }
}
