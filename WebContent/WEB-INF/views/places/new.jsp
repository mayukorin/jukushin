<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>新規配布場所登録ページ</h2>

        <form method="POST"
            action="<c:url value='/places/create?id=${i2.can_flag}'/>">
            <c:if test="${errors != null }">
                <div id="flush_error">
                    入力内容にエラーがあります。<br />
                    <c:forEach var="error" items="${errors}">
                        <c:out value="${error}" />
                        <br />
                    </c:forEach>
                </div>
            </c:if>
            <label for="name">配布場所</label><br /> <input type="text" name="name"
                value="${i2.place.name}" /> <br />
            <br /> <label for="date"> </label><br /> <input type="date"
                name="date" /> <br />
            <br /> <label for="mh">新聞の置き場所</label><br /> <select name="mh">
                <c:choose>
                    <c:when test="${decision == 0}">
                        <option value="1">日吉</option>
                        <option value="0">三田</option>
                        <option value="2">その他の場所</option>
                    </c:when>
                    <c:otherwise>
                        <option value="1" <c:if test="${flag == 1}">disabled</c:if>>日吉</option>
                        <option value="0" <c:if test="${flag == 2}">disabled</c:if>>三田</option>
                    </c:otherwise>
                </c:choose>
            </select> <br />
            <br /> <label for="aim">配布目標数</label><br /> <select name="aim">
                <c:forEach var="i" begin="1" end="${sessionScope.remain}" step="1">
                    <option value="${i}" <c:if test="${i2.aim == i}">selected</c:if>>${i}</option>
                </c:forEach>

            </select> <br />
            <br /> <label for="content">コメント</label><br />
            <textarea name="content" rows="10" cols="50">${i2.content}</textarea>
            <br />
            <br /> <input type="hidden" name="_token" value="${_token}" />
            <button type="submit">登録</button>


        </form>
        <p>
            <a href="<c:url value='/place/index'/>">${sessionScope.month}月のハケ状況一覧に戻る</a>
        </p>
    </c:param>

</c:import>