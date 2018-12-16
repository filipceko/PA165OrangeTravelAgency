<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="trips.show.title"/>
<my:pageTemplate title="${title}">
    <jsp:attribute name="body">
        <div class="row">
            <c:forEach items="${trips}" var="trip" varStatus="ic">
                <div class="col-xs-12"><!-- bootstrap responsive grid -->
                    <a href="${pageContext.request.contextPath}/trips/detail/${trip.id}">
                        <div class="thumbnail">
                            <h2>${ic.count}. <c:out value="${trip.destination}"/></h2>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </jsp:attribute>
</my:pageTemplate>

