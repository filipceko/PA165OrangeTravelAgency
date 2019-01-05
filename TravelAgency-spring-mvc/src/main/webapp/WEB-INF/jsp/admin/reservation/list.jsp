<%--
  Created by IntelliJ IDEA.
  User: Rithy
  Date: 07-Dec-18
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Reservation">
<jsp:attribute name="body">
    <form:form action="/travel.agency/admin/reservation/dateList" method="POST">
        <input type="text" id="fromDate" name="fromDate" value="<f:message key="trip.filter.baseValue"/>"/>
        <input type="text" id="toDate" name="toDate" value="<f:message key="trip.filter.baseValue"/>"/>
        <input type="submit" value="<f:message key="common.filter"/>"/>
    </form:form>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Destination</th>
            <th>Reservation Date</th>
            <th>Start Date</th>
            <th>Trip Available</th>
            <th>Customers</th>
            <th>Customer Email</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reservations}" var="reservation">
            <tr>
                <td>${reservation.id}</td>
                <td><my:a href="/admin/reservation/detail/${reservation.id}" class="text">
                    <c:out value="${reservation.trip.destination}"/>
                </my:a></td>
                <td><c:out value="${reservation.reserveDate}"/></td>
                <td><c:out value="${reservation.trip.fromDate}"/></td>
                <td><c:out value="${reservation.trip.capacity}"/></td>
                <td><c:out value="${reservation.customer.name}"/></td>
                <td><c:out value="${reservation.customer.email}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pageTemplate>
