<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>
<%@ page import="constants.AttributeConst"%>


<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="action" value="${ForwardConst.ACT_CAL.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>カロリー 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>日付</th>
                    <fmt:parseDate value="${calorie.calorieDate}"
                        pattern="yyyy-MM-dd'T'HH:mm:ss" var="calorieDay" type="date" />
                    <td><fmt:formatDate value='${calorieDay}'
                            pattern='yyyy-MM-dd HH:mm:ss' /></td>
                </tr>
                <tr>
                    <th>食べ物</th>
                    <td><c:out value="${calorie.food.name}" /></td>
                </tr>
                <tr>
                    <th>量</th>
                    <td><c:out value="${calorie.food.amount}" /></td>
                </tr>
                <tr>
                    <th>カロリー</th>
                    <td><c:out value="${calorie.food.kcal}" /> kcal</td>
                </tr>
            </tbody>
        </table>

        <p>
            <a href="#" onclick="confirmDestroy();">この記録を削除する</a>
        </p>
        <form method="POST" action="<c:url value='?action=${action}&command=${commDel}' />">
            <input type="hidden" name="${AttributeConst.CAL_ID.getValue()}" value="${calorie.id}" />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
        </form>

        <script>
            function confirmDestroy() {
                if (confirm("本当に削除してよろしいですか？")) {
                    document.forms[0].submit();
                }
            }
        </script>

        <p>
            <a href="<c:url value='?action=${actTop}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>