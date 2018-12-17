<%--
  Created by IntelliJ IDEA.
  User: filip
  Date: 10-Dec-18
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Edditing trip #${trip.id}">
<jsp:attribute name="body">
    <form:form method="POST" action="/travel.agency/admin/trip/editTrip" modelAttribute="trip">
         <table>
             <tr>
                 <td><form:label path="id">ID: </form:label></td>
                 <td>
                     <form:input path="id" readonly="true"/>
                     <form:errors path="id" cssClass="help-block"/>
                 </td>

             </tr>
             <tr>
                 <td><form:label path="destination"><f:message key="trip.destination"/>: </form:label></td>
                 <td>
                     <form:input path="destination"/>
                     <form:errors path="destination" cssClass="help-block"/>
                 </td>
             </tr>
             <tr>
                 <td><form:label path="fromDate"><f:message key="trip.from"/>: </form:label></td>
                 <td>
                     <form:input path="fromDate"/>
                     <form:errors path="fromDate" cssClass="help-block"/>
                 </td>
             </tr>
             <tr>
                 <td><form:label path="toDate"><f:message key="trip.to"/>: </form:label></td>
                 <td>
                     <form:input path="toDate"/>
                     <form:errors path="toDate" cssClass="help-block"/>
                 </td>
             </tr>
             <tr>
                 <td><form:label path="capacity"><f:message key="trip.capacity"/>: </form:label></td>
                 <td>
                     <form:input path="capacity"/>
                     <form:errors path="capacity" cssClass="help-block"/>
                 </td>
             </tr>
             <tr>
                 <td><form:label path="price"><f:message key="trip.price"/>: </form:label></td>
                 <td>
                     <form:input path="price"/>
                     <form:errors path="price" cssClass="help-block"/>
                 </td>
             </tr>
             <tr>
                 <td><input type="submit" value="<f:message key="common.edit"/>"/></td>
             </tr>
         </table>
    </form:form>
</jsp:attribute>
</my:pageTemplate>
