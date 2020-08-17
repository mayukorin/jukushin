<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>発行部数 登録ページ</h2>

        <form method="POST" action="<c:url value='/newspapers/create'/>">
            <c:if test="${errors != null }">
                <div id="flush_error">
                    入力内容にエラーがあります。<br/>
                    <c:forEach var="error" items="${errors}">
                        <c:out value="${error}"/><br/>
                    </c:forEach>
                </div>
            </c:if>

            <label for="month">月</label><br/>
            <c:out value="${i1.newspaper.month}"/>
            <br/><br/>

            <label for="volumn">発行部数</label><br/>
            <input type="number" name="volumn" value="${i1.volumn}"/>
            <br/><br/>

            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit">登録</button>
        </form>
    </c:param>
</c:import>