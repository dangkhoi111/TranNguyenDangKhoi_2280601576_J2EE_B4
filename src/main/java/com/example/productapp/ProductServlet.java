package com.example.productapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet("/products")
@MultipartConfig
public class ProductServlet extends HttpServlet {
    private Map<Integer, Product> store = new HashMap<>();
    private int nextId = 1;
    private Validator validator;

    @Override
    public void init() throws ServletException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("create".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/form.jsp").forward(req, resp);
        } else {
            req.setAttribute("products", store.values());
            req.getRequestDispatcher("/WEB-INF/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product p = new Product();
        p.setName(req.getParameter("name"));
        String priceStr = req.getParameter("price");
        if (priceStr != null && !priceStr.isEmpty()) {
            try {
                p.setPrice(Integer.parseInt(priceStr));
            } catch (NumberFormatException e) {
            }
        }
        p.setCategory(req.getParameter("category"));
        Part imagePart = req.getPart("image");
        if (imagePart != null && imagePart.getSize() > 0) {
            String submitted = imagePart.getSubmittedFileName();
            p.setImage(submitted);
            // skipping actual upload for brevity
        }

        Set<ConstraintViolation<Product>> violations = validator.validate(p);
        if (!violations.isEmpty()) {
            Map<String,String> errors = new HashMap<>();
            for (ConstraintViolation<Product> v: violations) {
                errors.put(v.getPropertyPath().toString(), v.getMessage());
            }
            req.setAttribute("errors", errors);
            req.setAttribute("product", p);
            req.getRequestDispatcher("/WEB-INF/form.jsp").forward(req, resp);
            return;
        }

        store.put(nextId++, p);
        resp.sendRedirect(req.getContextPath() + "/products");
    }
}