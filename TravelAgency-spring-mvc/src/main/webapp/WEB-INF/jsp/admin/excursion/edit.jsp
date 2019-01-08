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
        <form:form method="POST" action="/travel.agency/admin/excursion/editExcursion"
                   modelAttribute="excursion">
            <table>
                <tr>
                    <td><form:label path="id">ID: </form:label></td>
                        <td>
                        <form:input path="id" readonly="true"/>
                        <form:errors path="id" cssClass="help-block"/>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="tripId">Trip id: </form:label></td>
                        <td>
                        <form:input path="tripId" readonly="true"/>
                        <form:errors path="tripId" cssClass="help-block"/>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="destination"><f:message key="excursion.destination"/>: </form:label></td>
                        <td>
                        <form:input path="destination"/>
                        <form:errors path="destination" cssClass="help-block"/>
                    </td>

                </tr>
                <tr>
                    <td><form:label path="excursionDate"><f:message key="excursion.date"/>: </form:label></td>
                        <td>
                        <form:input path="excursionDate"/>
                        <form:errors path="excursionDate" cssClass="help-block"/>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="durationMinutes"><f:message key="excursion.duration"/>: </form:label></td>
                        <td>
                        <form:input path="durationMinutes"/>
                        <form:errors path="durationMinutes" cssClass="help-block"/>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="price"><f:message key="excursion.price"/>: </form:label></td>
                        <td>
                        <form:input path="price"/>
                        <form:errors path="price" cssClass="help-block"/>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="description"><f:message key="excursion.description"/>: </form:label></td>
                        <td>
                        <form:input path="description"/>
                        <form:errors path="description" cssClass="help-block"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button class="btn btn-primary" type="submit">Edit an excursion</button>
                    </td>
                </tr>
                <link rel="stylesheet" href="resources/css/jquery-ui.css" type="text/css">
                <link rel="stylesheet" href="resources/css/custom.css" type="text/css">
                <script type="text/javascript" src="resources/jquery/jquery-ui.min.js"></script>
                <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
                <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
                <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
                <script src="webjars/jquery/1.9.1/jquery.min.js"></script>
                <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
                <script src="webjars/bootstrap-datepicker/1.0.1/js/bootstrap-datepicker.js"></script>
                <script>
                    $("#excursionDate").datepicker({
                        dateFormat: "mm/dd/y",
                        changeMonth: true,
                        changeYear: true
                    });
                </script> 
            </table>
        </form:form>
    </jsp:attribute>
</my:pageTemplate>
