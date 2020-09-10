<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${i2 != null}">
                <h2>配布編集ページ</h2>
                <form method="POST" action="<c:url value='/places/update'/>">
                    <c:if test="${errors != null}">
                        <div id="flush_error">
                            入力内容にエラーがあります。<br />
                            <c:forEach var="error" items="${errors}">
                                <c:out value="${error}" />
                                <br />
                            </c:forEach>
                        </div>
                    </c:if>
                    <c:choose>
                        <c:when test="${i2.can_flag == 2 && decision == 1 }">
                            <label for="name">配布場所</label>
                            <br />
                            <c:out value="${i2.place.name}" />
                            <br />
                            <br />

                            <label for="date"> 配布予定日（カレンダーが表示されない場合は、2020-08-01のように入力してください）</label>
                            <br />
                            <c:out value="${i2.date }" />
                            <br />
                            <br />

                            <label for="mh">新聞の置き場所</label>
                            <br />
                            <c:out value="その他の場所" />
                            <br />
                            <br />

                            <label for="aim">配布目標数</label>
                            <br />
                            <c:out value="${i2.aim}" />
                            <br />
                            <br />

                            <label for="content">コメント</label>
                            <br />
                            <textarea name="content" rows="10" cols="50">${i2.content}</textarea>
                            <br />
                            <br />

                            <input type="hidden" name="_token" value="${_token}" />
                            <button type="submit">登録</button>

                        </c:when>
                        <c:otherwise>
                            <label for="name">配布場所</label>
                            <br />
                            <c:out value="${i2.place.name}" />
                            <br />
                            <br />

                            <label for="date"> 配布（予定）日</label>
                            <br />
                            <input type="date" name="date" value="${i2.date}" />
                            <br />
                            <br />

                            <label for="mh">新聞の置き場所</label>
                            <br />
                            <select name="mh">
                                <c:choose>
                                    <c:when test="${decision == 0}">
                                        <option value="1"
                                            <c:if test="${i2.can_flag == 1}">selected</c:if>>日吉</option>
                                        <option value="0"
                                            <c:if test="${i2.can_flag == 0}">selected</c:if>>三田</option>
                                        <option value="2"
                                            <c:if test="${i2.can_flag == 2}">selected</c:if>>その他の場所</option>
                                    </c:when>
                                    <c:otherwise>

                                        <option value="1" <c:if test="${flag == 1}">disabled</c:if>>日吉</option>
                                        <option value="0" <c:if test="${flag == 2}">disabled</c:if>>三田</option>


                                    </c:otherwise>
                                </c:choose>
                            </select>

                            <br />
                            <br />

                            <label for="aim">配布目標数</label>
                            <br />
                            <select name="aim">
                                <c:forEach var="i" begin="0" end="${sessionScope.remain+i2.aim}" step="1">
                                    <option value="${i}"
                                        <c:if test="${i2.aim == i}">selected</c:if>>${i}</option>
                                </c:forEach>

                            </select>
                            <br />
                            <br />

                            <label for="content">コメント</label>
                            <br />
                            <textarea name="content" rows="10" cols="50">${i2.content}</textarea>
                            <br />
                            <br />

                            <input type="hidden" name="_token" value="${_token}" />
                            <button type="submit">登録</button>
                        </c:otherwise>
                    </c:choose>



                </form>

            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>


        <p>
            <a href="<c:url value='/place/index'/>">${sessionScope.month}月のハケ状況一覧に戻る</a>
        </p>
    </c:param>
</c:import>