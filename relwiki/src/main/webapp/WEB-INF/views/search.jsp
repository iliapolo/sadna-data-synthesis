<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>Relational Wikipedia</title>
    <link rel="stylesheet" type="text/css" href="resources/css/style.css"/>
    <script type="text/javascript" src="resources/js/jquery.js"></script>
    <script type="text/javascript" src="resources/js/script.js"></script>
</head>

<body>
<h1 id="active-search" style="margin-top: 10px; margin-bottom: 10px; margin-left: 10px">
    Relational Wikipedia > Active Search By
      Authors : <span id="active-search-authors" style="font-variant: normal"></span> | Keywords : <span
        id="active-search-keywords" style="font-variant: normal"></span>
</h1>

<div style="border: 1px solid #333333"></div>
<div id="navigation" style="margin-top: 10px; border-right: 1px solid #222222; width: 15%; float: left">
    <div id="navigation-block-keywords">
        <ul id="sliding-navigation-keywords">
            <li class="sliding-element"><h3>Keywords</h3></li>
            <c:forEach var="keyword" items="${result.keywords}">
                <li class="sliding-element"><a onclick="query(this, 'keyword')"><c:out value="${keyword}"></c:out></a></li>
            </c:forEach>
        </ul>
    </div>

    <div id="navigation-block-authors" style="margin-top: 10px;">
        <ul id="sliding-navigation-authors">
            <li class="sliding-element"><h3>Authors</h3></li>
            <c:forEach var="author" items="${result.authors}">
                <li class="sliding-element"><a onclick="query(this, 'author')"><c:out value="${author}"></c:out></a></li>
            </c:forEach>
        </ul>
    </div>

    <div id="navigation-block-pages" style="margin-top: 10px;">
        <ul id="sliding-navigation-pages">
            <li class="sliding-element"><h3>Pages</h3></li>
            <c:forEach var="pageName" items="${result.pages}">
                <li class="sliding-element"><a href="#"><c:out value="${pageName}"></c:out></a></li>
            </c:forEach>
        </ul>
    </div>
</div>



</body>
</html>