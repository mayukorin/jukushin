<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${i2 != null }">
                <h2>実質配布数編集ページ</h2>
                <form method="POST" action="<c:url value='/places/updata'/>">
                <c:if test="${errors != null}">
                        <div id="flush_error">
                            入力内容にエラーがあります。<br/>
                            <c:forEach var="error" items="${errors}">
                                <c:out value="${error}"/><br/>
                            </c:forEach>
                        </div>
                    </c:if>
                    <label for="name">配布場所</label><br/>
                    <c:out value="${i2.place.name}"/>
                    <br/><br/>

                    <label for="date"> 配布日</label><br/>
                    <input type="date" name="date" value="${i2.date}"/>
                    <br/><br/>

                    <label for="mh">日吉か三田</label><br/>
                    <c:choose>
                        <c:when test="${i2.can_flag==0}">
                            <c:out value="三田"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="日吉"/>
                        </c:otherwise>
                    </c:choose>
                    <br/><br/>

                    <label for="aim">配布目標数</label><br/>
                    <c:out value="${i2.aim}"/>
                    <br/><br/>

                    <label for="act">実際配布数</label><br/>
                    <input type="number" name="act" value="${i2.act}"/>
                    <br/><br/>

                    <label for="content">コメント</label><br/>
                    <textarea name="content" rows="10" cols="50">${i2.content}</textarea>
                    <br/><br/>

                    <input type="hidden" name="_token" value="${_token}"/>
                    <button type="submit">登録</button>



                </form>

            </c:when>
        </c:choose>
        <p><a href="<c:url value="/place/index"/>">${i2.newspaper.month}月のハケ状況一覧に戻る</a>

    </c:param>
</c:import>