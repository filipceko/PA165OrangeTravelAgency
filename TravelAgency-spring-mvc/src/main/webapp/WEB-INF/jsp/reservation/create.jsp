<%--
  Created by IntelliJ IDEA.
  User: filip
  Date: 17-Dec-18
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="e" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pageTemplate>
    <jsp:attribute name="title">Booking trip to ${trip.destination}</jsp:attribute>
    <jsp:attribute name="body">
        <form:form method="post" action="/travel.agency/reservation/createReservation" modelAttribute="reservation">
            <form:hidden path="tripId" />
            <table>
                <tr>
                    <td><f:message key="trip.chooseExcursions"/>:</td>
                    <td><form:checkboxes items="${reservation.excursions}" path="excursions" /></td>
                    <td><form:errors path="excursions" cssClass="error" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="<f:message key="trip.book"/>"/></td>
                </tr>
            </table>
        </form:form>
    </jsp:attribute>
</my:pageTemplate>
