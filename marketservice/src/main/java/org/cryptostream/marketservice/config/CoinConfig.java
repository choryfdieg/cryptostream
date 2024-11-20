package org.cryptostream.marketservice.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CoinConfig {
    
    private static final Set<String> SUPPORTED_COIN_IDS = new HashSet<>();
    
    static {
        SUPPORTED_COIN_IDS.add("bitcoin");
        SUPPORTED_COIN_IDS.add("solana");
    }
    
    public static Set<String> getSupportedCoinIds() {
        
        return Collections.unmodifiableSet(SUPPORTED_COIN_IDS);
    }
    
    public static String getSupportedCoinIdsAsString() {
        return SUPPORTED_COIN_IDS.stream()
            .collect(Collectors.joining(","));
    }
}