<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${i2 != null}">
                <h2>配布編集ページ</h2>
                <form method="POST" action="<c:url value='/places/update'/>">
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

                    <label for="mh">日吉か三田</label><br/>
                    <select name="mh">
                        <option value="1"<c:if test="${i2.can_flag ==1 }">selected</c:if>>日吉</option>
                        <option value="0"<c:if test="${i2.can_flag ==0 }">selected</c:if>>三田</option>
                    </select>
                    <br/><br/>

                    <label for="aim">配布目標数</label><br/>
                    <select name="aim">
                        <c:forEach var="i" begin="1" end="${remain+i2.aim}" step="1">
                            <option value="${i}"<c:if test="${i2.aim == i}">selected</c:if>>${i}</option>
                        </c:forEach>

                    </select>
                    <br/><br/>

                    <label for="content">コメント</label><br/>
                    <textarea name="content" rows="10" cols="50">${i2.content}</textarea>
                    <br/><br/>

                    <input type="hidden" name="_token" value="${_token}"/>
                    <button type="submit">登録</button>

                </form>



            </c:when>

            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>


        <p><a href="<c:url value='/place/index'/>">一覧に戻る</a></p>
    </c:param>
</c:import>