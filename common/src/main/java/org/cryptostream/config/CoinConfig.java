package org.cryptostream.config;

import java.util.*;
import java.util.stream.Collectors;

public class CoinConfig {
    
    private static final Set<String> SUPPORTED_COIN_IDS = new HashSet<>();
    private static final Map<String, String> CRYPTO_IDS_MAP = new HashMap<>();
    
    static {
    
        SUPPORTED_COIN_IDS.add("bitcoin");
        SUPPORTED_COIN_IDS.add("solana");
        
        CRYPTO_IDS_MAP.put("BTC", "bitcoin");
        CRYPTO_IDS_MAP.put("SOL", "solana");
    }
    
    public static Set<String> getSupportedCoinIds() {
        
        return Collections.unmodifiableSet(SUPPORTED_COIN_IDS);
    }
    
    public static Map<String, String> getCryptoIdsMap() {
        return Collections.unmodifiableMap(CRYPTO_IDS_MAP);
    }
    
    public static String getSupportedCoinIdsAsString() {
        return SUPPORTED_COIN_IDS.stream()
            .collect(Collectors.joining(","));
    }
}