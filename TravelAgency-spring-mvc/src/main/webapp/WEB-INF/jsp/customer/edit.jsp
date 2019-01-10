<%--
    Document   : edit
    Created on : 17.12.2018, 10:54:02
    Author     : Rajivv/Rithy
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Edit Account ${customer.name}">
    <jsp:attribute name="body">
        <form:form method="POST" action="/travel.agency/customer/editCustomer/" modelAttribute="customer">
            <form:input type="hidden" path="id"/>
            <table>
                <tr>
                    <td><form:label path="name"><f:message key="customer.name"/>: </form:label></td>
                    <td><form:input path="name"/></td>
                </tr>
                <tr>
                    <td><form:label path="surname"><f:message key="customer.surname"/>: </form:label></td>
                    <td><form:input path="surname"/></td>
                </tr>
                <tr>
                    <td><form:label path="email"><f:message key="customer.email"/>: </form:label></td>
                    <td><form:input path="email"/></td>
                </tr>
                <tr>
                    <td><form:label path="phoneNumber"><f:message key="customer.phoneNumber"/>: </form:label></td>
                    <td><form:input path="phoneNumber"/></td>
                </tr>
                <tr>
                    <td><form:label path="passportNumber"><f:message key="customer.passportNumber"/>: </form:label></td>
                    <td><form:input path="passportNumber"/></td>
                </tr>
                <tr>
                    <td><form:label path="dateOfBirth"><f:message key="customer.dateOfBirth"/>: </form:label></td>
                    <td><form:input path="dateOfBirth"/></td>
                </tr>
                <tr>
                    <td><form:label path="password"><f:message key="customer.password"/>: </form:label></td>
                    <td><form:input type="password" path="password"/></td>
                </tr>
                <br/>
                <tr>
                    <td><p class="text-center">
                        <button class="btn btn-primary" type="submit">Update</button>
                    </p></td>
                    <td><p class="text-center">
                           <my:a href="/customer/delete" class="btn btn-danger">
                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                        </my:a>
                    </p>
                    </td>

                </tr>
                <br/>
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
                    $("#dateOfBirth").datepicker({
                        dateFormat: "mm/dd/y",
                        changeMonth: true,
                        changeYear: true
                    });
                </script> 
            </table>
        </form:form>
    </jsp:attribute>
</my:pageTemplate>

