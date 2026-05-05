package com.lisdev.transactional_api.infrastructure.web.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExistsResponse {

    private final Boolean exists;
}
