package com.example.proj.service;

import com.example.proj.dto.CategoryDTO;
import com.example.proj.model.Category;
import com.example.proj.repositry.CategoryRepositry;
import com.example.proj.utils.GenericObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepositry categoryRepositry;

    public List<Category> getCategory(){
        return categoryRepositry.findAll();
    }

    public Category getCategoryById(long id){
        Optional<Category> byId = categoryRepositry.findById(id);
        return byId.orElse(null);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO){
        Category category = GenericObjectMapper.map(categoryDTO,Category.class);
        Category newCategory = categoryRepositry.save(category);
        return mapToDTO(newCategory);
    }

    public CategoryDTO updateCategory(long id,CategoryDTO categoryDTO){
        Category category = categoryRepositry.findById(id).orElse(null);
        if(category != null) {
            category.setName(categoryDTO.getName());
            category.setDescription(categoryDTO.getName());
            return mapToDTO(category);
        }
        else
            return null;

    }
    public boolean deleteCategory(long id){
        Category category = categoryRepositry.findById(id).orElse(null);
        if(category!=null) {
            categoryRepositry.deleteById(id);
            return true;
        }
        else
            return false;
    }

    private CategoryDTO mapToDTO(Category category) {
        return GenericObjectMapper.map(category,CategoryDTO.class);
    }
}
