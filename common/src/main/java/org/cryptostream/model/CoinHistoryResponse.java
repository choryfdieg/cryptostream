package org.cryptostream.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CoinHistoryResponse {

    @JsonProperty("prices")
    private List<List<Object>> prices;

    @JsonProperty("market_caps")
    private List<List<Object>> marketCaps;

    @JsonProperty("total_volumes")
    private List<List<Object>> totalVolumes;
    
    public List<List<Object>> getPrices() {
        return prices;
    }

    public void setPrices(List<List<Object>> prices) {
        this.prices = prices;
    }

    public List<List<Object>> getMarketCaps() {
        return marketCaps;
    }

    public void setMarketCaps(List<List<Object>> marketCaps) {
        this.marketCaps = marketCaps;
    }

    public List<List<Object>> getTotalVolumes() {
        return totalVolumes;
    }

    public void setTotalVolumes(List<List<Object>> totalVolumes) {
        this.totalVolumes = totalVolumes;
    }
}