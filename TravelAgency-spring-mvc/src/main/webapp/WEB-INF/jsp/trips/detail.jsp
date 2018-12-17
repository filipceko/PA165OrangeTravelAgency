<%-- 
    Document   : login
    Created on : 14.12.2018, 18:44:07
    Author     : Rajivv
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Detail">
    <jsp:attribute name="body">
        <div class="row">
            <div class="col-xs-6">
                <h3><f:message key="trip.destination"/>: ${trip.destination} </h3>
                <h3><f:message key="trip.from"/>: ${trip.fromDate} </h3>
                <h3><f:message key="trip.to"/>: ${trip.toDate} </h3>
                <h3><f:message key="trip.capacity"/>: ${trip.capacity} </h3>
                <h3><f:message key="trip.price"/>: € ${trip.price} </h3>
            </div>
            <div class="col-xs-6">
                <table class="table">
                    <h3><f:message key="trip.excursions"/>: </h3><br/>
                    <thead>
                        <tr>
                            <th>Destination</th>
                            <th>Description</th>
                            <th>Date</th>
                            <th>Duration</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${trip.excursions}" var="excursion" varStatus="ic">
                            <tr>
                                <td>${ic.count}. <c:out value="${excursion.destination}"/></td>
                                <td><c:out value="${excursion.description}"/></td>
                                <td><c:out value="${excursion.excursionDate}"/></td>
                                <td><c:out value="${excursion.excursionDuration}"/></td>
                                <td><c:out value="${excursion.price}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </jsp:attribute>
</my:pageTemplate>

