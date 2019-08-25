package pl.michal_baniowski.coutmywall.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.dto.CompositeDto;
import pl.michal_baniowski.coutmywall.entity.Composite;
import pl.michal_baniowski.coutmywall.entity.CompositeType;
import pl.michal_baniowski.coutmywall.entity.auth.User;
import pl.michal_baniowski.coutmywall.repository.CompositeTypeRepository;
import pl.michal_baniowski.coutmywall.repository.UserRepository;
import pl.michal_baniowski.coutmywall.service.HeatTransferCoeficientRequirementsService;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompositeMapper implements DtoMapper<Composite, CompositeDto> {

    private CompositeTypeRepository typeRepository;
    private CompositeMaterialMapper compositeMaterialMapper;
    private UserRepository userRepository;
    private HeatTransferCoeficientRequirementsService requirementsService;

    @Autowired
    public CompositeMapper(CompositeTypeRepository typeRepository,
                           CompositeMaterialMapper compositeMaterialMapper,
                           UserRepository userRepository,
                           HeatTransferCoeficientRequirementsService requirementsService) {
        this.typeRepository = typeRepository;
        this.compositeMaterialMapper = compositeMaterialMapper;
        this.userRepository = userRepository;
        this.requirementsService = requirementsService;
    }

    @Override
    public Composite mapToEntity(CompositeDto dtoObject) {
        Composite composite = new Composite();
        composite.setId(dtoObject.getId());
        composite.setName(dtoObject.getName());
        composite.setDescription(dtoObject.getDescription());
        Optional<CompositeType> typeNameOptional = typeRepository.findFirstByName(dtoObject.getCompositeType());
        if (typeNameOptional.isPresent()) {
            composite.setCompositeType(typeNameOptional.get());
        }
        composite.setCompositeMaterials(dtoObject.getCompositeMaterials().stream()
                .map(compositeMaterialMapper::mapToEntity)
                .collect(Collectors.toList()));
        if(dtoObject.getAuthor() != null){
            User user = userRepository.findByUsername(dtoObject.getAuthor());
            composite.setAuthor(user);
        }
        composite.setCompositeDiffusionResistance(dtoObject.getCompositeDiffusionResistance());
        composite.setCompositeSumOfHeatResistance(dtoObject.getCompositeSumOfHeatResistance());
        composite.setCompositeHeatTransferCoefficient(dtoObject.getCompositeHeatTransferCoefficient());
        return composite;
    }

    @Override
    public CompositeDto mapToDto(Composite entityObject) {
        CompositeDto compositeDto = new CompositeDto();
        compositeDto.setId(entityObject.getId());
        compositeDto.setName(entityObject.getName());
        compositeDto.setDescription(entityObject.getDescription());
        compositeDto.setCompositeType(entityObject.getCompositeType().getName());
        compositeDto.setCompositeMaterials(entityObject.getCompositeMaterials().stream()
                .map(compositeMaterialMapper::mapToDto)
                .collect(Collectors.toList()));
        if(entityObject.getAuthor() != null) {
            compositeDto.setAuthor(entityObject.getAuthor().getUsername());
        }
        compositeDto.setCompositeDiffusionResistance(entityObject.getCompositeDiffusionResistance());
        compositeDto.setCompositeSumOfHeatResistance(entityObject.getCompositeSumOfHeatResistance());
        compositeDto.setCompositeHeatTransferCoefficient(entityObject.getCompositeHeatTransferCoefficient());
        compositeDto.setRequirementsResultMap(new HashMap<>());
        requirementsService.checkRequirements(compositeDto);
        return compositeDto;
    }
}
