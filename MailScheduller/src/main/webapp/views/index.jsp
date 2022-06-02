<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
                <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Главная страница сервера</title>
</head>
<body>

<p>Приветствуем тебя <c:choose>
                  	<c:when test="${pageContext.session != null && sessionScope.user != null}">
                  	    ${sessionScope.user.fio}
                  	</c:when>
                  	<c:otherwise>
                  		Странник
                  	</c:otherwise>
                  </c:choose>
</p>
<p>Ты можешь:</p>
<c:choose>
    <c:when test="${sessionScope.user == null}">
        <p><input type="button" onclick="location.href='${pageContext.request.contextPath}/signUp';" value="Зарегистрироваться" /></p>
        <p><input type="button" onclick="location.href='${pageContext.request.contextPath}/signIn';" value="Войти" /></p>
    </c:when>
    <c:otherwise>
        <p><input type="button" onclick="location.href='${pageContext.request.contextPath}/chats';" value="Просмотреть свои сообщения" /></p>
        <p><input type="button" onclick="location.href='${pageContext.request.contextPath}/message';" value="Отправить сообщения" /></p>
        <p><input type="button" onclick="location.href='${pageContext.request.contextPath}/admin';" value="Админка" /></p>
        <p><input type="button" onclick="location.href='${pageContext.request.contextPath}/logout';" value="Выйти" /></p>
    </c:otherwise>
</c:choose>
</body>
</html>