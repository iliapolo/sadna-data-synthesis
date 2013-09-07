<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>Relational Wikipedia</title>
    <link rel="stylesheet" type="text/css" href="<%request.getContextPath();%>./resources/css/search_style.css"/>
    <link rel="stylesheet" type="text/css" href="<%request.getContextPath();%>./resources/css/common.css"/>
    <script type="text/javascript" src="<%request.getContextPath();%>./resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%request.getContextPath();%>./resources/js/search_view.js"></script>
</head>

<body>
<div>
    <a style="margin: 10px;" href="<%request.getContextPath();%>./">
        <img src="<%request.getContextPath();%>./resources/images/home_w.png" style="float: right" width="30px" height="30px"/>
    </a>
    <h1 id="active-search" style="margin-top: 10px; margin-bottom: 10px; margin-left: 10px">
        Relational Wikipedia > Active Search >
        Authors : <span id="active-search-authors" style="font-variant: normal"></span> | Keywords : <span
            id="active-search-keywords" style="font-variant: normal"></span>
    </h1>
</div>

<div style="border: 1px solid #333333"></div>
<div id="navigation" style="margin-top: 10px; border-right: 1px solid #222222; width: 15%; float: left">
    <div id="navigation-block-keywords">
        <ul id="sliding-navigation-keywords">
            <li class="sliding-element"><h3>Keywords</h3></li>
            <c:forEach var="keyword" items="${result.keywords}">
                <li class="sliding-element">
                    <!--The class here is just easy element identification for javascript manipulation
                        see search_view.js
                    -->
                    <a href="#" class="keyword_search_anchor"><c:out value="${keyword}"></c:out></a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <div id="navigation-block-authors" style="margin-top: 10px;">
        <ul id="sliding-navigation-authors">
            <li class="sliding-element"><h3>Authors</h3></li>
            <c:forEach var="name" items="${result.authors}">
                <li class="sliding-element">
                    <!--The class here is just easy element identification for javascript manipulation
                        see search_view.js
                    -->
                    <a href="#" class="author_search_anchor"><c:out value="${name}"></c:out></a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <div id="navigation-block-pages" style="margin-top: 10px;">
        <ul id="sliding-navigation-pages">
            <li class="sliding-element"><h3>Pages</h3></li>
            <c:forEach var="pageTitle" items="${result.pages}">
                <li class="sliding-element">
                    <!--The class here is just easy element identification for javascript manipulation
                        see search_view.js
                    -->
                    <a href="#" class="page_search_anchor"><c:out value="${pageTitle}"></c:out></a>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<c:if test="${page != null}">
    <div id="page" style="margin-top: 10px; margin-left: 10px; width: 84%; float: left">
        <h1 class="page_title"><c:out value="${page.title}"></c:out></h1>
        <p class="page_authors">by <c:out value="${page.authors}"></c:out></p>
        <div id="page_content" style="margin-top: 10px">
            <c:out value="${page.content}"></c:out>
        </div>
        <div id="page_options" style="margin-top: 10px">
            <a href="javascript:getEdit()" class="button edit">edit</a>
            <a href="" class="button">history</a>
            <a href="javascript:getComments()" class="button">comments</a>
        </div>
    </div>
</c:if>

</body>
</html>