<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>${sessionScope.year}年度 新聞ハケ率一覧</h2>
        <table id="newspaper_list">
            <tbody>
                <tr>
                    <th class="newspaper_month">月</th>
                    <th class="newspaper_volume">発行部数</th>
                    <th class="newspaper_hake">ハケ数</th>
                    <th class="newspaper_rate">ハケ率</th>
                    <th class="report_action">操作①</th>
                    <th class="report_action">操作②</th>

                </tr>
                <c:forEach var="i" items="${i1}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="newspaper_month"><c:out value="${i.newspaper.month}" /></td>
                        <td class="newspaper_volume"><c:out value="${i.volumn}"/></td>
                        <td class="newspaper_hake"><c:out value="${i.hake}"/></td>
                        <td class="newspaper_rate"><c:out value="${i.rate}"/></td>
                        <c:choose>
                        <c:when test="${i.decision == 0 }">
                            <c:choose>
                                <c:when test="${i.vo_decision == 1 }">
                                    <td class="report_action"><a href="<c:url value='/newspapers/edit?id=${i.id}' />">発行部数を編集する</a></td>
                                </c:when>
                                <c:otherwise>
                                    <td class="report_action"><a href="<c:url value='/newspapers/new?id=${i.id}' />">発行部数を登録する</a></td>
                                </c:otherwise>
                            </c:choose>


                        </c:when>
                        <c:otherwise>
                            <td class="report_action">なし</td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${i.vo_decision == 1 }">
                                <td class="report_action"><a href="<c:url value='/place/index?id=${i.id}' />">詳細を見る</a></td>
                            </c:when>
                            <c:otherwise>
                                <td class="report_action"><c:out value=""/></td>
                            </c:otherwise>
                        </c:choose>


                    </tr>


                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${newspaper_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((newspaper_count - 1) / 6) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/newspapers/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
         <c:if test="${flag ==1 }">
            <p><a href="<c:url value='/chart/ore?id=${year}'/>">月別新聞ハケ率をグラフで見る</a></p>
        </c:if>
        <p><a href="<c:url value='/index.html'/>">年度別ハケ率一覧に戻る</a>

    </c:param>
</c:import>