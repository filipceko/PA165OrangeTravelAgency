<%--
  Created by IntelliJ IDEA.
  User: filip
  Date: 06-Dec-18
  Time: 5:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Trip #${trip.id} Detail">
<jsp:attribute name="body">
    <f:message key="trip.destination"/>: ${trip.destination} <br/>
    <f:message key="trip.from"/>: ${trip.fromDate} <br/>
    <f:message key="trip.to"/>: ${trip.toDate} <br/>
    <f:message key="trip.capacity"/>: ${trip.capacity} <br/>
    <f:message key="trip.price"/>: ${trip.price} <br/>
    <f:message key="trip.excursions"/>: <c:forEach items="${trip.excursions}" var="excursion">
        ${excursion.destination},
        <!-- TODO link to excursion page -->
    </c:forEach> <br/>
    <f:message key="trip.reservations"/>: <c:forEach items="${trip.reservations}" var="reservation">
            ${reservation.customer.name} ${reservation.customer.surname},
            <!-- TODO link to customer page -->
        </c:forEach> <br/>
    <my:a href="/admin/trip/edit/${trip.id}" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        <f:message key="common.edit"/>
    </my:a>
    <my:a href="/admin/trip/delete/${trip.id}" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        <f:message key="common.delete"/>
    </my:a>
</jsp:attribute>
</my:pageTemplate>