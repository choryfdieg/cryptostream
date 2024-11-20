package org.cryptostream.marketservice.service;

import org.cryptostream.marketservice.model.PriceResponse;

public interface ICoingeckoClient {

    PriceResponse getAllPrices();

    PriceResponse getPriceByCoinId(String coinId);

    Object getCoinHistoryByCoinId(String coinId, String startDate, String endDate);
}