
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="wb" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
aaa
<h1>name(key:name)----> ${name}</h1>
<h1>name(key:string)---->${string}</h1>
<h1>name(key:currentUser)---->${currentUser.userName}</h1>
<h1>name(key:user)---->${user.userName}</h1>
<table>

    <wb:forEach items="${data}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.userCode}</td>
            <td>${user.userName}</td>
            <td>${user.gender}</td>
            <td>
                    <fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>
            </td>
        </tr>
    </wb:forEach>
</table>
</body>
</html>
