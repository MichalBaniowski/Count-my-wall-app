package pl.michal_baniowski.coutmywall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.dto.CompositeTypeDto;
import pl.michal_baniowski.coutmywall.entity.CompositeType;
import pl.michal_baniowski.coutmywall.mapper.CompositeTypeMapper;
import pl.michal_baniowski.coutmywall.repository.CompositeTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompositeTypeService {
    private CompositeTypeMapper mapper;
    private CompositeTypeRepository compositeTypeRepository;

    @Autowired
    public CompositeTypeService(CompositeTypeMapper mapper,
                                CompositeTypeRepository compositeTypeRepository) {
        this.mapper = mapper;
        this.compositeTypeRepository = compositeTypeRepository;
    }
    public List<CompositeTypeDto> getAllCompositeTypeDto() {
        return compositeTypeRepository.findAll().stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    public CompositeTypeDto getCompositeTypeDtoById(Long id) {
        Optional<CompositeType> compositeTypeOptional = compositeTypeRepository.findById(id);
        if(compositeTypeOptional.isPresent()){
            return mapper.mapToDto(compositeTypeOptional.get());
        }
        return null;
    }
}
