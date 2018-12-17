<%--
  Created by IntelliJ IDEA.
  User: filip
  Date: 06-Dec-18
  Time: 4:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Trips">
<jsp:attribute name="body">
    <my:a href="/admin/trip/new" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        <f:message key="trip.create"/>
    </my:a>
    <my:a href="/admin/trip/list/future" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        <f:message key="trip.filter.future"/>
    </my:a>
        <form:form action="/travel.agency/admin/trip/filterTrips" method="POST">
            <input type="text" id="filter" name="filter" value="<f:message key="trip.filter.baseValue"/>" />
            <input type="submit" value="<f:message key="common.filter"/>" />
        </form:form>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Destination</th>
            <th>From</th>
            <th>To</th>
            <th>Capacity</th>
            <th>Price</th>
            <th># of Reservations</th>
            <th># of Excursions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${trips}" var="trip">
            <tr>
                <td>${trip.id}</td>
                <td><my:a href="/admin/trip/detail/${trip.id}" class="text">
                    <c:out value="${trip.destination}"/>
                </my:a></td>
                <td><c:out value="${trip.fromDate}"/></td>
                <td><c:out value="${trip.toDate}"/></td>
                <td><c:out value="${trip.capacity}"/></td>
                <td><c:out value="${trip.price}"/></td>
                <td><c:out value="${trip.reservations.size()}"/></td>
                <td><c:out value="${trip.excursions.size()}"/></td>
                <td><my:a href="/admin/trip/edit/${trip.id}" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        <f:message key="common.edit"/>
                    </my:a></td>
                <td><my:a href="/admin/trip/delete/${trip.id}" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        <f:message key="common.delete"/>
                    </my:a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:pageTemplate>
