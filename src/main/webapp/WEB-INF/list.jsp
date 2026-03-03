<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<h2>Products</h2>
<a href="${pageContext.request.contextPath}/products?action=create">Create New Product</a>
<table border="1">
    <tr><th>#</th><th>Name</th><th>Price</th><th>Image</th><th>Category</th></tr>
    <c:forEach var="p" items="${products}">
        <tr>
            <td>${p.name}</td>
            <td>${p.price}</td>
            <td>${p.image}</td>
            <td>${p.category}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>