<%--
  Created by IntelliJ IDEA.
  User: Simi
  Date: 9.12.2018
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Excursions">
<jsp:attribute name="body">
    <my:a href="/admin/excursion/new" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        <f:message key="excursion.create"/>
    </my:a>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Destination</th>
            <th>Date</th>
            <th>Duration</th>
            <th>Price</th>
            <th>Trip</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${excursions}" var="excursion">
            <tr>
                <td>${excursion.id}</td>
                <td><my:a href="/admin/excursion/detail/${excursion.id}" class="text">
                    <c:out value="${excursion.destination}"/>
                </my:a></td>
                <td><c:out value="${excursion.excursionDate}"/></td>
                <td><c:out value="${excursion.durationString}"/></td>
                <td><c:out value="${excursion.price}"/></td>
                <td><my:a href="/admin/trip/detail/${excursion.trip.id}" class="text">
                    <c:out value="${excursion.trip.destination}"/>
                </my:a></td>
                <td><my:a href="/admin/excursion/edit/${excursion.id}" class="btn btn-primary">
                        <f:message key="common.edit"/>
                    </my:a></td>
                <td><form method="post" action="${pageContext.request.contextPath}/admin/excursion/delete/${excursion.id}">
                    <button type="submit" class="btn btn-danger">
                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>
                    </form></td>
                <td><my:a href="/admin/excursion/delete/${excursion.id}" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        <f:message key="common.delete"/>
                    </my:a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:pageTemplate>
