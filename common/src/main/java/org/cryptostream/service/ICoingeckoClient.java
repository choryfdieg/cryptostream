package org.cryptostream.service;

import org.cryptostream.model.CoinHistoryResponse;
import org.cryptostream.model.PriceResponse;

public interface ICoingeckoClient {

    PriceResponse getAllPrices();

    PriceResponse getPriceByCoinId(String coinId);
    
    CoinHistoryResponse getCoinHistoryByCoinId(String coinId, String startDate, String endDate);
}