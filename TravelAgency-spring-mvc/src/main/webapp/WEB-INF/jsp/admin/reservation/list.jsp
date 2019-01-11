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
        <input type="text" id="fromDate" name="fromDate" value="<f:message key="common.pickDate"/>"/>
        <input type="text" id="toDate" name="toDate" value="<f:message key="common.pickDate"/>"/>
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
                <td><my:a href="/admin/reservation/detail/${reservation.id}" class="text">
                    <c:out value="${reservation.id}"/>
                </my:a></td>
                <td><my:a href="/admin/trip/detail/${reservation.trip.id}" class="text">
                    <c:out value="${reservation.trip.destination}"/>
                </my:a></td>
                <td><c:out value="${reservation.reserveDate}"/></td>
                <td><c:out value="${reservation.trip.fromDate}"/></td>
                <td><c:out value="${reservation.trip.capacity}"/></td>
                <td><my:a href="/admin/customer/detail/${reservation.customer.id}" class="text">
                    <c:out value="${reservation.customer.name}"/>
                </my:a></td>
                <td><c:out value="${reservation.customer.email}"/></td>
            </tr>
        </c:forEach>
        <link rel="stylesheet" href="resources/css/jquery-ui.css" type="text/css">
        <link rel="stylesheet" href="resources/css/custom.css" type="text/css">
        <script type="text/javascript" src="resources/jquery/jquery-ui.min.js"></script>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
        <script src="webjars/jquery/1.9.1/jquery.min.js"></script>
        <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script src="webjars/bootstrap-datepicker/1.0.1/js/bootstrap-datepicker.js"></script>
        <script>
            $("#fromDate").datepicker({
                dateFormat: "mm/dd/y",
                changeMonth: true,
                changeYear: true
            });
            $("#toDate").datepicker({
                dateFormat: "mm/dd/y",
                changeMonth: true,
                changeYear: true
            });
        </script>
        </tbody>
    </table>
</jsp:attribute>
</my:pageTemplate>
