package pl.michal_baniowski.coutmywall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.dto.CompositeDto;
import pl.michal_baniowski.coutmywall.dto.CompositeMaterialDto;
import pl.michal_baniowski.coutmywall.entity.Composite;
import pl.michal_baniowski.coutmywall.entity.CompositeType;
import pl.michal_baniowski.coutmywall.entity.User;
import pl.michal_baniowski.coutmywall.exception.AccessDeniedException;
import pl.michal_baniowski.coutmywall.exception.FailedRepositoryOperationException;
import pl.michal_baniowski.coutmywall.exception.NoEntityFound;
import pl.michal_baniowski.coutmywall.mapper.CompositeMapper;
import pl.michal_baniowski.coutmywall.repository.CompositeRepositoryApi;
import pl.michal_baniowski.coutmywall.repository.CompositeTypeRepository;
import pl.michal_baniowski.coutmywall.repository.UserRepository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompositeService {

    private CompositeMaterialService compositeMaterialService;
    private CompositeMapper compositeMapper;
    private CompositeRepositoryApi compositeRepository;
    private UserRepository userRepository;
    private CompositeTypeRepository compositeTypeRepository;
    @Autowired
    public CompositeService(CompositeMaterialService compositeMaterialService,
                            CompositeMapper compositeMapper,
                            CompositeRepositoryApi compositeRepository,
                            UserRepository userRepository,
                            CompositeTypeRepository compositeTypeRepository) {
        this.compositeMaterialService = compositeMaterialService;
        this.compositeMapper = compositeMapper;
        this.compositeRepository = compositeRepository;
        this.userRepository = userRepository;
        this.compositeTypeRepository = compositeTypeRepository;
    }
    public CompositeDto calculateProperties(CompositeDto compositeDto) {
        compositeDto.getCompositeMaterials().stream()
                .peek(compositeMaterialService::setMaterialHeatResistance)
                .forEach(compositeMaterialService::setMaterialDiffusionResistance);
        calculateSumDiffusionResist(compositeDto);
        calculateSumHeatResist(compositeDto);
        calculateHeatTransferCoefficient(compositeDto);
        return compositeDto;
    }
    private void calculateSumDiffusionResist(CompositeDto compositeDto) {
        double sum = compositeDto.getCompositeMaterials().stream()
                .mapToDouble(CompositeMaterialDto::getMaterialDiffusionResistance)
                .sum();
        compositeDto.setCompositeDiffusionResistance(BigDecimal.valueOf(sum).setScale(4, RoundingMode.HALF_DOWN).doubleValue());
    }
    private void calculateSumHeatResist(CompositeDto compositeDto) {
        double sum = compositeDto.getCompositeMaterials().stream()
                .mapToDouble(CompositeMaterialDto::getMaterialHeatResistance)
                .sum();
        compositeDto.setCompositeSumOfHeatResistance(BigDecimal.valueOf(sum)
                .add(BigDecimal.valueOf(0.04))
                .add(BigDecimal.valueOf(0.13))
                .setScale(4, RoundingMode.HALF_DOWN)
                .doubleValue());
    }
    private void calculateHeatTransferCoefficient(CompositeDto compositeDto) {
        double htc = BigDecimal.ONE
                .divide(BigDecimal.valueOf(compositeDto.getCompositeSumOfHeatResistance()), MathContext.DECIMAL32)
                .setScale(4, RoundingMode.HALF_DOWN)
                .doubleValue();
        compositeDto.setCompositeHeatTransferCoefficient(htc);
    }
    public List<CompositeDto> getAllDefaultComposites() {
        return compositeRepository.findAll().stream()
                .map(compositeMapper::mapToDto)
                .collect(Collectors.toList());
    }
    public List<CompositeDto> getDefaultCompositesByType(Long typeId) {
        Optional<CompositeType> compositeTypeOptional = compositeTypeRepository.findById(typeId);
        if(compositeTypeOptional.isPresent()) {
            return compositeRepository.findAllByCompositeType(compositeTypeOptional.get()).stream()
                    .map(compositeMapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public List<CompositeDto> getAllDefaultCompositesByName(String name) {
        return compositeRepository.findAllByName(name).stream()
                .map(compositeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CompositeDto> getAllCompositesByName(String name, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            return compositeRepository.findAllByName(name, userOptional.get()).stream()
                    .map(compositeMapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return getAllDefaultCompositesByName(name);
    }

    public List<CompositeDto> getAllDefaultAndUsersComposites(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            return compositeRepository.findAllOfUsers(userOptional.get()).stream()
                    .map(compositeMapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return getAllDefaultComposites();
    }

    public List<CompositeDto> getAllCompositesByType(Long typeId, Long userId) {
        Optional<CompositeType> compositeTypeOptional = compositeTypeRepository.findById(typeId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if(compositeTypeOptional.isPresent() && optionalUser.isPresent()) {
            return compositeRepository.findAllByCompositeType(compositeTypeOptional.get(), optionalUser.get()).stream()
                    .map(compositeMapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public CompositeDto getCompositeById(Long compositeId, Long userId) {
        Optional<Composite> optionalComposite = compositeRepository.findById(compositeId);
        if(!optionalComposite.isPresent() || optionalComposite.get().getAuthor().getId() != userId) {
            throw new AccessDeniedException();
        }
        return compositeMapper.mapToDto(optionalComposite.get());
    }

    public void updateComposite(CompositeDto compositeDto, Long compsiteId, Long userId) {
        Optional<Composite> optionalComposite = compositeRepository.findById(compsiteId);
        if(optionalComposite.isPresent()) {
            Composite composite = optionalComposite.get();
            if(composite.getAuthor().getId() != userId) {
                throw new AccessDeniedException();
            }
            Composite newComposite = compositeMapper.mapToEntity(calculateProperties(compositeDto));
            composite.setName(newComposite.getName());
            composite.setDescription(newComposite.getDescription());
            composite.setAuthor(newComposite.getAuthor());
            composite.setCompositeType(newComposite.getCompositeType());
            composite.setCompositeMaterials(newComposite.getCompositeMaterials());
            composite.setCompositeSumOfHeatResistance(newComposite.getCompositeSumOfHeatResistance());
            composite.setCompositeHeatTransferCoefficient(newComposite.getCompositeHeatTransferCoefficient());
            composite.setCompositeDiffusionResistance(newComposite.getCompositeDiffusionResistance());
            compositeRepository.saveComposite(composite);
        } else {
            saveComposite(compositeDto);
        }
    }

    public void saveComposite(CompositeDto compositeDto) {
        CompositeDto calculatedCompositeDto = calculateProperties(compositeDto);
        Long id = compositeRepository.saveComposite(compositeMapper.mapToEntity(calculatedCompositeDto)).getId();
        if(id == null){
            throw new FailedRepositoryOperationException("błąd zapisu");
        }
    }

    public void deleteComposite(Long compositeId, Long userId) {
        Optional<Composite> composite = compositeRepository.findById(compositeId);
        if(!composite.isPresent()) {
            throw new NoEntityFound("Nie ma takiej przegrody w bazie");
        }
        User author = composite.get().getAuthor();
        if(author == null || author.getId() != userId) {
            throw new AccessDeniedException();
        }
        compositeRepository.deleteComposite(compositeId);
        if(compositeRepository.isPresent(compositeId)) {
            throw new FailedRepositoryOperationException("Nie udało się usunąć przegrody");
        }
    }
}
