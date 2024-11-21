package org.cryptostream.services;

import org.cryptostream.model.PriceResponse;

public interface ICoingeckoClient {

    PriceResponse getAllPrices();

    PriceResponse getPriceByCoinId(String coinId);

    Object getCoinHistoryByCoinId(String coinId, String startDate, String endDate);
}