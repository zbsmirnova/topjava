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
    <hr class="container">
    <h3><spring:message code="meal.title"/></h3>

    <div class="row">
        <div class="lineStart">
        <div class="form-group">
            <label for="startDate"><spring:message code="meal.startDate"/></label>
            <input type="date" class="form-control" id="startDate" name="startDate" value="${param.startDate}">
        </div>
        <div class="form-group">
            <label for="startTime"><spring:message code="meal.startTime"/></label>
            <input type="time" class="form-control" id="startTime" name="startTime" value="${param.startTime}">
        </div>
        </div>
        <div class="lineEnd">
        <div class="form-group">
            <label for="endDate"><spring:message code="meal.endDate"/></label>
            <input type="date" class="form-control" id="endDate" name="endDate" value="${param.endDate}">
        </div>
        <div class="form-group">
            <label for="endTime"><spring:message code="meal.endTime"/></label>
            <input type="time" class="form-control" id="endTime" name="endTime" value="${param.endTime}">
        </div>
        </div>
    </div>


    <div>
        <button class="btn btn-primary" onclick="filter(startDate, startTime, endDate, endTime)">
            <span class="fa fa-filter"></span>
            <spring:message code="meal.filter"/>
        </button>
        <button class="btn btn-primary" onclick="clearFilter()" style="margin-left: 10px">
            <span class="fa fa-delete"></span>
            <spring:message code="meal.clearFilter"/>
        </button>
    </div>


        <button class="btn btn-primary" onclick="add()" style="margin-top: 10px">
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
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a><span class="fa fa-pencil"></span></a></td>
                    <td><a class="delete" id="${meal.id}" onclick="deleteRowMeal(id)"><span class="fa fa-remove"></span></a></td>
                </tr>
            </c:forEach>
        </table>

</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="common.add"/></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                        <input type="datetime-local" class="form-control" id="dateTime" name="dateTime"
                               placeholder="<spring:message code="meal.dateTime"/>">
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message code="meal.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="meal.description"/>">
                    </div>

                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input type="number" class="form-control" id="calories" name="calories"
                               placeholder="<spring:message code="meal.calories"/>">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <span class="fa fa-close" aria-hidden="true"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="saveMeal(startDate, startTime, endDate, endTime)">
                    <span class="fa fa-check" aria-hidden="true"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>


<jsp:include page="fragments/footer.jsp"/>
</body>
</html>