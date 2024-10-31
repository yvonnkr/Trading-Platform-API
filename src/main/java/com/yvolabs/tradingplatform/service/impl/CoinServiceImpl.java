package com.yvolabs.tradingplatform.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yvolabs.tradingplatform.cache.CoinCache;
import com.yvolabs.tradingplatform.model.Coin;
import com.yvolabs.tradingplatform.repository.CoinRepo;
import com.yvolabs.tradingplatform.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 30/10/2024
 */

@Service
@RequiredArgsConstructor
public class CoinServiceImpl implements CoinService {

    private final CoinRepo coinRepo;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    private final String COIN_API_BASE_URL = "https://api.coingecko.com/api/v3/coins/";

    @Override
    public List<Coin> getCoinList(int page) throws Exception {
        String url = COIN_API_BASE_URL + "markets?vs_currency=usd&per_page=10&page=" + page;
        String cacheKey = "getCoinList_page_" + page;

        String cachedResponse = CoinCache.getCachedResponse(cacheKey);
        if (cachedResponse != null) {
            return objectMapper.readValue(cachedResponse, new TypeReference<>() {
            });
        }

        try {
            HttpEntity<String> entity = new HttpEntity<>("parameters", new HttpHeaders());
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            CoinCache.cacheResponse(cacheKey, response.getBody());
            return objectMapper.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getMarketChart(String coinId, int days) throws Exception {
        String url = COIN_API_BASE_URL + coinId + "/market_chart?vs_currency=usd&days=" + days;
        String cacheKey = "getMarketChart_" + coinId + "_days_" + days;

        String cachedResponse = CoinCache.getCachedResponse(cacheKey);
        if (cachedResponse != null) {
            return cachedResponse;
        }

        try {
            HttpEntity<String> entity = new HttpEntity<>("parameters", new HttpHeaders());
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            CoinCache.cacheResponse(cacheKey, response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getCoinDetails(String coinId) throws Exception {
        String url = COIN_API_BASE_URL + coinId;
        String cacheKey = "getCoinDetails_" + coinId;

        String cachedResponse = CoinCache.getCachedResponse(cacheKey);
        if (cachedResponse != null) {
            return cachedResponse;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            Coin coin = new Coin();
            coin.setId(jsonNode.get("id").asText());
            coin.setName(jsonNode.get("name").asText());
            coin.setSymbol(jsonNode.get("symbol").asText());
            coin.setImage(jsonNode.get("image").get("large").asText());

            JsonNode marketData = jsonNode.get("market_data");

            coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());
            coin.setMarketCap(marketData.get("market_cap").get("usd").asLong());
            coin.setMarketCapRank(marketData.get("market_cap_rank").asInt());
            coin.setTotalVolume(marketData.get("total_volume").get("usd").asLong());
            coin.setHigh24h(marketData.get("high_24h").get("usd").asDouble());
            coin.setLow24h(marketData.get("low_24h").get("usd").asDouble());
            coin.setPriceChange24h(marketData.get("price_change_24h").asDouble());
            coin.setPriceChangePercentage24h(marketData.get("price_change_percentage_24h").asDouble());
            coin.setMarketCapChange24h(marketData.get("market_cap_change_24h").asLong());
            coin.setMarketCapChangePercentage24h(marketData.get("market_cap_change_percentage_24h").asLong());

            coin.setTotalSupply(marketData.get("total_supply").asLong());

            coinRepo.save(coin);
            CoinCache.cacheResponse(cacheKey, response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Coin findById(String coinId) throws Exception {
        Optional<Coin> optionalCoin = coinRepo.findById(coinId);
        if (optionalCoin.isEmpty()) {
            throw new Exception("Coin not found");
        }
        return optionalCoin.get();
    }

    @Override
    public String searchCoin(String keyword) throws Exception {
        String url = "https://api.coingecko.com/api/v3/search?query=" + keyword;
        String cacheKey = "searchCoin_" + keyword;

        String cachedResponse = CoinCache.getCachedResponse(cacheKey);
        if (cachedResponse != null) {
            return cachedResponse;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            CoinCache.cacheResponse(cacheKey, response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getTop50CoinByMarketCapRank() throws Exception {
        String url = COIN_API_BASE_URL +  "markets?vs_currency=usd&per_page=50&page=1";
        String cacheKey = "getTop50CoinByMarketCapRank";

        String cachedResponse = CoinCache.getCachedResponse(cacheKey);
        if (cachedResponse != null) {
            return cachedResponse;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            CoinCache.cacheResponse(cacheKey, response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getTrendingCoins() throws Exception {
        String url = "https://api.coingecko.com/api/v3/search/trending";
        String cacheKey = "getTrendingCoins";

        String cachedResponse = CoinCache.getCachedResponse(cacheKey);
        if (cachedResponse != null) {
            return cachedResponse;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            CoinCache.cacheResponse(cacheKey, response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }
}
