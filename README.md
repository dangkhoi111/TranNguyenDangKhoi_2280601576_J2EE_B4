# TranNguyenDangKhoi_2280601576_J2EE_B4

Simple J2EE application demonstrating validation requirements from the assignment.

## Features

- Product entity with fields: name, price, image name, category
- Validation rules:
  - Name not blank
  - Image name max 200 chars
  - Price required and between 1 and 9,999,999
  - Category required ("Điện thoại" or "Laptop")
- Add products through form with server-side validation
- List products with basic display

## Structure

```
src/main/java/com/example/productapp
  Product.java
  ProductServlet.java
src/main/webapp/WEB-INF
  form.jsp
  list.jsp
pom.xml
```

## Running

1. Install a servlet container (Tomcat) and deploy the generated WAR (`mvn package`).
2. Access `http://localhost:8080/product-app/products`.

Validation messages will appear on the form when rules are violated.
