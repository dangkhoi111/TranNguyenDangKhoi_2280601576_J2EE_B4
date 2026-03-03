package com.example.demo3.controller;
import com.example.demo3.model.Category;
import com.example.demo3.model.Product;
import com.example.demo3.service.CategoryService;
import com.example.demo3.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String Index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/create")
    public String Create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String Create(@Valid Product newProduct,
                         BindingResult result,
                         @RequestParam("category.id") int categoryId,
                         @RequestParam("imageProduct") MultipartFile imageProduct,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        productService.updateImage(newProduct, imageProduct); // Xử lý ảnh
        Category selectedCategory = categoryService.get(categoryId);
        newProduct.setCategory(selectedCategory);

        productService.add(newProduct);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String Edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "error/404"; // Trang lỗi tùy chỉnh
        }

        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String Edit(@Valid Product editProduct,
                       BindingResult result,
                       @RequestParam("category.id") int categoryId,
                       @RequestParam("imageProduct") MultipartFile imageProduct,
                       Model model) {

        if (result.hasErrors()) {
            model.addAttribute("product", editProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        // Lấy category
        Category selectedCategory = categoryService.get(categoryId);
        editProduct.setCategory(selectedCategory);

        // Nếu có upload ảnh mới
        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        } else {
            // Nếu không upload ảnh mới thì giữ ảnh cũ
            Product oldProduct = productService.get(editProduct.getId());
            if (oldProduct != null) {
                editProduct.setImage(oldProduct.getImage());
            }
        }

        productService.update(editProduct);

        return "redirect:/products";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }

}