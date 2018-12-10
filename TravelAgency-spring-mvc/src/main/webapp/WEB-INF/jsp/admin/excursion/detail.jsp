<%--
  Created by IntelliJ IDEA.
  User: Simi
  Date: 9.12.2018
  Time: 15:42
  To change this template use File | Settings | File Templates.
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
                </my:a> <br/>
    <my:a href="/admin/excursion/edit/${excursion.id}" class="btn btn-primary">
        <f:message key="common.edit"/>
    </my:a>
    <my:a href="/admin/excursion/delete/${excursion.id}" class="btn btn-danger">
        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
    </my:a>
</jsp:attribute>
</my:pageTemplate>