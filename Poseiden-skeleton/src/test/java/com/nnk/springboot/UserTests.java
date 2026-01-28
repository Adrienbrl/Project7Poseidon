package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void userCrudTest() {
        // Create
        User user = new User();
        user.setFullName("User Test");
        user.setUsername("user_test_" + System.currentTimeMillis()); // évite conflit unique
        user.setPassword("Aa1!aaaa"); // respecte ton @Pattern (8 chars + maj + chiffre + symbole)
        user.setRole("USER");

        // Save
        user = userRepository.save(user);
        assertNotNull(user.getId());
        assertEquals("User Test", user.getFullName());
        assertEquals("USER", user.getRole());

        // Update
        user.setFullName("User Test Updated");
        user.setRole("ADMIN");
        user = userRepository.save(user);
        assertEquals("User Test Updated", user.getFullName());
        assertEquals("ADMIN", user.getRole());

        // Find
        List<User> listResult = userRepository.findAll();
        assertTrue(listResult.size() > 0);

        Optional<User> found = userRepository.findById(user.getId());
        assertTrue(found.isPresent());
        assertEquals(user.getUsername(), found.get().getUsername());

        // Delete
        Integer id = user.getId();
        userRepository.delete(user);

        Optional<User> afterDelete = userRepository.findById(id);
        assertFalse(afterDelete.isPresent());
    }

    @Test
    void findByUsername_shouldReturnUser() {
        // Arrange
        String uniqueUsername = "find_user_" + System.currentTimeMillis();

        User user = new User();
        user.setFullName("Find Username");
        user.setUsername(uniqueUsername);
        user.setPassword("Aa1!aaaa");
        user.setRole("USER");
        user = userRepository.save(user);

        // Act
        Optional<User> found = userRepository.findByUsername(uniqueUsername);

        // Assert
        assertTrue(found.isPresent());
        assertEquals(uniqueUsername, found.get().getUsername());

        // Cleanup
        userRepository.deleteById(user.getId());
    }
}
