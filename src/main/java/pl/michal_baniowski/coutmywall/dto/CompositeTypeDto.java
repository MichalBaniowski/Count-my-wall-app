package pl.michal_baniowski.coutmywall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompositeTypeDto {
    private Long id;
    private String name;
    private String description;
}
