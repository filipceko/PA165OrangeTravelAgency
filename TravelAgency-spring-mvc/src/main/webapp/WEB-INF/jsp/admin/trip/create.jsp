<%--
  Created by IntelliJ IDEA.
  User: filip
  Date: 10-Dec-18
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate>
    <jsp:attribute name="title">
        <f:message key="trip.create.title"/>
    </jsp:attribute>
    <jsp:attribute name="body">
        <form:form method="POST" action="/travel.agency/admin/trip/createTrip" modelAttribute="trip">
            <table>
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
                    <td><input type="submit" value="<f:message key="common.create"/>"/></td>
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
                    $("#fromDate").datepicker({
                        dateFormat: "mm/dd/y",
                        changeMonth: true,
                        changeYear: true
                    });
                    $("#toDate").datepicker({
                        dateFormat: "mm/dd/y",
                        changeMonth: true,
                        changeYear: true
                    });
                </script> 
            </table>
        </form:form>
    </jsp:attribute>
</my:pageTemplate>
