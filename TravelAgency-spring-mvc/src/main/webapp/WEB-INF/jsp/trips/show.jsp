<%-- 
    Document   : show
    Created on : 14.12.2018, 18:44:07
    Author     : Rajivv
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<f:message var="title" key="trips.show.title"/>
<my:pageTemplate title="${title}">
    <jsp:attribute name="body">

        <my:a href="/trips/show/future" class="btn btn-primary">
            <span class="glyphicon glyphicon-list" aria-hidden="true"></span>
            <f:message key="trip.filter.future"/>
        </my:a>
        <br/><br/><br/>
        <div class="row">
            <c:forEach items="${trips}" var="trip" varStatus="ic">
                <div class="col-xs-12"><!-- bootstrap responsive grid -->
                    <a href="${pageContext.request.contextPath}/trips/detail/${trip.id}">
                        <div class="thumbnail">
                            <h2>${ic.count}. <c:out value="${trip.destination}"/></h2>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </jsp:attribute>
</my:pageTemplate>