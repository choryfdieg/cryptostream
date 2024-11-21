package org.cryptostream.controller;

import org.cryptostream.config.CoinConfig;
import org.cryptostream.model.PriceResponse;
import org.cryptostream.service.ICoingeckoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/market")
public class CoinDataController {

    @Autowired
    private ICoingeckoClient coingeckoClient;
    
    @GetMapping("/prices/all")
    public PriceResponse getAllCoinPrices(){
        return this.coingeckoClient.getAllPrices();
    }
    
    @GetMapping("/prices/{coin_id}")
    public PriceResponse getPricesByCoinId(@PathVariable(name = "coin_id") String coinId) {
        String coingeckoId = CoinConfig.getCryptoIdsMap().get(coinId);
        return coingeckoClient.getPriceByCoinId(coingeckoId);
    }
    
    @GetMapping("/history/{coin_id}")
    public Object getCoinHistoryByCoinId(
        @PathVariable(name = "coin_id") String coinId,
        @RequestParam(name = "start_date", required = true) String startDate,
        @RequestParam(name = "end_date", required = true) String endDate) {
    
        String coingeckoId = CoinConfig.getCryptoIdsMap().get(coinId);
        
        return coingeckoClient.getCoinHistoryByCoinId(coingeckoId, startDate, endDate);
    }
}
