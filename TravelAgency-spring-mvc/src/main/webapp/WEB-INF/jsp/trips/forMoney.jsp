<%--
  Created by IntelliJ IDEA.
  User: filip
  Date: 05-Jan-19
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Best options for you">
<jsp:attribute name="body">
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
                    <td>
                        <my:a href="/trips/detail/${trip.id}" class="text">
                            <c:out value="${trip.destination}"/>
                        </my:a>
                    </td>
                    <td><c:out value="${trip.fromDate}"/></td>
                    <td><c:out value="${trip.toDate}"/></td>
                    <td><c:out value="${trip.capacity}"/></td>
                    <td><c:out value="${trip.price}"/></td>
                    <td><c:out value="${trip.reservations.size()}"/></td>
                    <td><c:out value="${trip.excursions.size()}"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <c:out value="With these available excursions"/>
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
                    <td><c:out value="${excursion.destination}"/></td>
                    <td><c:out value="${excursion.excursionDate}"/></td>
                    <td><c:out value="${excursion.durationString}"/></td>
                    <td><c:out value="${excursion.price}"/></td>
                    <td><my:a href="/trips/detail/${excursion.trip.id}" class="text">
                        <c:out value="${excursion.trip.destination}"/>
                    </my:a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pageTemplate>
