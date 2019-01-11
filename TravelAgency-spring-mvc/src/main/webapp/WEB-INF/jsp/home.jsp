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
    <jsp:attribute name="title"><f:message key="home.welcome"/></jsp:attribute>
    <jsp:attribute name="body">
    <div class="jumbotron">
        <h1>Orange Travel Agency</h1>
        <p class="lead"><f:message key="home.slogan"/></p>
    </div>
    <form:form action="/travel.agency/trips/forMoney" method="POST">
        <input type="text" id="money" name="money" value="<f:message key="home.moneyDefault"/>"/>
        <button type="submit" class="btn btn-primary">
            <span class="glyphicon glyphicon-euro" aria-hidden="true"></span>
            <f:message key="home.showTrips"/>
        </button>
    </form:form>
    <div class="container">
        <!-- Example row of columns -->
        <c:forEach items="${trips}" var="trip">
            <div class="col-md-4">
                <h2><c:out value="${trip.destination}"/></h2>
                <p><f:message key="trip.price"/> : € <c:out value="${trip.price}"/></p>
                <p><f:message key="trip.from"/> : <c:out value="${trip.fromDate}"/></p>
                <p><f:message key="trip.to"/> : <c:out value="${trip.toDate}"/></p>
                <p><f:message key="trip.capacity"/>: <c:out value="${trip.capacity}" /></p>
                <p><f:message key="home.bait"><f:param value="${trip.destination}"/></f:message> </p>
                <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/trips/detail/${trip.id}" role="button">
                    <f:message key="home.details"/>&raquo;
                </a></p>
            </div>
         </c:forEach>
        <br/>
    </div> <!-- /container -->
</jsp:attribute>
</my:pageTemplate>

