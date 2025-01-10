package com.example.proj.service;

import com.example.proj.dto.CategoryDTO;
import com.example.proj.model.Category;
import com.example.proj.model.Instructor;
import com.example.proj.model.User;
import com.example.proj.repositry.CategoryRepositry;
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
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        Category newCategory = categoryRepositry.save(category);
        return mapToDTO(newCategory);
    }

    public Optional<CategoryDTO> updateCategory(long id,CategoryDTO categoryDTO){
        Optional<Category> category = categoryRepositry.findById(id);
        if(category.isPresent()) {
            Category recieved = category.get();
            recieved.setName(categoryDTO.getName());
            recieved.setDescription(categoryDTO.getName());
            return Optional.of(mapToDTO(recieved));
        }
        else
            return Optional.empty();

    }
    public boolean deleteCategory(long id){
        Optional<Category> category = categoryRepositry.findById(id);
        if(category.isPresent()) {
            categoryRepositry.deleteById(id);
            return true;
        }
        else
            return false;
    }


    private CategoryDTO mapToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }
}
