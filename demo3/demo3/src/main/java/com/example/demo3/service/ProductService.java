package com.example.demo3.service;
import com.example.demo3.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.nio.file.*;
import java.io.IOException;
@Service
public class ProductService {

    List<Product> listProduct = new ArrayList<>();

    // upload directory configured via properties
    @Value("${image.upload-dir:static/images}")
    private String uploadDir;

    @PostConstruct
    public void init() throws IOException {
        Path dirImages = Paths.get(uploadDir);
        if (!Files.exists(dirImages)) {
            Files.createDirectories(dirImages);
        }
    }

    public List<Product> getAll() {
        return listProduct;
    }

    public Product get(int id) {
        return listProduct.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Product newProduct) {
        int maxId = listProduct.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);

        newProduct.setId(maxId + 1);
        listProduct.add(newProduct);
    }
    public void delete(int id) {
        Product find = get(id);
        if (find != null) {
            listProduct.remove(find);
        }
    }
    public void update(Product editProduct) {
        Product find = get(editProduct.getId());

        if (find != null) {
            find.setName(editProduct.getName());
            find.setPrice(editProduct.getPrice());
            find.setCategory(editProduct.getCategory());

            // Nếu có ảnh mới thì update
            if (editProduct.getImage() != null) {
                find.setImage(editProduct.getImage());
            }
        }
    }

    public void updateImage(Product newProduct, MultipartFile imageProduct) {
        String contentType = imageProduct.getContentType();
        if (contentType == null) {
            throw new IllegalArgumentException("Tệp tải lên không phải là hình ảnh!");
        }

        if (!imageProduct.isEmpty()) {
            try {
                Path dirImages = Paths.get(uploadDir);
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }

                String newFileName = UUID.randomUUID() + "-" +
                        imageProduct.getOriginalFilename();

                Path pathFileUpload = dirImages.resolve(newFileName);

                Files.copy(imageProduct.getInputStream(),
                        pathFileUpload,
                        StandardCopyOption.REPLACE_EXISTING);

                newProduct.setImage(newFileName);

            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
}