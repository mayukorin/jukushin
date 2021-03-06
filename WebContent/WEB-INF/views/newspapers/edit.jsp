<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>${i1.newspaper.month}月の発行部数編集ページ</h2>
        <c:if test="${errors != null }">
                <div id="flush_error">
                    入力内容にエラーがあります。<br/>
                    <c:forEach var="error" items="${errors}">
                        <c:out value="${error}"/><br/>
                    </c:forEach>
                </div>
            </c:if>

        <form method="POST" action="<c:url value='/newspaper/update'/>">
        <label for="month">月</label><br/>
        <c:out value="${i1.newspaper.month}"/>
        <br/><br/>

        <label for="volumn">発行部数</label><br/>
        <input type="number" name="volumn" value="${i1.volumn }">
        <br/><br/>

        <input type="hidden" name="_token" value="${_token}"/>
        <button type="submit">登録</button>

        </form>
        <p><a href="<c:url value='/newspapers/index'/>">${sessionScope.year}年度月新聞ハケ率一覧に戻る</a></p>

    </c:param>
</c:import>