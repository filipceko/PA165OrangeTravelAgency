<%--
  Author: Simona Raucinova
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Excursion Detail">
<jsp:attribute name="body">
    <h1><f:message key="excursion"/> #${excursion.id}</h1>

    <f:message key="excursion.destination"/>: ${excursion.destination} <br/>
    <f:message key="excursion.date"/>: ${excursion.excursionDate} <br/>
    <f:message key="excursion.duration"/>: ${excursion.durationString} <br/>
    <f:message key="excursion.price"/>: ${excursion.price} <br/>
    <f:message key="excursion.description"/>: ${excursion.description} <br/>
    <f:message key="trip"/>: <my:a href="/admin/trip/detail/${excursion.trip.id}" class="text">
                                <c:out value="${excursion.trip.destination}"/>
                            </my:a>
    <br/>

    <div class="row">
        <div class="col-xs-12 col-sm-6 col-md-2 col-lg-1">
            <my:a href="/admin/excursion/edit/${excursion.id}" class="btn btn-primary">
                <f:message key="common.edit"/>
            </my:a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-2 col-lg-1">
            <form method="post" action="${pageContext.request.contextPath}/admin/excursion/delete/${excursion.id}">
                <button type="submit" class="btn btn-danger">
                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                </button>
            </form>
        </div>
    </div>
</jsp:attribute>
</my:pageTemplate>