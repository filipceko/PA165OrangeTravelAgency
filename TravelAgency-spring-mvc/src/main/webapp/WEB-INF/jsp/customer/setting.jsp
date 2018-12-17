<%-- 
    Document   : edit
    Created on : 17.12.2018, 10:54:02
    Author     : Rajivv
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Setting Account ${customer.name}">
    <jsp:attribute name="body">
        <form:form method="POST" action="/travel.agency/customer/settingAccount/${customer.id}" modelAttribute="customer">
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
                    <td><form:input path="password"/></td>
                </tr>
                <br/> 
                <br/>
                <tr>
                    <td><p class="text-center">
                            <button class="btn btn-primary" type="submit">Update</button>
                        </p></td>
                    <td><p class="text-center">
                            <button class="btn btn-danger" type="submit">Delete</button>
                        </p></td>

                </tr>
                <br/>
            </table>
        </form:form>
    </jsp:attribute>
</my:pageTemplate>

