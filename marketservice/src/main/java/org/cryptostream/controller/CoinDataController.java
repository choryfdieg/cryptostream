package org.cryptostream.controller;

import org.cryptostream.config.CoinConfig;
import org.cryptostream.controller.api.CoinDataAPI;
import org.cryptostream.model.CoinHistoryResponse;
import org.cryptostream.model.PriceResponse;
import org.cryptostream.service.ICoingeckoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/market")
public class CoinDataController implements CoinDataAPI {

    @Autowired
    private ICoingeckoClient coingeckoClient;
    
    @GetMapping("/prices/all")
    public ResponseEntity<PriceResponse> getAllCoinPrices(){
        return ResponseEntity.ok(this.coingeckoClient.getAllPrices());
    }
    
    @GetMapping("/prices/{coin_id}")
    public ResponseEntity<PriceResponse> getPricesByCoinId(String coinId) {
        String coingeckoId = CoinConfig.getCryptoIdsMap().get(coinId);
        return ResponseEntity.ok(coingeckoClient.getPriceByCoinId(coingeckoId));
    }
    
    @GetMapping("/history/{coin_id}")
    public ResponseEntity<CoinHistoryResponse> getCoinHistoryByCoinId(
            String coinId,
            String startDate,
            String endDate) {
    
        String coingeckoId = CoinConfig.getCryptoIdsMap().get(coinId);
    
        CoinHistoryResponse historyResponse = coingeckoClient.getCoinHistoryByCoinId(coingeckoId, startDate, endDate);
        
        return ResponseEntity.ok(historyResponse);
    }
}
