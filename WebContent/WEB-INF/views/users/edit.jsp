<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst"%>
<%@ page import="constants.AttributeConst"%>

<c:set var="action" value="${ForwardConst.ACT_USE.getValue()}" />
<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">

        <h2>id : ${user.id} の会員情報 編集ページ</h2>
        <p>（パスワードは変更する場合のみ入力してください。）</p>
        <form method="POST"
            action="<c:url value='?action=${action}&command=${commUpd}' />">
            <c:import url="_form.jsp" />
        </form>

         <p>
            <a href="#" onclick="confirmDestroy();">退会はこちら</a>
        </p>
        <form method="POST"
            action="<c:url value='?action=${action}&command=${commDel}' />">
            <input type="hidden" name="${AttributeConst.USE_ID.getValue()}" value="${user.id}" />
        </form>
        <script>
            function confirmDestroy() {
                if (confirm("本当に退会してよろしいですか？")) {
                    document.forms[1].submit();
                }
            }
        </script>
        <p>
            <a href="<c:url value='?action=${actTop}&command=${commIdx}' />">トップページに戻る</a>
        </p>
    </c:param>
</c:import>