<%-- 
    Document   : login
    Created on : 14.12.2018, 18:44:07
    Author     : Rajivv
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="customer.login"/>
<my:pageTemplate title="${title}">
    <jsp:attribute name="body">
        <body>
            <div class="container">
                <form method="POST" action="${contextPath}/login" class="form-signin">
                    <h2 class="form-heading">Log in</h2>
                    <div class="form-group ${error != null ? 'has-error' : ''}">
                        <span>${message}</span>
                        <input name="username" type="text" class="form-control" placeholder="Username"
                               autofocus="true"/>
                        <input name="password" type="password" class="form-control" placeholder="Password"/>
                        <span>${error}</span>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
                        <h4 class="text-center"><a href="${contextPath}/customer/registration">Register New Account</a></h4>
                    </div>
                </form>
            </div>
        </body>
    </jsp:attribute>
</my:pageTemplate>

