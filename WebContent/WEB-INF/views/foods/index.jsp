<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst"%>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actUse" value="${ForwardConst.ACT_USE.getValue()}" />
<c:set var="actFod" value="${ForwardConst.ACT_FOD.getValue()}" />

<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>カロリーカウントへようこそ</h2>
        <h3>登録したFOOD 一覧</h3>
        <table id="food_list">
            <tbody>
                <tr>
                    <th class="food_code">食品コード</th>
                    <th class="food_name">食べ物</th>
                    <th class="food_amount">量</th>
                    <th class="food_cal">カロリー</th>
                    <th class="food_create">登録日時</th>
                    <th class="food_update">更新日時</th>
                    <th class="food_action">編集</th>
                </tr>
                <c:forEach var="food" items="${foods}" varStatus="status">
                <fmt:parseDate value="${food.created_at}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="foodCreateDay" type="date" />
                <fmt:parseDate value="${food.updated_at}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="foodUpdateDay" type="date" />
                <tr class="row${status.count % 2}">
                        <td class="food_code"><c:out value="${food.code}" /></td>
                        <td class="report_name"><c:out value="${food.name}" /></td>
                        <td class="food_amount"><c:out value="${food.amount}" /></td>
                        <td class="food_cal"><c:out value="${food.cal}" /></td>
                        <td class="food_create"><fmt:formatDate value="${foodCreateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="food_update"><fmt:formatDate value="${foodUpdateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="food_action"><a href="<c:url value='?action=${actFod}&command=${commEdt}&id=${food.id}' />">編集する</a></td>
                </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
        (全 ${foods_count} 件)<br />
        <c:forEach var="i" begin="1" end="${((foods_count - 1) / maxRow) + 1}" step="1">
            <c:choose>
                <c:when test="${i == page}">
                    <c:out value="${i}" />&nbsp;
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='?action=${actFod}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                </c:otherwise>
            </c:choose>
        </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actFod}&command=${commNew}' />">食べ物の新規登録はこちら</a></p>
    </c:param>
</c:import>