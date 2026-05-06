package com.lisdev.transactionalapi.infrastructure.web.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExistsResponse {

    private final Boolean exists;
}
