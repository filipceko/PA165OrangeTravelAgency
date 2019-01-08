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
            <form:form method="post" action="${pageContext.request.contextPath}/customer/create"
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
                        <form:input path="dateOfBirth" type="text" class="form-control" required="required"/>
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
        </body>
    </jsp:attribute>
</my:pageTemplate>