<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
    <script>var array =[];</script>
    <% List<String> al =(List<String>)request.getAttribute("s");%>
    <%Iterator<String> it = al.iterator();%>
    <% while (it.hasNext()) { %>
    <%      String a =it.next();%>
    <script> var aa='<%=a%>';</script>


    <script>array.push(aa);</script>

    <%} %>

    <script>var srray =[];</script>
    <% List<Double> sl =(List<Double>)request.getAttribute("i");%>
    <%Iterator<Double> st = sl.iterator();%>
    <% while (st.hasNext()) { %>
    <%      Double is =st.next();%>
    <script> var ss='<%=is%>';</script>
    <script> var sw=parseInt(ss);</script>
    <script>srray.push(sw);</script>

    <%} %>

    <h2>ハケ率グラフ</h2>
  <canvas id="myBarChart"></canvas>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.js"></script>

  <script>
  var ctx = document.getElementById("myBarChart");
  var myBarChart = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: array,
      datasets: [
        {
          label: '年毎ハケ率',
          data: srray,
          backgroundColor: "rgba(130,201,169,0.5)"
        },
      ]
    },
    options: {
        title: {
          display: true,
          text: '年毎ハケ率'
        },
        scales: {
          yAxes: [{
            ticks: {
              suggestedMax: 100,
              suggestedMin: 0,
              stepSize: 10,
              callback: function(value, index, values){
                return  value + "%"
              }
            }
          }]
        },
      }
    });

  </script>

<p><a href="<c:url value='/index.html'/>">年度別ハケ率一覧に戻る</a></p>


    </c:param>
</c:import>