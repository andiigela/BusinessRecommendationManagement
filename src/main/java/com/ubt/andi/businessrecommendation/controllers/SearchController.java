package com.ubt.andi.businessrecommendation.controllers;

import com.ubt.andi.businessrecommendation.models.Business;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @GetMapping("/search")
    public String searchPage(Model model){
        model.addAttribute("businesses",new ArrayList<Business>());
        return "search-page";
    }

}
