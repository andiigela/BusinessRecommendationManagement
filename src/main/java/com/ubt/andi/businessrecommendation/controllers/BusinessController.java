package com.ubt.andi.businessrecommendation.controllers;

import com.ubt.andi.businessrecommendation.models.ApplicationUser;
import com.ubt.andi.businessrecommendation.models.Business;
import com.ubt.andi.businessrecommendation.models.Image;
import com.ubt.andi.businessrecommendation.models.Rating;
import com.ubt.andi.businessrecommendation.services.ApplicationUserService;
import com.ubt.andi.businessrecommendation.services.BusinessService;
import com.ubt.andi.businessrecommendation.services.ImageUploadService;
import com.ubt.andi.businessrecommendation.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class BusinessController {
    private BusinessService businessService;
    private ApplicationUserService userService;
    private ImageUploadService imageUploadService;
    @Autowired
    public BusinessController(BusinessService businessService,ImageUploadService imageUploadService,
                              ApplicationUserService userService){
        this.businessService=businessService;
        this.imageUploadService=imageUploadService;
        this.userService=userService;
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
            if(!imageFile.isEmpty()){
                Image image = new Image();
                String fileName = imageUploadService.uploadImage(imageFile);
                image.setPhotoUrl(fileName);
                image.setBusiness(business);
                imageUploadService.createImage(image);
                images.add(image);
            }
        }
        business.setImages(images);
       // business.setStarValues(Arrays.asList((byte)0));
        businessService.createBusiness(business);
        return "redirect:/business";
    }
    @GetMapping("/business/edit/{businessId}")
    public String editBusinessForm(@PathVariable("businessId") Long id,Model model){
        Business business = businessService.getBusinessById(id);
        if(business != null){
            model.addAttribute("businessEdit",business);
        }
        return "business-edit";
    }
    @PostMapping("/business/edit/{businessId}")
    public String editBusiness(@ModelAttribute("businessEdit") Business business,
                               @RequestParam("imageFiles") List<MultipartFile> imageFiles) throws IOException{
        List<Image> images = new ArrayList<>();
        for(MultipartFile imageFile : imageFiles){
            if(!imageFile.isEmpty()){
                Image image = new Image();
                String fileName = imageUploadService.uploadImage(imageFile);
                image.setPhotoUrl(fileName);
                image.setBusiness(business);
                imageUploadService.createImage(image);
                images.add(image);
            }
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
    @PostMapping("/business/addstars")
    public String addStar(@RequestParam("star") String star,@RequestParam("businessId") Long id){
        byte starValue = Byte.parseByte(star);
        Business business = businessService.findBusiness(id);
        String username = SecurityContextUtil.getSessionUser();
        ApplicationUser user = userService.findByUsername(username);
        business.getStarValues().add(starValue); // adding star value
        business.getRatedByUsers().add(user); // relationship business - user
        int sum=0;
        for(int i=0;i<business.getStarValues().size();i++){
            sum += business.getStarValues().get(i);
        }
        System.out.println("Rating value before: " + business.getRatingValue());
        double avg = (double)sum/(double)business.getStarValues().size();
        System.out.println("Rating value after: " + business.getRatingValue());
        business.setRatingValue(avg);
        businessService.updateBusinessWithoutUser(business);
        user.getRatedBusinesses().add(business); // relationship user - business
        userService.saveUser(user);
        return "redirect:/search";
    }
    /*
    @PostMapping("/add/images")
    public String addBusiness(@ModelAttribute Business business,
                              ) throws IOException {
        return "redirect:/business/list";
    }*/

    /*
    @PostMapping("/business/deleteImage")
    public String deleteImage(@RequestParam("imageToDelete") String imageUrl) throws IOException{
        imageUploadService.deleteImage(imageUrl);
        return "redirect:/business";
    }
    */
}
