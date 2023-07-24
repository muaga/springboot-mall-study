<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <form action="/seller/update" method="post" enctype="application/x-www-form-urlencoded">
        <div class="mb-3 mt-3">
            <input type="text" class="form-control" value=${seller.id} name="id">
        </div>
        <div class="mb-3">
            <input type="text" class="form-control" value=${seller.name} name="name">
        </div>
        <div class="mb-3">
            <input type="text" class="form-control" value=${seller.email} name="email">
        </div>
        <button type="submit" class="btn btn-primary">UPDATE</button>
        <%--        input를 하게 하는 버튼으로, type이 submit이면 자동으로 폼이 제출되어 POST의 역할을 한다. --%>
    </form>
    <form action="/seller/delete" method="post">
        <%-- action의 기능을 한다 --%>
        <input type="hidden" class="form-control" value=${seller.id} name="id">
        <%-- 해당 id에 대한 값에 대해 DELETE한다. --%>
        <button type="submit" class="btn btn-danger">DELETE</button>
    </form>
</div>
</body>
</html>