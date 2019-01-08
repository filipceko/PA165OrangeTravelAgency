<%--
  Created by IntelliJ IDEA.
  User: filip
  Date: 03-Dec-18
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate>
    <jsp:attribute name="title">Welcome!</jsp:attribute>
    <jsp:attribute name="body">
    <div class="jumbotron">
        <h1>Orange Travel Agency</h1>
        <p class="lead">The best place you can be!</p>
    </div>
    <form:form action="/travel.agency/trips/forMoney" method="POST">
        <input type="text" id="money" name="money" value="<f:message key="trip.filter.baseValue"/>"/>
        <button type="submit" class="btn btn-primary">
            <span class="glyphicon glyphicon-euro" aria-hidden="true"></span>
            <f:message key="common.filter"/>
        </button>
    </form:form>
    <div class="container">
        <!-- Example row of columns -->
        <c:forEach items="${trips}" var="trip">
            <div class="col-md-4">s
                <h2><c:out value="${trip.destination}"/></h2>
                <p>Price : € <c:out value="${trip.price}"/></p>
                <p>Date : <c:out value="${trip.fromDate}"/></p>
                <p>Available : <c:out value="${trip.capacity}" /></p>
                <p><c:out value="Trip to ${trip.destination}. The best you can get, for this price there is!"/></p>
                <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/trips/detail/${trip.id}" role="button">View details &raquo;</a></p>
            </div>
         </c:forEach>
        <br/>
    </div> <!-- /container -->
</jsp:attribute>
</my:pageTemplate>

