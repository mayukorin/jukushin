<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${i2 != null}">
                <h2>ハケ状況詳細ページ</h2>

                <table>
                <tbody>
                    <tr>
                        <th>配布年</th>
                        <td><c:out value="${i2.newspaper.year}"/></td>
                    </tr>
                    <tr>
                        <th>配布場所</th>
                        <td><c:out value="${i2.place.name}"/></td>
                    </tr>
                    <tr>
                        <th>配布（予定）日</th>
                        <td><c:out value="${i2.date}"/></td>
                    </tr>
                    <tr>
                        <th>配布号</th>
                        <td><c:out value="${i2.newspaper.month}"/></td>
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
                        <td><c:out value="${i2.aim}"/></td>
                    </tr>
                    <tr>
                        <th>実際</th>
                        <td><c:out value="${i2.act}"/></td>
                    </tr>
                    <tr>
                        <th>ハケ率</th>
                        <td>
                            <c:if test="${i2.aim != 0 }">
                                <c:out value="${i2.act/i2.aim}"/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>登録日時</th>
                        <td><fmt:formatDate value="${i2.created_at}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                    <tr>
                        <th>更新日時</th>
                        <td><fmt:formatDate value="${i2.created_at}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                    <tr>
                        <th>コメント</th>
                        <td>
                            <pre><c:out value="${i2.content}"/></pre>
                        </td>
                    </tr>

                </tbody>
                </table>
                <c:choose>
                    <c:when test="${decision==0}">
                        <p><a href="<c:url value="/places/edit?id=${i2.id}"/>">この配布を編集する</a></p>
                    </c:when>
                    <c:otherwise>
                                <c:choose>
                                    <c:when test="${i2.act == 0 }">
                                        <c:if test="${i2.aim + remain > 0 }">
                                        <p><a href="<c:url value="/places/edit?id=${i2.id}"/>">この配布の目標数を変更する</a></p>
                                        </c:if>
                                    </c:when>
                                    <c:when test="${i2.place.name == '三田ラック' or i2.place.name =='日吉ラック' or i2.place.name =='研究室棟' or i2.place.name =='矢上ラック' }">
                                        <c:if test="${i2.aim + remain > 0 }">
                                        <p><a href="<c:url value="/places/edit?id=${i2.id}"/>">この配布の目標数を変更する</a></p>
                                        </c:if>
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${i2.can_flag !=2 }">
                                        <p><a href="<c:url value="/places/editj?id=${i2.id}"/>">この配布の実際の数を変更する</a></p>
                                    </c:when>
                                    <c:otherwise>
                                        <p><a href="<c:url value="/places/edit?id=${i2.id}"/>">コメントを編集する</a></p>
                                    </c:otherwise>
                                </c:choose>


                    </c:otherwise>
                </c:choose>
                <c:if test="${i2.act==0 and i2.place.name!='日吉ラック' and i2.place.name!='三田ラック' and i2.place.name!='矢上ラック' and i2.place.name!='研究室棟'}">
                    <p><a href="#" onclick="confirmDestroy();">この配布を削除する</a></p>
                    <form method="POST" action="<c:url value='/places/destroy'/>">
                    <input type="hidden" name="_token" value="${_token}" />
                    </form>
                    <script>
                    function confirmDestroy() {
                        if (confirm("本当に削除してよろしいてすか？")) {
                            document.forms[0].submit();
                        }
                    }
                    </script>
                </c:if>




            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value="/place/search?id=${i2.id}"/>">他の年の${i2.place.name}のハケ状況を見る</a>
        <p><a href="<c:url value="/place/index"/>">${i2.newspaper.month}月のハケ状況一覧に戻る</a>





    </c:param>
</c:import>