<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_USE.getValue()}" />
<c:set var="commTop" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
    入力内容にエラーがあります。<br />
    <c:forEach var="error" items="${errors}">
    ・<c:out value="${error}" /><br />
    </c:forEach>
    </div>
</c:if>

<label for="${AttributeConst.USE_CODE.getValue()}">会員番号</label><br />
<input type="text" name="${AttributeConst.USE_CODE.getValue()}" id="${AttributeConst.USE_CODE.getValue()}" value="${user.code}" />
<br /><br />

<label for="${AttributeConst.USE_NAME.getValue()}">氏名</label><br />
<input type="text" name="${AttributeConst.USE_NAME.getValue()}" id="${AttributeConst.USE_NAME.getValue()}" value="${user.name}" />
<br /><br />

<label for="${AttributeConst.USE_MAIL.getValue()}">メールアドレス</label><br />
<input type="text" name="${AttributeConst.USE_MAIL.getValue()}" id="${AttributeConst.USE_MAIL.getValue()}" value="${user.mail}" />
<br /><br />

<label for="${AttributeConst.USE_PASS.getValue()}">パスワード</label><br />
<input type="password" name="${AttributeConst.USE_PASS.getValue()}" id="${AttributeConst.USE_PASS.getValue()}" />
<br /><br />
<input type="hidden" name="${AttributeConst.USE_ID.getValue()}" value="${user.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">決定</button>