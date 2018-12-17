<%--
  Created by IntelliJ IDEA.
  User: filip
  Date: 06-Dec-18
  Time: 5:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Customer Detail">
<jsp:attribute name="body">
    <h1><f:message key="customer.name"/> #${customer.name}</h1>
    <f:message key="customer.surname"/>: ${customer.surname} <br/>
    <f:message key="customer.email"/>: ${customer.email} <br/>
    <f:message key="customer.dateOfBirth"/>: ${customer.dateOfBirth} <br/>
    <f:message key="customer.passportNumber"/>: ${customer.passportNumber} <br/>
    <f:message key="customer.phoneNumber"/>: ${customer.phoneNumber} <br/>

</jsp:attribute>
</my:pageTemplate>