<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst"%>
<%@ page import="constants.AttributeConst"%>

<c:set var="action" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="actUse" value="${ForwardConst.ACT_USE.getValue()}" />
<c:set var="command" value="${ForwardConst.CMD_LOGIN.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${loginError}">
            <div id="flush_error">会員番号かパスワードが間違っています。</div>
        </c:if>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>ログイン</h2>
        <form method="POST"
            action="<c:url value='/?action=${action}&command=${command}' />">
            <label for="${AttributeConst.USE_CODE.getValue()}">会員番号</label><br />
            <input type="text" name="${AttributeConst.USE_CODE.getValue()}"
                id="${AttributeConst.USE_CODE.getValue()}" value="${code}" /> <br />
            <br /> <label for="${AttributeConst.USE_PASS.getValue()}">パスワード</label><br />
            <input type="password" name="${AttributeConst.USE_PASS.getValue()}"
                id="${AttributeConst.USE_PASS.getValue()}" /> <br />
            <br /> <input type="hidden"
                name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <button type="submit">ログイン</button>
        </form>

        <form method="POST"
            action="<c:url value='/?action=${actUse}&command=${commNew}' />">
                        <button type="submit">新規会員登録はこちら</button>
            </form>
    </c:param>
</c:import>