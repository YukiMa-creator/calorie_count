<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actCal" value="${ForwardConst.ACT_CAL.getValue()}" />
<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>カロリー 編集ページ</h2>
        <form method="POST" action="<c:url value='?action=${actCal}&command=${commUpd}' />">
            <c:import url="_form2.jsp" />
        </form>

        <p>
            <a href="<c:url value='?action=Top&command=index' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>