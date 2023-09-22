package com.ubt.andi.businessrecommendation.controllers;
import com.ubt.andi.businessrecommendation.models.ApplicationUser;
import com.ubt.andi.businessrecommendation.models.Business;
import com.ubt.andi.businessrecommendation.services.ApplicationUserService;
import com.ubt.andi.businessrecommendation.services.BusinessService;
import com.ubt.andi.businessrecommendation.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.ubt.andi.businessrecommendation.models.Business;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    private BusinessService businessService;
    private ApplicationUserService userService;
    private RatingService ratingService;
    @Autowired
    public SearchController(BusinessService businessService
            ,ApplicationUserService userService,RatingService ratingService){
        this.businessService=businessService;
        this.userService=userService;
        this.ratingService=ratingService;
    }
    @GetMapping("/search")
    public String searchPage(){
        return "search-page";
    }

    @PostMapping("/search")
    public String searchThings(@RequestParam("businessName") String businessName, Model model){
        List<Business> businessesByNotUser = businessService.findBusinessesByNotApplicationUser("",businessName);
        model.addAttribute("businesses",businessesByNotUser);
        String username = SecurityContextUtil.getSessionUser();
        ApplicationUser user = userService.findByUsername(username);
        model.addAttribute("user",user);
        return "search-page-data";
    }
    @GetMapping("/search")
    public String searchPage(Model model){
        model.addAttribute("businesses",new ArrayList<Business>());
        return "search-page";
    }
}
