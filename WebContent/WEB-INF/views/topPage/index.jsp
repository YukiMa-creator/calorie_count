<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="actFod" value="${ForwardConst.ACT_FOD.getValue()}" />
<c:set var="actCal" value="${ForwardConst.ACT_CAL.getValue()}" />
<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />


<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />


<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>カロリーカウントシステムへようこそ</h2>
        <h3>【自分のカロリー　一覧】</h3>
        <table id="calorie_list">
            <tbody>
                <tr>
                    <th class="calorie_date">日付</th>
                    <th class="report_action">操作</th>
                </tr>
                <c:forEach var="calorie" items="${calories}" varStatus="status">
                    <fmt:parseDate value="${calorie.calorieDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="calorietDay" type="date" />
                    <tr class="row${status.count % 2}">
                        <td class="calorie_date"><fmt:formatDate value='${calorietDay}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
                        <td class="calorie_action"><a href="<c:url value='?action=${actCal}&command=${commShow}&id=${calorie.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${calories_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((calories_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actTop}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p>
            <a href="<c:url value='?action=${actFod}&command=${commIdx}' />">food</a>
        </p>
    </c:param>
</c:import>