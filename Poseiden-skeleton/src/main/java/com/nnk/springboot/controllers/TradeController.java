package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class TradeController {

    private final TradeRepository tradeRepository;

    public TradeController(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @RequestMapping("/trade/list")
    public String home(Model model, Authentication authentication) {

        if (isAdmin(authentication)) {
            model.addAttribute("trades", tradeRepository.findAll());
        } else {
            String username = authentication.getName();
            model.addAttribute("trades", tradeRepository.findAllByUsername(username));
        }

        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model, Authentication authentication) {

        if (result.hasErrors()) {
            return "trade/add";
        }

        trade.setUsername(authentication.getName());

        tradeRepository.save(trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {

        Trade trade;

        if (isAdmin(authentication)) {
            trade = tradeRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid trade id: " + id));
        } else {
            trade = tradeRepository.findByTradeIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));
        }

        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id,
                              @Valid Trade trade,
                              BindingResult result,
                              Model model,
                              Authentication authentication) {

        if (result.hasErrors()) {
            trade.setTradeId(id);
            return "trade/update";
        }

        if (!isAdmin(authentication)) {
            tradeRepository.findByTradeIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));

            trade.setUsername(authentication.getName());
        } else {
            Trade existing = tradeRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid trade id: " + id));
            trade.setUsername(existing.getUsername());
        }

        trade.setTradeId(id);
        tradeRepository.save(trade);

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model, Authentication authentication) {

        if (isAdmin(authentication)) {
            Trade trade = tradeRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid trade id: " + id));
            tradeRepository.delete(trade);
        } else {
            tradeRepository.findByTradeIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));
            tradeRepository.deleteById(id);
        }

        return "redirect:/trade/list";
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
