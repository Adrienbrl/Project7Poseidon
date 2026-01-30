package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CurveController {

    private final CurvePointRepository curvePointRepository;

    public CurveController(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model, Authentication authentication) {

        if (isAdmin(authentication)) {
            model.addAttribute("curvePoints", curvePointRepository.findAll());
        } else {
            String username = authentication.getName();
            model.addAttribute("curvePoints", curvePointRepository.findAllByUsername(username));
        }

        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint,
                           BindingResult result,
                           Model model,
                           Authentication authentication) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        curvePoint.setUsername(authentication.getName());
        curvePointRepository.save(curvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id,
                                 Model model,
                                 Authentication authentication) {

        CurvePoint curvePoint;

        if (isAdmin(authentication)) {
            curvePoint = curvePointRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id: " + id));
        } else {
            curvePoint = curvePointRepository.findByIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));
        }

        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id,
                                   @Valid CurvePoint curvePoint,
                                   BindingResult result,
                                   Model model,
                                   Authentication authentication) {

        if (result.hasErrors()) {
            curvePoint.setId(id);
            return "curvePoint/update";
        }

        if (!isAdmin(authentication)) {
            curvePointRepository.findByIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));

            curvePoint.setUsername(authentication.getName());
        } else {
            CurvePoint existing = curvePointRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id: " + id));
            curvePoint.setUsername(existing.getUsername());
        }

        curvePoint.setId(id);
        curvePointRepository.save(curvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id,
                                   Model model,
                                   Authentication authentication) {

        if (isAdmin(authentication)) {
            CurvePoint curvePoint = curvePointRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id: " + id));
            curvePointRepository.delete(curvePoint);
        } else {
            curvePointRepository.findByIdAndUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not allowed"));
            curvePointRepository.deleteById(id);
        }

        return "redirect:/curvePoint/list";
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
