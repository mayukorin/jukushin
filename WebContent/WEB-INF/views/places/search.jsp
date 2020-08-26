<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>${i2.place.name}のハケ状況詳細ページ</h2>
        <c:forEach var="i" items = "${i2s}">
        <table>
                <tbody>
                    <tr>
                        <th>配布年</th>
                        <td><c:out value="${i.newspaper.year}"/></td>
                    </tr>
                    <tr>
                        <th>配布場所</th>
                        <td><c:out value="${i.place.name}"/></td>
                    </tr>
                    <tr>
                        <th>配布（予定）日</th>
                        <td><c:out value="${i.date}"/></td>
                    </tr>
                    <tr>
                        <th>配布号</th>
                        <td><c:out value="${i.newspaper.month}"/></td>
                    </tr>
                    <tr>
                        <th>
                        <c:choose>
                        <c:when test="${decision == 0 }">
                            目標数
                        </c:when>
                        <c:otherwise>
                            現在の目標数
                        </c:otherwise>
                        </c:choose>
                        </th>
                        <td><c:out value="${i.aim}"/></td>
                    </tr>
                    <tr>
                        <th>実際</th>
                        <td><c:out value="${i.act}"/></td>
                    </tr>
                    <tr>
                        <th>ハケ率</th>
                        <td>
                            <c:if test="${i.aim != 0 }">
                                <c:out value="${i.act/i.aim}"/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>登録日時</th>
                        <td><fmt:formatDate value="${i.created_at}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                    <tr>
                        <th>更新日時</th>
                        <td><fmt:formatDate value="${i.created_at}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                    <tr>
                        <th>コメント</th>
                        <td>
                            <pre><c:out value="${i.content}"/></pre>
                        </td>
                    </tr>

                </tbody>
                </table>
                <br/><br/>
        </c:forEach>
        <p><a href ="<c:url value='/places/show?id=${i2.id}'/>">${i2.newspaper.year}年${i2.place.name}のハケ状況詳細ページに戻る</a>



    </c:param>
</c:import>