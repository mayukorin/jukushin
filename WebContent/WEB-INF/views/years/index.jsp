<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>年度別 新聞ハケ率一覧</h2>

        <table id="annual_list">
            <tbody>
                <tr>
                    <th class="annual_year">年度</th>
                    <th class="annual_rate">ハケ率</th>
                </tr>
                <c:forEach var="ys_rs" items="${years_rates}" varStatus="status">
                    <tr class="row${status.count % 2 }">
                        <c:forEach var="y_s" items="${ys_rs}" varStatus = "s">
                            <c:choose>
                                <c:when test="${s.count == 1 }">
                                    <td class="annual_year"><a href="<c:url value='/newspapers/index?year=${y_s}'/>"><c:out value="${y_s}"/></a></td>
                                </c:when>
                                <c:otherwise>
                                    <td class="annual_rate"><c:out value="${y_s}"/></td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全${year_count}件）<br/>
            <c:forEach var="i" begin="1" end ="${((year_count-1)/15)+1 }" step="1">
                <c:choose>
                    <c:when test="${i==page }">
                        <c:out value="${i}"/>&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/index.html?page=${i}'/>"><c:out value="${i}"/></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/years/new'/>">新規年度の登録</a></p>
        <c:if test="${flag==1 }">
            <p><a href="<c:url value='/chart/annualole'/>">年度別新聞ハケ率をグラフで見る</a><p>
        </c:if>
        <p><a href="<c:url value='/companies/edit'/>">アカウント情報を編集する</a></p>
    </c:param>
</c:import>