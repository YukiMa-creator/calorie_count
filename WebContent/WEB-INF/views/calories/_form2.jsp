<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<fmt:parseDate value="${calorie.calorieDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="calDay" type="date" />
<label for="${AttributeConst.CAL_DATE.getValue()}">日付</label><br />
<input type="date" name="${AttributeConst.CAL_DATE.getValue()}" id="${AttributeConst.CAL_DATE.getValue()}" value="<fmt:formatDate value='${calDay}' pattern='yyyy-MM-dd HH:mm:ss' />" />
<br /><br />

<input type="hidden" name="${AttributeConst. CAL_ID.getValue()}" value="${calorie.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">決定</button>