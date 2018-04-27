<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
    <h3><spring:message code="meal.title"/></h3>

    <div class="row">
        <div class="form-group">
            <label for="startDate"><spring:message code="meal.startDate"/></label>
            <input type="text" class="form-control" id="startDate">
        </div>
        <div class="form-group">
            <label for="startTime"><spring:message code="meal.startTime"/></label>
            <input type="text" class="form-control" id="startTime">
        </div>
        <div class="form-group">
            <label for="endDate"><spring:message code="meal.endDate"/></label>
            <input type="text" class="form-control" id="endDate">
        </div>
        <div class="form-group">
            <label for="endTime"><spring:message code="meal.endTime"/></label>
            <input type="text" class="form-control" id="endTime">
        </div>
    </div>


        <%--<dl>--%>
            <%--<dt><spring:message code="meal.startDate"/>:</dt>--%>
            <%--<dd><input type="date" name="startDate" value="${param.startDate}"></dd>--%>
        <%--</dl>--%>
        <%--<dl>--%>
            <%--<dt><spring:message code="meal.endDate"/>:</dt>--%>
            <%--<dd><input type="date" name="endDate" value="${param.endDate}"></dd>--%>
        <%--</dl>--%>
        <%--<dl>--%>
            <%--<dt><spring:message code="meal.startTime"/>:</dt>--%>
            <%--<dd><input type="time" name="startTime" value="${param.startTime}"></dd>--%>
        <%--</dl>--%>
        <%--<dl>--%>
            <%--<dt><spring:message code="meal.endTime"/>:</dt>--%>
            <%--<dd><input type="time" name="endTime" value="${param.endTime}"></dd>--%>
        <%--</dl>--%>

        <button class="btn btn-primary" onclick="filter()">
            <span class="fa fa-plus"></span>
            <spring:message code="meal.filter"/>
        </button>



        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="common.add"/>
        </button>

        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                <tr data-mealExceed="${meal.exceed}">
                    <td>
                        ${fn:formatDateTime(meal.dateTime)}
                    </td>
                    <td><a><span class="fa fa-pencil"></span></a></td>
                    <td><a class="delete" id="${meal.id}"><span class="fa fa-remove"></span></a></td>
                </tr>
            </c:forEach>
        </table>
        <%--</table>--%>
    <%--<table border="1" cellpadding="8" cellspacing="0">--%>
        <%--<thead>--%>
        <%--<tr>--%>
            <%--<th><spring:message code="meal.dateTime"/></th>--%>
            <%--<th><spring:message code="meal.description"/></th>--%>
            <%--<th><spring:message code="meal.calories"/></th>--%>
            <%--<th></th>--%>
            <%--<th></th>--%>
        <%--</tr>--%>
        <%--</thead>--%>
        <%--<c:forEach items="${meals}" var="meal">--%>
            <%--<jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>--%>
            <%--<tr data-mealExceed="${meal.exceed}">--%>
                <%--<td>--%>
                        <%--&lt;%&ndash;${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<%=TimeUtil.toString(meal.getDateTime())%>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;${fn:replace(meal.dateTime, 'T', ' ')}&ndash;%&gt;--%>
                        <%--${fn:formatDateTime(meal.dateTime)}--%>
                <%--</td>--%>
                <%--<td>${meal.description}</td>--%>
                <%--<td>${meal.calories}</td>--%>
                <%--<td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>--%>
                <%--<td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>--%>
            <%--</tr>--%>
        <%--</c:forEach>--%>
    <%--</table>--%>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>