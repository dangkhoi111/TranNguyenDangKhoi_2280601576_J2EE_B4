package com.example.demo3.service;

import com.example.demo3.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    List<Category> listCategory = new ArrayList<>();

    public CategoryService() {
        listCategory.add(new Category(1, "Điện thoại"));
        listCategory.add(new Category(2, "Laptop"));
        listCategory.add(new Category(3, "Phụ kiện"));
    }

    public List<Category> getAll() {
        return listCategory;
    }

    public Category get(int id) {
        return listCategory.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}