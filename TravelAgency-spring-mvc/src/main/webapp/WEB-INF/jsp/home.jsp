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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate>
    <jsp:attribute name="title">Welcome!</jsp:attribute>
    <jsp:attribute name="body">

    <div class="jumbotron">
        <h1>Orange Travel Agency</h1>
        <p class="lead">The best place you can be!</p>
    </div>

    <div class="container">
        <!-- Example row of columns -->
        <c:forEach items="${trips}" var="trip">
            <div class="col-md-4">
                <h2><c:out value="${trip.destination}"/></h2>
                <p>Price : € <c:out value="${trip.price}"/></p>
                <p>Date : <c:out value="${trip.fromDate}"/></p>
                <p>Available : <c:out value="${trip.capacity}" /></p>
                <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
                <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
            </div>
         </c:forEach>
        <hr>

    </div> <!-- /container -->


</jsp:attribute>
</my:pageTemplate>

