package com.example.productapp;

import javax.validation.constraints.*;

public class Product {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 1, message = "Giá sản phẩm phải lớn hơn hoặc bằng 1")
    @Max(value = 9999999, message = "Giá sản phẩm phải nhỏ hơn hoặc bằng 9999999")
    private Integer price;

    @Size(max = 200, message = "Tên hình ảnh không quá 200 kí tự")
    private String image;

    @NotBlank(message = "Category is required")
    private String category;

    // getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}