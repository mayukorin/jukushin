<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
<c:param name="content">
<form method="POST" action="<c:url value='/years/create'/>">
<h2>新規年度 登録ページ</h2>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="annual_year">年度</label><br/>
<input type="number" name="annual_year" value="${year}"/>
<br/><br/>

<label for="name">氏名</label><br/>
<c:out value="${sessionScope.login_company.name}"/>
<br/><br/>

<input type="hidden" name="_token" value="${_token}"/>
<button type="submit">登録</button>
</form>

<p><a href="<c:url value='/index.html'/>">一覧に戻る</a></p>
</c:param>
</c:import>
