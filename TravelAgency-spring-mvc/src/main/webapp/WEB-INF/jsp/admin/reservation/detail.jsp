<%--
  Created by IntelliJ IDEA.
  User: Rithy
  Date: 07-Dec-18
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Reservation Detail">
<jsp:attribute name="body">
    <h1><f:message key="reservation"/> #${reservation.id}</h1>
    <f:message key="reservation.destination"/>: ${reservation.trip.destination} <br/>
    <f:message key="reservation.reserveDate"/>: ${reservation.reserveDate} <br/>
    <f:message key="reservation.customer"/>: ${reservation.customer.name} <br/>
    <f:message key="customer.email"/>: ${reservation.customer.email} <br/>
    <f:message key="trip.capacity"/>: ${reservation.trip.capacity} <br/>
    <f:message key="reservation.excursion.destination"/>: <c:forEach items="${reservation.excursions}" var="excursion">
        ${excursion.destination},
        <!-- TODO link to excursion page -->
    </c:forEach> <br/>
       <my:a href="/admin/reservation/delete/${reservation.id}" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        <f:message key="common.delete"/>
    </my:a>
</jsp:attribute>
</my:pageTemplate>