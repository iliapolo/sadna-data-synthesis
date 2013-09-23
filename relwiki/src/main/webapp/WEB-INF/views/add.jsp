<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>Relational Wikipedia</title>
    <link rel="stylesheet" type="text/css" href="<%request.getContextPath();%>./resources/css/common.css"/>
    <script type="text/javascript" src="<%request.getContextPath();%>./resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%request.getContextPath();%>./resources/js/expanding.js"></script>
    <script type="text/javascript" src="<%request.getContextPath();%>./resources/js/add_view.js"></script>
</head>

<body>

<div>
    <a style="margin: 10px;" href="<%request.getContextPath();%>./">
        <img src="<%request.getContextPath();%>./resources/images/home_w.png" style="float: right" width="30px" height="30px"/>
    </a>
</div>


<form id="form" style="width: 80%; height: 80%" class="rounded" method="post" action="#" onsubmit="return postComment()">
    <h3 style="font-family: 'Courier New', Courier, monospace;">Add Page</h3>

    <div class="field">
        <label for="page_author_name">Name:</label>
        <input id="page_author_name" type="text" class="input"/>
        <p class="hint">Enter your name.</p>
    </div>

    <div class="field">
        <label for="page_title">Title:</label>
        <input id="page_title" type="text" class="input"/>
        <p class="hint">Enter Page Title.</p>
    </div>

    <div class="field">
        <label for="page_keywords">Keywords:</label>
        <input id="page_keywords" type="text" class="input"/>
        <p class="hint">Comma separated list of keywords.</p>
    </div>

    <div class="field">
        <label for="page_content">Content:</label>
        <textarea id="page_content" style="width: 70%" class="input textarea expanding"></textarea>
        <p class="hint">Write your content.</p>
    </div>

    <a href="javascript:addPage()" class="button add" style="float: right; margin:10px 70px 10px 0;">Add</a>
</form>


</body>
</html>