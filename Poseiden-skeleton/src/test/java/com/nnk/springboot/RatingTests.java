package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RatingTests {

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    void ratingTest() {
        Rating rating = new Rating();
        rating.setUsername("testUser");
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPrating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(10);

        // Save
        rating = ratingRepository.save(rating);
        assertNotNull(rating.getId());
        assertEquals(10, rating.getOrderNumber());

        // Update
        rating.setOrderNumber(20);
        rating = ratingRepository.save(rating);
        assertEquals(20, rating.getOrderNumber());

        // Find
        List<Rating> listResult = ratingRepository.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = rating.getId();
        ratingRepository.delete(rating);

        Optional<Rating> ratingFound = ratingRepository.findById(id);
        assertFalse(ratingFound.isPresent());
    }
}
