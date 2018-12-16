<%--
  Author: Simona Raucinova
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Editing excursion #${excursion.id}">
<jsp:attribute name="body">
    <form:form method="POST" action="/travel.agency/admin/excursion/editExcursion/${excursion.id}/${excursion.tripId}"
               modelAttribute="excursion">
         <table>
             <tr>
                 <td><form:label path="id">ID: ${excursion.id}</form:label></td>
                 <td><form:input path="id" hidden="true"/>
                 <form:errors path="id" cssClass="help-block"/>
             </tr>
             <tr>
                 <td><form:label path="tripId">Trip id: ${excursion.tripId}</form:label></td>
                 <form:errors path="tripId" cssClass="help-block"/>
             </tr>
             <tr>
                 <td><form:label path="destination"><f:message key="excursion.destination"/>: </form:label></td>
                 <td><form:input path="destination"/>
                 <form:errors path="destination" cssClass="help-block"/></td>
             </tr>
             <tr>
                 <td><form:label path="excursionDate"><f:message key="excursion.date"/>: </form:label></td>
                 <td><form:input path="excursionDate"/>
                 <form:errors path="excursionDate" cssClass="help-block"/></td>
             </tr>
             <tr>
                 <td><form:label path="durationMinutes"><f:message key="excursion.duration"/>: </form:label></td>
                 <td><form:input path="durationMinutes"/>
                 <form:errors path="durationMinutes" cssClass="help-block"/></td>
             </tr>
             <tr>
                 <td><form:label path="price"><f:message key="excursion.price"/>: </form:label></td>
                 <td><form:input path="price"/>
                 <form:errors path="price" cssClass="help-block"/></td>
             </tr>
             <tr>
                 <td><form:label path="description"><f:message key="excursion.description"/>: </form:label></td>
                 <td><form:input path="description"/>
                 <form:errors path="description" cssClass="help-block"/></td>
             </tr>
             <tr>
                 <td><button class="btn btn-primary" type="submit">Edit an excursion</button></td>
             </tr>
         </table>
    </form:form>
</jsp:attribute>
</my:pageTemplate>
