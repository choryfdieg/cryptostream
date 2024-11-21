package org.cryptostream.marketservice.controller;

import org.cryptostream.config.CoinConfig;
import org.cryptostream.model.PriceResponse;
import org.cryptostream.services.ICoingeckoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/crypto-data")
public class CoinDataController {

    @Autowired 
    private final ICoingeckoClient coingeckoClient;
    
    public CoinDataController(ICoingeckoClient coingeckoClient) {
        this.coingeckoClient = coingeckoClient;
    }
    
    @GetMapping("/prices/all")
    public PriceResponse getAllCoinPrices(){
        return this.coingeckoClient.getAllPrices();
    }
    
    @GetMapping("/prices/{coin_id}")
    public PriceResponse getPricesByCoinId(@PathVariable(name = "coin_id") String coinId) {
        if (!CoinConfig.getSupportedCoinIds().contains(coinId)) {
            throw new IllegalArgumentException("The coin with id " + coinId + " is not supported");
        }
        return coingeckoClient.getPriceByCoinId(coinId);
    }
    
    @GetMapping("/history/{coin_id}")
    public Object getCoinHistoryByCoinId(
        @PathVariable(name = "coin_id") String coinId,
        @RequestParam(name = "start_date", required = true) String startDate,
        @RequestParam(name = "end_date", required = true) String endDate) {
    
        if (!CoinConfig.getSupportedCoinIds().contains(coinId)) {
            throw new IllegalArgumentException("The coin with id " + coinId + " is not supported");
        }
        
        return coingeckoClient.getCoinHistoryByCoinId(coinId, startDate, endDate);
    }
}
