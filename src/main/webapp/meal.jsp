<%--
  Created by IntelliJ IDEA.
  User: Зинаида
  Date: 05.03.2018
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <meta charset="utf-8">
    <title>Meal data</title>
</head>
<body>
<h3><a href="meals">Back to Meals</a></h3>
<form method="post">
    <p> <input placeholder="yyyy-MM-dd HH:mm" type="text" name="date"></p>
    <p> <input placeholder="Введите описание" type="text" name="description"></p>
    <p> <input placeholder="Введите калорийность" type="number" name="calories"></p>
    <p> <input type="submit" value="Отправить"></p>
</form>
</body>
</html>
