package org.cryptostream.service;

import org.cryptostream.config.CoinConfig;
import org.cryptostream.model.CoinHistoryResponse;
import org.cryptostream.model.PriceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service
public class CoingeckoClientImpl implements ICoingeckoClient {
    
    @Value("${coingecko.api.base}")
    private String coingeckoApiBase;
    
    @Value("${coingecko.api.key}")
    private String coingeckoApiKey;
    
    private final String PRICE_URL = "/simple/price?ids=%s&vs_currencies=usd";
    private final String HISTORY_URL = "/coins/%s/market_chart/range?vs_currency=usd&from=%d&to=%d";
    
    @Override
    public PriceResponse getAllPrices() {
        
        String ids = CoinConfig.getSupportedCoinIdsAsString();
        
        RestTemplate restTemplate = new RestTemplate();
    
        HttpEntity<String> entity = createHttpEntity();
    
        String url = String.format(coingeckoApiBase + PRICE_URL, ids);
    
        HashMap responseMap = restTemplate.exchange(url, HttpMethod.GET, entity, HashMap.class).getBody();
    
        return PriceResponse.builder().prices(responseMap).build();
        
    }
    
    @Override
    public PriceResponse getPriceByCoinId(String coinId) {
        
        RestTemplate restTemplate = new RestTemplate();
    
        HttpEntity<String> entity = createHttpEntity();
        
        String url = String.format(coingeckoApiBase + PRICE_URL, coinId);
    
        HashMap responseMap = restTemplate.exchange(url, HttpMethod.GET, entity, HashMap.class).getBody();
        
        return PriceResponse.builder().prices(responseMap).build();
        
    }
    
    @Override
    public CoinHistoryResponse getCoinHistoryByCoinId(String coinId, String startDate, String endDate) {
    
        Date start;
        Date end;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
        try {
            start = dateFormat.parse(startDate);
            end = dateFormat.parse(endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    
        long startTime = start.getTime() / 1000;
        long endTime = end.getTime() / 1000;
        
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entity = createHttpEntity();
        
        String url = String.format(coingeckoApiBase + HISTORY_URL, coinId, startTime, endTime);
        
        return restTemplate.exchange(url, HttpMethod.GET, entity, CoinHistoryResponse.class).getBody();
    }
    
    private HttpEntity<String> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-cg-demo-api-key", coingeckoApiKey);
        return new HttpEntity<>(headers);
    }

}
