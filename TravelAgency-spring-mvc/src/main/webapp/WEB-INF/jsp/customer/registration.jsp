<%-- 
    Document   : registration
    Created on : 14.12.2018, 19:14:41
    Author     : Rajivv
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="customer.registration"/>
<my:pageTemplate title="${title}">
    <jsp:attribute name="body">
        <body>
            <form:form method="post" action="${pageContext.request.contextPath}/customer/registration"
                       modelAttribute="customerCreate" cssClass="form-horizontal">

                <div class="form-group ${name_error?'has-error':''}">
                    <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
                        <div class="col-sm-10">
                        <form:input path="name" cssClass="form-control"/>
                        <form:errors path="name" cssClass="help-block"/>
                    </div>
                </div>      
                <div class="form-group ${surname_error?'has-error':''}">
                    <form:label path="surname" cssClass="col-sm-2 control-label">Surname</form:label>
                        <div class="col-sm-10">
                        <form:input path="surname" cssClass="form-control"/>
                        <form:errors path="surname" cssClass="help-block"/>
                    </div>
                </div>     
                <div class="form-group ${email_error?'has-error':''}">
                    <form:label path="email" cssClass="col-sm-2 control-label">Email</form:label>
                        <div class="col-sm-10">
                        <form:input path="email" cssClass="form-control"/>
                        <form:errors path="email" cssClass="help-block"/>
                    </div>
                </div>         

                <div class="form-group ${phoneNumber_error?'has-error':''}">
                    <form:label path="phoneNumber" cssClass="col-sm-2 control-label">Phone Number</form:label>
                        <div class="col-sm-10">
                        <form:input path="phoneNumber" cssClass="form-control"/>
                        <form:errors path="phoneNumber" cssClass="help-block"/>
                    </div>
                </div>               

                <div class="form-group ${passportNumber_error?'has-error':''}">
                    <form:label path="passportNumber" cssClass="col-sm-2 control-label">Passport Number</form:label>
                        <div class="col-sm-10">
                        <form:input path="passportNumber" cssClass="form-control"/>
                        <form:errors path="passportNumber" cssClass="help-block"/>
                    </div>
                </div>               
                <div class="form-group ${dateOfBirth_error?'has-error':''}">
                    <form:label path="dateOfBirth" cssClass="col-sm-2 control-label">Date Of Birth</form:label>
                        <div class="col-sm-10">
                        <form:input path="dateOfBirth" cssClass="form-control"/>
                        <form:errors path="dateOfBirth" cssClass="help-block"/>
                    </div>
                </div>                 
                <div class="form-group ${password_error?'has-error':''}">
                    <form:label path="password" cssClass="col-sm-2 control-label">Password</form:label>
                        <div class="col-sm-10">
                        <form:input path="password" cssClass="form-control"/>
                        <form:errors path="password" cssClass="help-block"/>
                    </div>
                </div>   
                    <p class="text-center">
                    <button class="btn btn-primary" type="submit">Submit</button>
                </p>

            </form:form>
        </body>
    </jsp:attribute>
</my:pageTemplate>

