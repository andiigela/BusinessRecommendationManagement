package com.ubt.andi.businessrecommendation.controllers;

import com.ubt.andi.businessrecommendation.models.Business;
import com.ubt.andi.businessrecommendation.models.Image;
import com.ubt.andi.businessrecommendation.models.SearchForm;
import com.ubt.andi.businessrecommendation.services.BusinessService;
import com.ubt.andi.businessrecommendation.services.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BusinessController {
    private BusinessService businessService;
    private ImageUploadService imageUploadService;
    @Autowired
    public BusinessController(BusinessService businessService,ImageUploadService imageUploadService){
        this.businessService=businessService;
        this.imageUploadService=imageUploadService;
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
    public String createBusiness(@ModelAttribute("business") Business business,
                                 @RequestParam("imageFiles") List<MultipartFile> imageFiles) throws IOException{
        businessService.createBusiness(business);
        List<Image> images = new ArrayList<>();
        for(MultipartFile imageFile : imageFiles){
            Image image = new Image();
            image.setPhotoUrl(imageFile.getOriginalFilename());
            image.setBusiness(business);
            imageUploadService.createImage(image);
            images.add(image);
            imageUploadService.uploadImage(imageFile);

        }
        business.setImages(images);
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
    public String editBusiness(@ModelAttribute("businessEdit") Business business,
                               @RequestParam("imageFiles") List<MultipartFile> imageFiles) throws IOException{
        businessService.updateBusiness(business);
        List<Image> images = new ArrayList<>();
        for(MultipartFile imageFile : imageFiles){
            Image image = new Image();
            image.setPhotoUrl(imageFile.getOriginalFilename());
            image.setBusiness(business);
            imageUploadService.createImage(image);
            images.add(image);
            imageUploadService.uploadImage(imageFile);
        }
        business.setImages(images);
        businessService.updateBusiness(business);
        return "redirect:/business";
    }
    @PostMapping("/business/delete/{businessId}")
    public String deleteBusiness(@PathVariable("businessId") Long id){
        businessService.deleteBusiness(id);
        return "redirect:/business";
    }
    @PostMapping("/add/images")
    public String addBusiness(@ModelAttribute Business business,
                              @RequestParam("photoFile") MultipartFile photoFile) throws IOException {

        return "redirect:/business/list";
    }

    @GetMapping("/search")
    public String searchForm(Model model){
        model.addAttribute("business", new Business());
        return "search-page";
    }
    @GetMapping("/search/")
    public String searchByName(@RequestParam("name") String name,Model model){
        List<Business> businessListByName = businessService.findBusinessesByName(name);
        System.out.println("Size: " + businessListByName.size());
        model.addAttribute("businesses",businessListByName);
        return "search-results";
    }
}
