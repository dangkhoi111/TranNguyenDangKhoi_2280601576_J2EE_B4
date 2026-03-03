<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create Product</title>
</head>
<body>
<h2>Create Product</h2>
<form action="${pageContext.request.contextPath}/products" method="post" enctype="multipart/form-data">
    Name: <input type="text" name="name" value="${product.name}"><br/>
    <c:if test="${errors.name != null}"><span style="color:red">${errors.name}</span><br/></c:if>
    Price: <input type="text" name="price" value="${product.price}"><br/>
    <c:if test="${errors.price != null}"><span style="color:red">${errors.price}</span><br/></c:if>
    Image: <input type="file" name="image"><br/>
    <c:if test="${errors.image != null}"><span style="color:red">${errors.image}</span><br/></c:if>
    Category:
    <select name="category">
        <option value="">--choose--</option>
        <option value="Điện thoại" ${product.category == 'Điện thoại' ? 'selected' : ''}>Điện thoại</option>
        <option value="Laptop" ${product.category == 'Laptop' ? 'selected' : ''}>Laptop</option>
    </select><br/>
    <c:if test="${errors.category != null}"><span style="color:red">${errors.category}</span><br/></c:if>
    <button type="submit">Add Product</button>
</form>
</body>
</html>