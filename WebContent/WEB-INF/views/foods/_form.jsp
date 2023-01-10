<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
        ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label for="${AttributeConst.FOD_CODE.getValue()}">食品コード</label><br />
<input type="text" name="${AttributeConst.FOD_CODE.getValue()}" id="${AttributeConst.FOD_CODE.getValue()}" value="${food.name}" />
<br /><br />

<label for="${AttributeConst.FOD_NAME.getValue()}">食べ物</label><br />
<input type="text" name="${AttributeConst.FOD_NAME.getValue()}" id="${AttributeConst.FOD_NAME.getValue()}" value="${food.name}" />
<br /><br />

<label for="${AttributeConst.FOD_AMOUNT.getValue()}">量</label><br />
<input type="text" name="${AttributeConst.FOD_AMOUNT.getValue()}" id="${AttributeConst.FOD_AMOUNT.getValue()}" value="${food.amount}" />
<br /><br />

<label for="${AttributeConst.FOD_CAL.getValue()}">カロリー</label><br />
<input type="text" name="${AttributeConst.FOD_CAL.getValue()}" id="${AttributeConst.FOD_CAL.getValue()}" value="${food.cal}" />
<br /><br />
<input type="hidden" name="${AttributeConst.FOD_ID.getValue()}" value="${food.id}"/>
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">決定</button>
