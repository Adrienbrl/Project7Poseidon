package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CurvePointTests {

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    void curvePointTest() {

        // Create
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setUsername("testUser");
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10.0);
        curvePoint.setValue(30.0);

        // Save
        curvePoint = curvePointRepository.save(curvePoint);
        assertNotNull(curvePoint.getId());
        assertEquals(10, curvePoint.getCurveId());
        assertEquals(10.0, curvePoint.getTerm());
        assertEquals(30.0, curvePoint.getValue());

        // Update
        curvePoint.setValue(50.0);
        curvePoint = curvePointRepository.save(curvePoint);
        assertEquals(50.0, curvePoint.getValue());

        // Find
        List<CurvePoint> listResult = curvePointRepository.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = curvePoint.getId();
        curvePointRepository.delete(curvePoint);

        Optional<CurvePoint> curvePointFound = curvePointRepository.findById(id);
        assertFalse(curvePointFound.isPresent());
    }
}
