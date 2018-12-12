<%--
  Created by IntelliJ IDEA.
  User: Simi
  Date: 11.12.2018
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Editing excursion #${excursion.id}">
<jsp:attribute name="body">
    <form:form method="POST" action="/travel.agency/admin/excursion/editExcursion/${excursion.id}/${excursion.trip.id}" modelAttribute="excursion">
         <table>
             <tr>
                 <td><form:label path="id">ID: </form:label></td>
                 <td><form:label path="id"/></td>
             </tr>
             <tr>
                 <td><form:label path="destination"><f:message key="excursion.destination"/>: </form:label></td>
                 <td><form:input path="destination"/></td>
             </tr>
             <tr>
                 <td><form:label path="excursionDate"><f:message key="excursion.date"/>: </form:label></td>
                 <td><form:input path="excursionDate"/></td>
             </tr>
             <tr>
                 <td><form:label path="durationMinutes"><f:message key="excursion.duration"/>: </form:label></td>
                 <td><form:input path="durationMinutes"/></td>
             </tr>
             <tr>
                 <td><form:label path="price"><f:message key="excursion.price"/>: </form:label></td>
                 <td><form:input path="price"/></td>
             </tr>
             <tr>
                 <td><form:label path="description"><f:message key="excursion.description"/>: </form:label></td>
                 <td><form:input path="description"/></td>
             </tr>
             <tr>
                 <td><input type="submit" value="Update"/></td>
             </tr>
         </table>
    </form:form>
</jsp:attribute>
</my:pageTemplate>
