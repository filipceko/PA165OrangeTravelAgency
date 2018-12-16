<%-- 
    Document   : detail
    Created on : 13.12.2018, 15:33:03
    Author     : Rajivv
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Customer #${customer.id} Detail">
    <jsp:attribute name="body">
        <h3> <f:message key="customer.name"/>: ${customer.name} <br/></h3>
        <h3><f:message key="customer.surname"/>: ${customer.surname} <br/></h3>
        <h3><f:message key="customer.email"/>: ${customer.email} <br/></h3>
        <h3><f:message key="customer.phoneNumber"/>: ${customer.phoneNumber} <br/></h3>
        <h3><f:message key="customer.passportNumber"/>: ${customer.passportNumber} <br/></h3>
        <h3><f:message key="customer.dateOfBirth"/>: ${customer.dateOfBirth} <br/></h3>
    </jsp:attribute>
</my:pageTemplate>