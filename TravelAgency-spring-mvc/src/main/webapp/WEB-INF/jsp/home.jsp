<%--
  Created by IntelliJ IDEA.
  User: filip
  Date: 03-Dec-18
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate>
    <jsp:attribute name="title">Welcome!</jsp:attribute>
    <jsp:attribute name="body">

    <div class="jumbotron">
        <h1>Orange Travel Agency</h1>
        <p class="lead">The best place you can be!</p>
    </div>

    <div class="row">
        <c:forEach begin="1" end="10" var="i">
            <div class="col-xs-12 col-sm-6 col-md-2 col-lg-1">
                <p>
                    <button class="btn btn-default">Button ${i}</button>
                </p>
            </div>
        </c:forEach>
    </div>

</jsp:attribute>
</my:pageTemplate>

