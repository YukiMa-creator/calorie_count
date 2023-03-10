<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actUse" value="${ForwardConst.ACT_USE.getValue()}" />
<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">

        <h2>id : ${login_user.id} の会員情報 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>会員番号</th>
                    <td><c:out value="${login_user.code}" /></td>
                </tr>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${login_user.name}" /></td>
                </tr>
                <tr>
                    <th>メールアドレス</th>
                    <td><c:out value="${login_user.mail}" /></td>
                </tr>
                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${login_user.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>更新日時</th>
                    <fmt:parseDate value="${login_user.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

        <p>
        <a href="<c:url value='?action=${actUse}&command=${commEdit}&id=${login_user.id}' />">会員情報を修正する</a>
        </p>
        <p>
        <a href="<c:url value='?action=${actTop}&command=${commIdx}' />">トップページに戻る</a>
        </p>

    </c:param>
</c:import>
