package com.yvolabs.tradingplatform.service;

import com.yvolabs.tradingplatform.model.Coin;

import java.util.List;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 30/10/2024
 */
public interface CoinService {

    List<Coin> getCoinList(int page) throws Exception;

    String getMarketChart(String coinId, int days) throws Exception;

    String getCoinDetails(String coinId) throws Exception;

    Coin findById(String coinId) throws Exception;

    String searchCoin(String keyword) throws Exception;

    String getTop50CoinByMarketCapRank() throws Exception;

    String getTrendingCoins() throws Exception;
}
