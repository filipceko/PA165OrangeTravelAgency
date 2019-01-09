<%-- 
    Document   : reservationView
    Created on : 8.1.2019, 17:48:49
    Author     : Rajivv
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Reservation Detail">
    <jsp:attribute name="body">

        <h3><f:message key="reservation.customer.detail"/>: <br/></h3>
        <f:message key="customer.name"/>: ${customer.name} <br/>
        <f:message key="customer.surname"/>: ${customer.surname} <br/>
        <f:message key="customer.email"/>: ${customer.email} <br/>

        <h3><f:message key="reservation.detail"/>: <br/></h3>
            <c:forEach items="${reservations}" var="reservation">
            <tr>
                <td><f:message key="reservation.destination"/>: ${reservation.trip.destination}</td><br/>
            <td><f:message key="reservation.reserveDate"/>: ${reservation.reserveDate}</td><br/>
            <td><f:message key="trip.from"/>: ${reservation.trip.fromDate}</td><br/>
            <td><f:message key="trip.to"/>: ${reservation.trip.toDate}</td><br/>
            <td><f:message key="trip.capacity"/>: ${reservation.trip.capacity}</td><br/>

            <h3><f:message key="reservation.excursion"/>: <br/></h3>
                <c:forEach items="${reservation.trip.excursions}" var="excursion">

                <td><f:message key="reservation.excursion.destination"/>: ${excursion.destination}</td><br/>

            </c:forEach> <br/>
        </tr>
    </c:forEach>

</jsp:attribute>
</my:pageTemplate>