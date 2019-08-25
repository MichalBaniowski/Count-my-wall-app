package pl.michal_baniowski.coutmywall.service;

import org.springframework.stereotype.Service;
import pl.michal_baniowski.coutmywall.dto.CompositeDto;
import pl.michal_baniowski.coutmywall.model.Requirement;
import pl.michal_baniowski.coutmywall.service.interfaces.RequirementsService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HeatTransferCoeficientRequirementsService implements RequirementsService<CompositeDto> {

    private List<Requirement> reuirements;
    private final String requirementsFilePath = "config_files/heatTransferCoefficientRequirements.txt";
    private final String message = "heatTransferCoeficientRequirementMessage";


    public HeatTransferCoeficientRequirementsService() {
        try {
            this.reuirements = getRequirementsList();
        } catch (Exception e) {
            this.reuirements = new ArrayList<>();
        }
    }

    @Override
    public void checkRequirements(CompositeDto elementToCheck) {
        Map<String, String> resultMap = elementToCheck.getRequirementsResultMap();
        List<Requirement> elementTypeRequirements = reuirements.stream()
                .filter(requirement -> requirement.getPropertyName().equals(elementToCheck.getCompositeType()))
                .filter(requirement -> requirement.getValue() < elementToCheck.getCompositeHeatTransferCoefficient())
                .sorted((req1, req2) -> {
                    return req1.getObligatoryDate().isBefore(req2.getObligatoryDate()) ?
                        -1 : 1;
                })
                .collect(Collectors.toList());
        if(elementTypeRequirements.size() != 0) {
           if(elementTypeRequirements.get(0).getObligatoryDate().isBefore(LocalDate.now())) {
               resultMap.put(message, getErrorMessage(elementTypeRequirements.get(0).getValue()));
           } else {
               resultMap.put(message, getWarningMessage(elementTypeRequirements));
           }
        } else {
            resultMap.put(message, "Współczynnik przewodzenia ciepła spełnia wymagania");
        }
    }

    private String getErrorMessage(double requiredValue) {
        return String.format("Współczynnik przewodzenia ciepła nie spełnia wymagań, powinien wynosić maksymalnie %.2f",
                requiredValue);
    }

    private String getWarningMessage(List<Requirement> requirements) {
        StringBuilder sb = new StringBuilder();
        for (Requirement r : requirements) {
            sb.append(String.format(" od %s maksymalny współczynnik: %.2f",
                    r.getObligatoryDate().toString(), r.getValue()));
        }
        return String.format("Wpsółczynnik przewodzenia ciepła spełnia " +
                "obecne wymagania, lecz nie spełnia wymagań obowiązujących %s", sb.toString());
    }

    private List<Requirement> getRequirementsList() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader()
                .getResource(requirementsFilePath).toURI());
        List<Requirement> requirements = new ArrayList<>();
        Files.readAllLines(path).stream()
                .forEach(line -> setRequirementFromLine(line, requirements));
        return requirements;
    }

    private void setRequirementFromLine(String line, List<Requirement> requirements) {
        String[] params = line.split("[,=]");
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            Requirement requirement = new Requirement(params[0],
                    Double.parseDouble(params[1]),
                    LocalDate.parse(params[2], formater));
            requirements.add(requirement);
        } catch (Exception e) {
        }
    }
}
