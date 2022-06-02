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
    <c:when test="${requestScope.error}">
        <p style="color:red;">${requestScope.message}</p>
    </c:when>
    <c:otherwise>
        <p>Пройдите регистрацию</p>
    </c:otherwise>
</c:choose>
        <form action="${pageContext.request.contextPath}/signUp" method="POST">
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
                    <tr>
                        <td>FIO:</td>
                        <td>
                            <input type="text" name="fio">
                        </td>
                    </tr>
                    <tr>
                        <td>Birthday:</td>
                        <td>
                            <input type="date" name="birthday">
                        </td>
                    </tr>
                </tbody>
            </table>
            <p><input type="submit" value="Sign Up" /></p>
        </form>
    </body>
</html>