<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
                    <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Главная страница входа</title>
    </head>
    <body>
<c:choose>
    <c:when test="${sessionScope.user != null}">
        <p>Вы уже вошли, может хотите выйти?</p>
        <p><input type="button" onclick="location.href='${pageContext.request.contextPath}/logout';" value="Выйти" /></p>
    </c:when>
    <c:otherwise>
    <c:choose>
        <c:when test="${requestScope.error}">
        <p style="color:red;">${requestScope.message}</p>
        </c:when>
        <c:otherwise>
        <p>Заходи быстрее!</p>
        </c:otherwise>
    </c:choose>
        <form action="${pageContext.request.contextPath}/signIn" method="POST">
            <table>
                <tbody>
                    <tr>
                        <td>Login:</td>
                        <td>
                            <input type="text" name="login">
                        </td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td>
                            <input type="password" name="password">
                        </td>
                    </tr>
                </tbody>
            </table>
            <p><input type="submit" value="Login" /></p>
        </form>
    </c:otherwise>
</c:choose>
    </body>
</html>