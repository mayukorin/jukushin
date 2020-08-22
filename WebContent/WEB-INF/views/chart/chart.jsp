<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
<body>
  <h2>ハケ場所割合</h2>
  <canvas id="myPieChart"></canvas>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.js"></script>

  <script>
  var a ='<%=request.getAttribute("a")%>';
  var b ='<%=request.getAttribute("b")%>';
  var c ='<%=request.getAttribute("c")%>';
  var d ='<%=request.getAttribute("d")%>';
  var e ='<%=request.getAttribute("e")%>';


  var aa ='<%=request.getAttribute("aa")%>';
  var aaa=parseInt(aa);

  var bb ='<%=request.getAttribute("bb")%>';
  var bbb=parseInt(bb);

  var cc ='<%=request.getAttribute("cc")%>';
  var ccc=parseInt(cc);

  var dd ='<%=request.getAttribute("dd")%>';
  var ddd = parseInt(dd);

  var ee ='<%=request.getAttribute("ee")%>';
  var eee = parseInt(ee);


  var ctx = document.getElementById("myPieChart");
  var myPieChart = new Chart(ctx, {
    type: 'pie',
    data: {
      labels: [a,b,c,d,e],
      datasets: [{
          backgroundColor: [
              "#BB5179",
              "#FAFF67",
              "#58A27C",
              "#3C00FF",
              "#50A3BB"

          ],
          data: [aa,bb,cc,dd,ee]
      }]
    },
    options: {
      title: {
        display: true,
        text: 'ハケ割合'
      }
    }
  });
  </script>
  <p><a href="<c:url value='/place/index'/>">${sessionScope.month}月のハケ状況一覧に戻る</a></p>

</body>
</c:param>
</c:import>