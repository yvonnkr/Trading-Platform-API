package com.yvolabs.tradingplatform.repository;

import com.yvolabs.tradingplatform.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 30/10/2024
 */
public interface CoinRepo extends JpaRepository<Coin, String> {
}
