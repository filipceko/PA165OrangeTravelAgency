<%--
  Author: Simona Raucinova
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<my:pageTemplate title="New excursion">
    <jsp:attribute name="body">
        <form:form method="post" action="${pageContext.request.contextPath}/admin/excursion/create"
                   modelAttribute="excursionCreate" cssClass="form-horizontal">
            <div class="form-group">
                <form:label path="tripId" cssClass="col-sm-2 control-label">Trip</form:label>
                    <div class="col-sm-10">
                    <form:select path="tripId" cssClass="form-control">
                        <c:forEach items="${trips}" var="trip">
                            <form:option value="${trip.id}">TO ${trip.destination} FROM ${trip.fromDate} TO ${trip.toDate}</form:option>
                        </c:forEach>
                    </form:select>
                    <p class="help-block"><form:errors path="tripId" cssClass="error"/></p>
                </div>
            </div>
            <div class="form-group ${destination_error?'has-error':''}">
                <form:label path="destination" cssClass="col-sm-2 control-label">Destination</form:label>
                    <div class="col-sm-10">
                    <form:input path="destination" cssClass="form-control"/>
                    <form:errors path="destination" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${date_error?'has-error':''}">
                <form:label path="excursionDate" cssClass="col-sm-2 control-label">Excursion date</form:label>
                    <div class="col-sm-10">
                    <form:input path="excursionDate" cssClass="form-control"/>
                    <form:errors path="excursionDate" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${duration_error?'has-error':''}">
                <form:label path="durationMinutes" cssClass="col-sm-2 control-label">Excursion duration in minutes</form:label>
                    <div class="col-sm-10">
                    <form:input path="durationMinutes" cssClass="form-control"/>
                    <form:errors path="durationMinutes" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${price_error?'has-error':''}" >
                <form:label path="price" cssClass="col-sm-2 control-label">Price</form:label>
                    <div class="col-sm-10">
                    <form:input path="price" cssClass="form-control"/>
                    <form:errors path="price" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${description_error?'has-error':''}">
                <form:label path="description" cssClass="col-sm-2 control-label">Description</form:label>
                    <div class="col-sm-10">
                    <form:textarea cols="80" rows="20" path="description" cssClass="form-control"/>
                    <form:errors path="description" cssClass="help-block"/>
                </div>
            </div>

            <button class="btn btn-primary" type="submit">Create an excursion</button>

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
        </form:form>
    </jsp:attribute>
</my:pageTemplate>