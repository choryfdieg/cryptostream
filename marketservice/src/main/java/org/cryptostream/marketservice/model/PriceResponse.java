package org.cryptostream.marketservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class PriceResponse {
    private Map<String, Price> prices;
    
    @Data
    public static class Price {
        private double usd;
    }
}

