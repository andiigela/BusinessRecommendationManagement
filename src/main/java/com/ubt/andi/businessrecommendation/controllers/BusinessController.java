package com.ubt.andi.businessrecommendation.controllers;

import com.ubt.andi.businessrecommendation.models.Business;
import com.ubt.andi.businessrecommendation.services.BusinessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BusinessController {
    private BusinessService businessService;
    public BusinessController(BusinessService businessService){
        this.businessService=businessService;
    }
    @GetMapping("/business")
    public String retrieveBusinessList(Model model){
        List<Business> businessList =  businessService.getBusinesses();
        model.addAttribute("businesses",businessList);
        return "business-list";

    }
    @GetMapping("/business/create")
    public String createBusinessForm(Model model){
        Business business = new Business();
        model.addAttribute("business",business);
        return "business-create";
    }
    @PostMapping("/business/create")
    public String createBusiness(@ModelAttribute("business") Business business){
        businessService.createBusiness(business);
        return "redirect:/business";
    }
    @GetMapping("/business/edit/{businessId}")
    public String editBusinessForm(@PathVariable("businessId") Long id,Model model){
        Business business = businessService.getBusinessById(id);
        model.addAttribute("businessEdit",business);
        return "business-edit";
    }
    @PostMapping("/business/edit/{businessId}")
    public String editBusiness(@ModelAttribute("businessEdit") Business business){
        businessService.updateBusiness(business);
        return "redirect:/business";
    }
    @PostMapping("/business/delete/{businessId}")
    public String deleteBusiness(@PathVariable("businessId") Long id){
        businessService.deleteBusiness(id);
        return "redirect:/business";
    }
}
