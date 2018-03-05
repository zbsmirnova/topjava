<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: black;
            margin: auto;
        }

        .tg td {
            font-family: "Times New Roman", sans-serif;
            font-size: 15px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: black;
            background-color: honeydew;
            text-align: center;

        }

        .tg th {
            font-family: "Times New Roman", sans-serif;
            font-size: 20px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: black;
            background-color: honeydew;
            text-align: center;

        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>


<table class="tg">
    <tr>
        <th width="150">DateTime</th>
        <th width="80">Description</th>
        <th width="80">Calories</th>
        <th colspan=2>Action</th>
    </tr>

    <c:forEach items="${requestScope.mealList}" var="meal">
            <tr style = "color: ${meal.exceed ? "red" : "green"}">
                <td>${localDateTimeFormat.format(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a></td>
                <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
    </c:forEach>
</table>
<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>