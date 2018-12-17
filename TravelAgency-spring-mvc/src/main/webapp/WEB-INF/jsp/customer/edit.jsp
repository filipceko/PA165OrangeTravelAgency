<%--
  Created by IntelliJ IDEA.
  User: Rithy
  Date: 17-Dec-18
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Edditing trip #${customer.id}">
<jsp:attribute name="body">
    <form:form method="POST" action="/travel.agency/customer/editCustomer" modelAttribute="customer">
         <table>
             <tr>
                 <td><form:label path="id">ID: </form:label></td>
                 <td>
                     <form:input path="id" readonly="true"/>
                     <form:errors path="id" cssClass="help-block"/>
                 </td>

             </tr>
             <tr>
                 <td><form:label path="name"><f:message key="customer.name"/>: </form:label></td>
                 <td>
                     <form:input path="name"/>
                     <form:errors path="name" cssClass="help-block"/>
                 </td>
             </tr>
             <tr>
                 <td><form:label path="surname"><f:message key="customer.surname"/>: </form:label></td>
                 <td>
                     <form:input path="surname"/>
                     <form:errors path="surname" cssClass="help-block"/>
                 </td>
             </tr>
             <tr>
                 <td><form:label path="email"><f:message key="customer.email"/>: </form:label></td>
                 <td>
                     <form:input path="email"/>
                     <form:errors path="email" cssClass="help-block"/>
                 </td>
             </tr>
             <tr>
                 <td><form:label path="phoneNumber"><f:message key="customer.phoneNumber"/>: </form:label></td>
                 <td>
                     <form:input path="phoneNumber"/>
                     <form:errors path="phoneNumber" cssClass="help-block"/>
                 </td>
             </tr>
             <tr>
                 <td><form:label path="dateOfBirth"><f:message key="customer.dateOfBirth"/>: </form:label></td>
                 <td>
                     <form:input path="dateOfBirth" type="datetime"/>
                     <form:errors path="dateOfBirth" cssClass="help-block"/>
                 </td>
             </tr>
             <tr>
                 <td><form:label path="passportNumber"><f:message key="customer.passportNumber"/>: </form:label></td>
                 <td>
                     <form:input path="passportNumber"/>
                     <form:errors path="passportNumber" cssClass="help-block"/>
                 </td>
             </tr>
             <tr>
                 <td><input type="submit" value="<f:message key="common.edit"/>"/></td>
             </tr>
         </table>
    </form:form>
</jsp:attribute>
</my:pageTemplate>
