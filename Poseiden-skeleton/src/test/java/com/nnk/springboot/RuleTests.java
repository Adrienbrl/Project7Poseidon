package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RuleTests {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    void ruleNameTest() {

        // Create
        RuleName rule = new RuleName();
        rule.setUsername("testUser");
        rule.setName("Rule Test");
        rule.setDescription("Description Test");
        rule.setJson("Json Test");
        rule.setTemplate("Template Test");
        rule.setSqlStr("SQL Test");
        rule.setSqlPart("SQL Part Test");

        // Save
        rule = ruleNameRepository.save(rule);
        assertNotNull(rule.getId());
        assertEquals("Rule Test", rule.getName());

        // Update
        rule.setName("Rule Test Updated");
        rule = ruleNameRepository.save(rule);
        assertEquals("Rule Test Updated", rule.getName());

        // Find
        List<RuleName> listResult = ruleNameRepository.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = rule.getId();
        ruleNameRepository.delete(rule);

        Optional<RuleName> ruleFound = ruleNameRepository.findById(id);
        assertFalse(ruleFound.isPresent());
    }
}
