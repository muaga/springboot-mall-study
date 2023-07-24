<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">MALL</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mynavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mynavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/">상품목록</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/product">상품등록</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/seller/list">판매자목록</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/seller">판매자등록</a>
                </li>
            </ul>
            <form class="d-flex">
                <input class="form-control me-2" type="text" placeholder="Search">
                <button class="btn btn-primary" type="button">Search</button>
            </form>
        </div>
    </div>
</nav>
<div class="container mt-3">
    <%--select 결과가 나올, table이 필요하다.--%>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>판매자번호</th>
            <th>판매자명</th>
            <th>판매 중인 상품</th>
            <th>상품가격</th>
            <th>상품재고</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="sp" items="${sellerToProductList}">
            <%-- ${} 안에 들어가는 것은 name명 쓰기--%>
            <tr>
                <td>${sp.seller.id}</td>
                <td>${sp.seller.name}</td>
                <td>${sp.name}</td>
                <td>${sp.price}</td>
                <td>${sp.qty}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>