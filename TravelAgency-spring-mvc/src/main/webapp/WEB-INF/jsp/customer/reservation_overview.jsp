<%-- 
    Document   : reservation_overview
    Created on : 17.12.2018, 10:55:05
    Author     : Rajivv
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Reservation Overview">
    <jsp:attribute name="body">
        <table class="table">
            <thead>
                <tr>
                    <th>Reservation Date</th>
                    <th>Destination</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Excursion Destination</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${customer.reservations}" var="reservation">
                    <tr>
                        <td><c:out value="${reservation.reserveDate}"/></td>
                        <td><c:out value="${reservation.trip.destination}"/></td>
                        <td><c:out value="${reservation.trip.fromDate}"/></td>
                        <td><c:out value="${reservation.trip.toDate}"/></td>
                        <td><c:out value="${reservation.excursion.destination}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pageTemplate>
