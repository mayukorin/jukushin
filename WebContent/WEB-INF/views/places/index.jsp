<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <c:if test="${error != null}">
            <div id="flush_error">
                <c:forEach var="e" items="${error}">
                    <c:out value="${e}"/><br/>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${errorr != null }">
            <div id="flush_error">
                <c:out value="${errorr}"/><br/>

            </div>
        </c:if>
        <h2>${i1.newspaper.month}月のハケ状況一覧</h2>

            <table id="place_list">
            <tbody>
                <c:choose>
                <c:when test="${decision == 0}">
                 <tr>
                            <th class="place_name">新聞</th>
                            <th class="place_aim">暫定発行部数</th>
                            <th class="place_act">想定残部</th>
                            <th class="place_action">操作</th>
                </tr>
                <tr class="row2">
                    <td class="place_name">${i1.newspaper.month}月号</td>
                    <td class="place_aim"><c:out value="${i1.volumn}"/></td>
                    <td class="place_act"><c:out value="${i1.remain}"/></td>
                    <td class="place_action">なし</td>
                </tr>
                 </c:when>
                 <c:otherwise>
                    <tr>
                            <th class="place_name">新聞</th>
                            <th class="place_aim">発行部数</th>
                            <th class="place_act1">ハケ数</th>
                            <th class="place_act2">残部</th>
                            <th class="place_action">操作</th>
                    </tr>
                <tr class="row2">
                    <td class="place_name">${i1.newspaper.month}月号</td>
                    <td class="place_aim"><c:out value="${i1.volumn}"/></td>
                    <td class="place_act"><c:out value="${i1.hake}"/></td>
                    <td class="place_act"><c:out value="${i1.remain}"/></td>
                    <td class="place_action"><a href="<c:url value='/chart/bo'/>">他の年と比較する</a></td>
                </tr>
                 </c:otherwise>
                 </c:choose>

             </tbody>
            </table>
            <br/>
            <hr color=#339966 size="3">


        <br/>
        <table id ="place_list">
            <tbody>
                <c:choose>
                    <c:when test="${decision == 0}">
                        <tr>
                            <th class="place_name">場所</th>
                            <th class="place_aim">目標数</th>
                            <th class="place_act">配布済数</th>
                            <th class="place_action">操作</th>
                            </tr>
                            <tr class="row2">
                            <td class="place_name">三田合計</td>
                            <td class="place_aim"><c:out value="${i1.mita}"/></td>
                            <td class="place_act"><c:out value="${i1.mita_a}"/></td>
                            <td class="place_action">なし</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <th class="place_name">場所</th>
                            <th class="place_aim">発行部数</th>
                            <th class="place_act1">配布済数</th>
                            <th class="place_act2">配布見込み</th>
                            <th class="place_action">操作</th>
                        </tr>
                        <c:choose>
                        <c:when test="${mm == 0 }">
                            <tr class="row2">
                                <td class="place_name">三田</td>
                                <td class="place_aim"><c:out value="${i1.mita}"/></td>
                                <td class="place_act1"><c:out value="${i1.mita_a}"/></td>
                                <td class="place_act2"><c:out value="${m}"/></td>
                                <td class="place_action">
                                <c:choose>
                                <c:when test="${i1.mita_a > 0 }">
                                <p><a href="<c:url value='/places/circle?id=0'/>">図で見る</a></p>
                                </c:when>
                                <c:otherwise>
                                なし
                                </c:otherwise>
                                </c:choose>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr class="row4">
                                <td class="place_name">三田</td>
                                <td class="place_aim"><c:out value="${i1.mita}"/></td>
                                <td class="place_act1"><c:out value="${i1.mita_a}"/></td>
                                <td class="place_act2"><c:out value="${m}"/></td>
                                <td class="place_action">
                                <c:choose>
                                <c:when test="${i1.mita_a > 0 }">
                                <p><a href="<c:url value='/places/circle?id=0'/>">図で見る</a></p>
                                </c:when>
                                <c:otherwise>
                                なし
                                </c:otherwise>
                                </c:choose>
                                </td>
                            </tr>
                        </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
        <br/>
        <table id="place_list">
            <tbody>
                <tr>
                    <th class="place_name">場所</th>
                    <c:choose>
                        <c:when test="${decision == 0 }">
                            <th class="place_aim">目標数</th>
                            <th class="place_act">配布済数</th>
                        </c:when>
                        <c:otherwise>
                            <th class="place_aim">初期目標数</th>
                            <th class="place_act1">現在の目標数</th>
                            <th class="place_act1">配布済数</th>
                            <th class="place_act2">配布見込み</th>
                        </c:otherwise>
                    </c:choose>
                    <th class="place_action">操作</th>
                </tr>
                <c:forEach var="pl" items="${othm}" varStatus="status">
                    <c:choose>
                        <c:when test="${decision == 0 }">
                        <tr class="row${status.count % 2 }">
                        <td class="place_name"><c:out value="${pl.place.name}"/></td>
                        <td class="place_aim"><c:out value="${pl.aimconst}"/></td>
                        <c:choose>
                            <c:when test="${decision == 0 }">
                                <td class="place_act"><c:out value="${pl.act}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td class="place_act1"><c:out value="${pl.aim}"/></td>
                                <td class="place_act1"><c:out value="${pl.act}"/></td>
                                <td class="place_act2">
                                    <c:choose>
                                        <c:when test="${pl.act == 0  or pl.place.name=='三田ラック' or pl.place.name=='研究室棟'}">
                                        <c:choose>
                                            <c:when test="${pl.act >pl.aim }">
                                                <c:out value="${pl.act }"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${pl.aim }"/>
                                            </c:otherwise>
                                        </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${pl.act}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </c:otherwise>
                        </c:choose>
                        <td class="place_action"><a href="<c:url value='/places/show?id=${pl.id}'/>">詳細を見る</a></td>
                    </tr>
                    </c:when>
                    <c:when test="${decision == 1 && pl.act >= pl.aim }">
                    <tr class="row2">
                        <td class="place_name"><c:out value="${pl.place.name}"/></td>
                        <td class="place_aim"><c:out value="${pl.aimconst}"/></td>
                        <c:choose>
                            <c:when test="${decision == 0 }">
                                <td class="place_act"><c:out value="${pl.act}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td class="place_act1"><c:out value="${pl.aim}"/></td>
                                <td class="place_act1"><c:out value="${pl.act}"/></td>
                                <td class="place_act2">
                                    <c:choose>
                                        <c:when test="${pl.act == 0  or pl.place.name=='三田ラック' or pl.place.name=='研究室棟'}">
                                        <c:choose>
                                            <c:when test="${pl.act >pl.aim }">
                                                <c:out value="${pl.act }"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${pl.aim }"/>
                                            </c:otherwise>
                                        </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${pl.act}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </c:otherwise>
                        </c:choose>
                        <td class="place_action"><a href="<c:url value='/places/show?id=${pl.id}'/>">詳細を見る</a></td>
                    </tr>
                    </c:when>

                    <c:otherwise>
                    <tr class="row4">
                        <td class="place_name"><c:out value="${pl.place.name}"/></td>
                        <td class="place_aim"><c:out value="${pl.aimconst}"/></td>
                        <c:choose>
                            <c:when test="${decision == 0 }">
                                <td class="place_act"><c:out value="${pl.act}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td class="place_act1"><c:out value="${pl.aim}"/></td>
                                <td class="place_act1"><c:out value="${pl.act}"/></td>
                                <td class="place_act2">
                                    <c:choose>
                                        <c:when test="${pl.act == 0  or pl.place.name=='三田ラック' or pl.place.name=='研究室棟'}">
                                        <c:choose>
                                            <c:when test="${pl.act >pl.aim }">
                                                <c:out value="${pl.act }"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${pl.aim }"/>
                                            </c:otherwise>
                                        </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${pl.act}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </c:otherwise>
                        </c:choose>
                        <td class="place_action"><a href="<c:url value='/places/show?id=${pl.id}'/>">詳細を見る</a></td>
                    </tr>
                    </c:otherwise>
                    </c:choose>
                </c:forEach>
            </tbody>
        </table>
        <br/>
        <hr color=#339966 size="3">

        <br/>
        <table id="place_list">
                <tbody>
                    <c:choose>
                        <c:when test="${decision == 0}">
                            <tr>
                            <th class="place_name">場所</th>
                            <th class="place_aim">目標数</th>
                            <th class="place_act">配布済数</th>
                            <th class="place_action">操作</th>
                            </tr>
                            <tr class="row2">
                            <td class="place_name">日吉合計</td>
                            <td class="place_aim"><c:out value="${i1.hiyoshi}"/></td>
                            <td class="place_act"><c:out value="${i1.hiyoshi_a}"/></td>
                            <td class="place_action">なし</td>
                            </tr>

                        </c:when>
                        <c:otherwise>
                            <tr>
                                <th class="place_name">場所</th>
                                <th class="place_aim">発行部数</th>
                                <th class="place_act1">配布済数</th>
                                <th class="place_act2">配布見込み</th>
                                <th class="place_action">操作</th>
                            </tr>
                            <c:choose>
                            <c:when test="${hh == 0 }">
                                <tr class="row2">
                                   <td class="place_name">日吉</td>
                                   <td class="place_aim"><c:out value="${i1.hiyoshi}"/></td>
                                   <td class="place_act1"><c:out value="${i1.hiyoshi_a}"/></td>
                                   <td class="place_act2"><c:out value="${h}"/></td>
                                   <td class="place_action">
                                   <c:choose>
                                   <c:when test="${i1.hiyoshi_a > 0 }">
                                   <p><a href="<c:url value='/places/circle?id=1'/>">図で見る</a></p>
                                   </c:when>
                                   <c:otherwise>
                                   なし
                                   </c:otherwise>

                                   </c:choose>
                                   </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr class="row4">
                                    <td class="place_name">日吉</td>
                                    <td class="place_aim"><c:out value="${i1.hiyoshi}"/></td>
                                    <td class="place_act1"><c:out value="${i1.hiyoshi_a}"/></td>
                                    <td class="place_act2"><c:out value="${h}"/></td>
                                    <td class="place_action">
                                    <c:choose>
                                    <c:when test="${i1.hiyoshi_a > 0 }">
                                    <p><a href="<c:url value='/places/circle?id=1'/>">図で見る</a></p>
                                    </c:when>
                                    <c:otherwise>
                                    なし
                                    </c:otherwise>

                                    </c:choose>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>

        </c:otherwise>
        </c:choose>
        </tbody>
        </table>
        <br/>
        <table id="place_list">
            <tbody>
                <tr>
                    <th class="place_name">場所</th>
                    <c:choose>
                        <c:when test="${decision==0 }">
                            <th class="place_aim">目標数</th>
                            <th class="place_act">配布済数</th>
                        </c:when>
                        <c:otherwise>
                            <th class="place_aim">初期目標数</th>
                            <th class="place_act1">現在の目標数</th>
                            <th class="place_act1">配布済数</th>
                            <th class="place_act2">配布見込み</th>
                        </c:otherwise>
                    </c:choose>
                    <th class="place_action">操作</th>
                    </tr>
                    <c:forEach var="plm" items="${oth}" varStatus = "status" >
                         <c:choose>
                         <c:when test="${decision == 1 && plm.act > plm.aim}">
                            <tr class="row2">
                            <td class="place_name"><c:out value="${plm.place.name}"/></td>
                            <td class="place_aim"><c:out value="${plm.aimconst}"/></td>
                            <c:choose>
                                <c:when test="${decision==0}">
                                    <td class="place_act"><c:out value="${plm.act}"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td class="place_act1"><c:out value="${plm.aim}"/></td>
                                    <td class="place_act1"><c:out value="${plm.act}"/></td>
                                    <c:choose>
                                        <c:when test="${plm.act==0 or plm.place.name=='日吉ラック' or plm.place.name=='矢上ラック'}">
                                            <c:choose>
                                                <c:when test="${plm.act > plm.aim }">
                                                    <td class="place_act2"><c:out value="${plm.act}"/></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td class="place_act2"><c:out value="${plm.aim}"/></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="place_act2"><c:out value="${plm.act}"/></td>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                            <td class="place_action"><a href="<c:url value='/places/show?id=${plm.id}'/>">詳細を見る</a></td>
                        </tr>
                        </c:when>

                        <c:when test="${decision == 0}">
                            <tr class="row${status.count % 2 }">
                            <td class="place_name"><c:out value="${plm.place.name}"/></td>
                            <td class="place_aim"><c:out value="${plm.aimconst}"/></td>
                            <c:choose>
                                <c:when test="${decision==0}">
                                    <td class="place_act"><c:out value="${plm.act}"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td class="place_act1"><c:out value="${plm.aim}"/></td>
                                    <td class="place_act1"><c:out value="${plm.act}"/></td>
                                    <c:choose>
                                        <c:when test="${plm.act==0 or plm.place.name=='日吉ラック' or plm.place.name=='矢上ラック'}">
                                            <c:choose>
                                                <c:when test="${plm.act > plm.aim }">
                                                    <td class="place_act2"><c:out value="${plm.act}"/></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td class="place_act2"><c:out value="${plm.aim}"/></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="place_act2"><c:out value="${plm.act}"/></td>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                            <td class="place_action"><a href="<c:url value='/places/show?id=${plm.id}'/>">詳細を見る</a></td>
                        </tr>
                        </c:when>
                        <c:otherwise>
                            <tr class="row4">
                            <td class="place_name"><c:out value="${plm.place.name}"/></td>
                            <td class="place_aim"><c:out value="${plm.aimconst}"/></td>
                            <c:choose>
                                <c:when test="${decision==0}">
                                    <td class="place_act"><c:out value="${plm.act}"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td class="place_act1"><c:out value="${plm.aim}"/></td>
                                    <td class="place_act1"><c:out value="${plm.act}"/></td>
                                    <c:choose>
                                        <c:when test="${plm.act==0 or plm.place.name=='日吉ラック' or plm.place.name=='矢上ラック'}">
                                            <c:choose>
                                                <c:when test="${plm.act > plm.aim }">

                                                    <td class="place_act2"><c:out value="${plm.act}"/></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td class="place_act2"><c:out value="${plm.aim}"/></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="place_act2"><c:out value="${plm.act}"/></td>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                            <td class="place_action"><a href="<c:url value='/places/show?id=${plm.id}'/>">詳細を見る</a></td>
                        </tr>
                        </c:otherwise>
                        </c:choose>
                    </c:forEach>
                 </tbody>
              </table>
            <br/>
            <hr color=#339966 size="3">

        <br/>
        <table id="place_list">
            <tbody>
                 <tr>
                    <th class="place_name">場所</th>
                    <c:choose>
                        <c:when test="${decision == 0 }">
                            <th class="place_aim">目標数</th>
                            <th class="place_act">配布済数</th>
                        </c:when>
                        <c:otherwise>
                            <th class="place_aim">初期目標数</th>
                            <th class="place_act1">配布済数</th>
                            <th class="place_act2">配布見込み</th>
                        </c:otherwise>
                    </c:choose>
                    <th class="place_action">操作</th>
                </tr>
        <c:forEach var="plo" items="${otho}" varStatus = "status" >
                    <c:choose>
                        <c:when test="${decision == 0}">
                        <tr class="row${status.count % 2 }">
                        <td class="place_name"><c:out value="${plo.place.name}"/></td>
                        <td class="place_aim"><c:out value="${plo.aim}"/></td>
                        <c:choose>
                            <c:when test="${decision == 0 }">
                                <td class="place_act"><c:out value="${plo.act}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td class="place_act1"><c:out value="${plo.act}"/></td>
                                <td class="place_act2"><c:out value="${plo.act}"/></td>
                            </c:otherwise>
                        </c:choose>

                        <td class="place_action"><a href="<c:url value='/places/show?id=${plo.id}'/>">詳細を見る</a></td>

                        </tr>
                        </c:when>
                        <c:otherwise>
                        <tr class="row2">
                            <td class="place_name"><c:out value="${plo.place.name}"/></td>
                        <td class="place_aim"><c:out value="${plo.aim}"/></td>
                        <c:choose>
                            <c:when test="${decision == 0 }">
                                <td class="place_act"><c:out value="${plo.act}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td class="place_act1"><c:out value="${plo.act}"/></td>
                                <td class="place_act2"><c:out value="${plo.act}"/></td>
                       </c:otherwise>
                        </c:choose>

                        <td class="place_action"><a href="<c:url value='/places/show?id=${plo.id}'/>">詳細を見る</a></td>

                        </tr>
                        </c:otherwise>

                    </c:choose>
           </c:forEach>
           </tbody>
           </table>


        <c:choose>
            <c:when test="${decision == 0}">
                <c:if test="${i1.remain > 0}">
                    <p><a href="<c:url value='/places/new?flag=0'/>">新規配布場所の登録</a></p>
                </c:if>
                <p><a href="<c:url value='/places/decide'/>"><button type="submit">日吉と三田の部数を確定させる</button></a></p>
            </c:when>
            <c:otherwise>
                <c:if test="${hh > 0}">
                    <p><a href="<c:url value='/places/new?flag=2'/>">日吉からの新規配布場所の登録</a></p>
                 </c:if>
                  <c:if test="${mm > 0}">
                    <p><a href="<c:url value='/places/new?flag=1'/>">三田からの新規配布場所の登録</a></p>
                </c:if>





            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/newspapers/index'/>"><c:out value="${i1.newspaper.year}年度新聞ハケ率一覧に戻る"/></a></p>
    </c:param>
</c:import>
