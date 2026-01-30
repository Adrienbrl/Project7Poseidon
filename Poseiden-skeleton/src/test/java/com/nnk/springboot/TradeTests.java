package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TradeTests {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    void tradeTest() {

        // Create
        Trade trade = new Trade();
        trade.setUsername("testUser");
        trade.setAccount("Account Test");
        trade.setType("Type Test");
        trade.setBuyQuantity(10.0);

        // Save
        trade = tradeRepository.save(trade);
        assertNotNull(trade.getTradeId());
        assertEquals(10.0, trade.getBuyQuantity());

        // Update
        trade.setBuyQuantity(20.0);
        trade = tradeRepository.save(trade);
        assertEquals(20.0, trade.getBuyQuantity());

        // Find
        List<Trade> listResult = tradeRepository.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = trade.getTradeId();
        tradeRepository.delete(trade);

        Optional<Trade> tradeFound = tradeRepository.findById(id);
        assertFalse(tradeFound.isPresent());
    }
}
