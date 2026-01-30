package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class BidListController {

    private final BidListRepository bidListRepository;

    public BidListController(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model, Authentication authentication) {

        if (isAdmin(authentication)) {
            model.addAttribute("bidLists", bidListRepository.findAll());
        } else {
            String username = authentication.getName();
            model.addAttribute("bidLists", bidListRepository.findAllByUsername(username));
        }

        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            return "bidList/add";
        }

        bidList.setUsername(authentication.getName());

        bidListRepository.save(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {

        BidList bidList;
        if (isAdmin(authentication)) {
            bidList = bidListRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id: " + id));
        } else {
            bidList = bidListRepository.findByBidListIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));
        }

        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id,
                            @Valid BidList bidList,
                            BindingResult result,
                            Model model,
                            Authentication authentication) {

        if (result.hasErrors()) {
            bidList.setBidListId(id);
            return "bidList/update";
        }

        if (!isAdmin(authentication)) {
            bidListRepository.findByBidListIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));

            bidList.setUsername(authentication.getName());
        } else {
            BidList existing = bidListRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id: " + id));
            bidList.setUsername(existing.getUsername());
        }

        bidList.setBidListId(id);
        bidListRepository.save(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, Authentication authentication) {

        if (isAdmin(authentication)) {
            BidList bidList = bidListRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id: " + id));
            bidListRepository.delete(bidList);
        } else {
            bidListRepository.findByBidListIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));

            bidListRepository.deleteById(id);
        }

        return "redirect:/bidList/list";
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
