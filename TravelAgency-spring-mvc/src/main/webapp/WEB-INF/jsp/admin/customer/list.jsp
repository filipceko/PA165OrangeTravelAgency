<%-- 
    Document   : list
    Created on : 13.12.2018, 15:33:21
    Author     : Rajivv
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pageTemplate title="Customers">
    <jsp:attribute name="body">
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Email</th>
                    <th>Phone Number</th>
                    <th>Passport Number</th>
                    <th>Date Of Birth</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${customers}" var="customer">
                    <tr>
                        <td>${customer.id}</td>
                        <td><my:a href="/admin/customer/detail/${customer.id}" class="text">
                                <c:out value="${customer.name}"/>
                            </my:a></td>
                        <td><c:out value="${customer.surname}"/></td>
                        <td><c:out value="${customer.email}"/></td>
                        <td><c:out value="${customer.phoneNumber}"/></td>
                        <td><c:out value="${customer.passportNumber}"/></td>
                        <td><c:out value="${customer.dateOfBirth}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


    </jsp:attribute>
</my:pageTemplate>

