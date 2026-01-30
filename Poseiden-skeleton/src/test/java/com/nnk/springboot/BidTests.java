package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BidTests {

    @Autowired
    private BidListRepository bidListRepository;

    @Test
    void bidListTest() {

        // Create
        BidList bidList = new BidList();
        bidList.setUsername("testUser");
        bidList.setAccount("Account Test");
        bidList.setType("Type Test");
        bidList.setBidQuantity(10.0);

        // Save
        bidList = bidListRepository.save(bidList);
        assertNotNull(bidList.getBidListId());
        assertEquals(10.0, bidList.getBidQuantity());

        // Update
        bidList.setBidQuantity(20.0);
        bidList = bidListRepository.save(bidList);
        assertEquals(20.0, bidList.getBidQuantity());

        // Find
        List<BidList> listResult = bidListRepository.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = bidList.getBidListId();
        bidListRepository.delete(bidList);

        Optional<BidList> bidListFound = bidListRepository.findById(id);
        assertFalse(bidListFound.isPresent());
    }
}
