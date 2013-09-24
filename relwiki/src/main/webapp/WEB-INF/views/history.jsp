<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>Relational Wikipedia</title>
    <link rel="stylesheet" type="text/css" href="<%request.getContextPath();%>../resources/css/history_style.css"/>
    <link rel="stylesheet" type="text/css" href="<%request.getContextPath();%>../resources/css/common.css"/>
    <script type="text/javascript" src="<%request.getContextPath();%>../resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%request.getContextPath();%>../resources/js/expanding.js"></script>
    <script type="text/javascript" src="<%request.getContextPath();%>../resources/js/history_view.js"></script>
</head>
<body>

<div>
    <a style="margin: 10px;" href="<%request.getContextPath();%>../">
        <img src="<%request.getContextPath();%>../resources/images/home_w.png" style="float: right" width="30px" height="30px"/>
    </a>
    <h1 id="page_title" class="page_title" style="margin-left: 5px"><c:out value="${title}"></c:out></h1>
    <p class="page_authors" style="margin-left: 5px">by <c:out value="${authors}"></c:out></p>
</div>
<table id="revisions_table" class="revisions_table" border="1">
    <tbody id="revisions_table-body">
    <tr>
        <th>Revision</th><th>Removed</th><th>Added</th>
    </tr>
    <c:forEach var="revision" items="${revisions}">
        <tr>
            <td><c:out value="${revision.number}"></c:out></td>

            <td>
                <c:forEach var="removed" items="${revision.removed}">
                    <li style="background-color: red">
                        <c:out value="${removed.lineNumber}"></c:out> -- <c:out value="${removed.text}"></c:out>
                    </li>
                </c:forEach>
            </td>

            <td>
                <c:forEach var="added" items="${revision.added}">
                    <li style="background-color: green">
                        <c:out value="${added.lineNumber}"></c:out> ++ <c:out value="${added.text}"></c:out>
                    </li>
                </c:forEach>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>