package com.lisdev.transactional_api.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class FindByIdentification {

    @NotBlank(message = "Identification is required")
    private String identification;

}
