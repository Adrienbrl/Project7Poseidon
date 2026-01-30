package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RuleNameController {

    private final RuleNameRepository ruleNameRepository;

    public RuleNameController(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model, Authentication authentication) {

        if (isAdmin(authentication)) {
            model.addAttribute("ruleNames", ruleNameRepository.findAll());
        } else {
            String username = authentication.getName();
            model.addAttribute("ruleNames", ruleNameRepository.findAllByUsername(username));
        }

        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model, Authentication authentication) {

        if (result.hasErrors()) {
            return "ruleName/add";
        }

        ruleName.setUsername(authentication.getName());
        ruleNameRepository.save(ruleName);

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {

        RuleName ruleName;

        if (isAdmin(authentication)) {
            ruleName = ruleNameRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid ruleName id: " + id));
        } else {
            ruleName = ruleNameRepository.findByIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));
        }

        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id,
                                 @Valid RuleName ruleName,
                                 BindingResult result,
                                 Model model,
                                 Authentication authentication) {

        if (result.hasErrors()) {
            ruleName.setId(id);
            return "ruleName/update";
        }

        if (!isAdmin(authentication)) {
            ruleNameRepository.findByIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));
            ruleName.setUsername(authentication.getName());
        } else {
            RuleName existing = ruleNameRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid ruleName id: " + id));
            ruleName.setUsername(existing.getUsername());
        }

        ruleName.setId(id);
        ruleNameRepository.save(ruleName);

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model, Authentication authentication) {

        if (isAdmin(authentication)) {
            RuleName ruleName = ruleNameRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid ruleName id: " + id));
            ruleNameRepository.delete(ruleName);
        } else {
            ruleNameRepository.findByIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));
            ruleNameRepository.deleteById(id);
        }

        return "redirect:/ruleName/list";
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}