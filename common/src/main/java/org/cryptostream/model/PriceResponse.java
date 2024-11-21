package org.cryptostream.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class PriceResponse {
    private Map<String, Map<String, BigDecimal>> prices;
}

