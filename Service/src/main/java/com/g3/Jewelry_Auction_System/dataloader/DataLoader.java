package com.g3.Jewelry_Auction_System.dataloader;

import com.g3.Jewelry_Auction_System.entity.*;
import com.g3.Jewelry_Auction_System.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JewelryCategoryRepository jewelryCategoryRepository;
    @Autowired
    PostCategoryRepository postCategoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Arrays.asList(ERole.values()).forEach(roleName -> {
                Role role = Role.builder().roleName(roleName).build();
                roleRepository.save(role);
            });
        }
        if (jewelryCategoryRepository.count() == 0) {
            Arrays.asList(EJewelCategory.values()).forEach(categoryName -> {
                JewelryCategory category = JewelryCategory.builder().categoryName(categoryName).build();
                jewelryCategoryRepository.save(category);
            });
        }
        if (postCategoryRepository.count() == 0) {
            Arrays.asList(EPostCategory.values()).forEach(categoryName -> {
                PostCategory category = PostCategory.builder().categoryName(categoryName).build();
                postCategoryRepository.save(category);
            });
        }
    }
}
