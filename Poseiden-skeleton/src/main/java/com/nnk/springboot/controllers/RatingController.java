package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.RatingRepository;

import com.nnk.springboot.domain.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@Controller
public class RatingController {
    private final RatingRepository ratingRepository;

    @RequestMapping("/rating/list")
    public String home(Model model, Authentication authentication) {

        if (isAdmin(authentication)) {
            model.addAttribute("ratings", ratingRepository.findAll());
        } else {
            String username = authentication.getName();
            model.addAttribute("ratings", ratingRepository.findAllByUsername(username));
        }

        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            return "rating/add";
        }

        rating.setUsername(authentication.getName());

        ratingRepository.save(rating);
        return "redirect:/rating/list";
    }


    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {

        Rating rating;

        if (isAdmin(authentication)) {
            rating = ratingRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        } else {
            rating = ratingRepository.findByIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));
        }

        model.addAttribute("rating", rating);
        return "rating/update";
    }


    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id,
                               @Valid Rating rating,
                               BindingResult result,
                               Model model,
                               Authentication authentication) {

        if (result.hasErrors()) {
            rating.setId(id);
            return "rating/update";
        }

        if (!isAdmin(authentication)) {
            ratingRepository.findByIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));

            rating.setUsername(authentication.getName());
        } else {
            Rating existing = ratingRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
            rating.setUsername(existing.getUsername());
        }

        rating.setId(id);
        ratingRepository.save(rating);
        return "redirect:/rating/list";
    }


    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model, Authentication authentication) {

        if (isAdmin(authentication)) {
            Rating rating = ratingRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
            ratingRepository.delete(rating);
        } else {
            ratingRepository.findByIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));
            ratingRepository.deleteById(id);
        }

        return "redirect:/rating/list";
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

}
