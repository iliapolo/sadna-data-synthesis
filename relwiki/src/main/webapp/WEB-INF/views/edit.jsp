<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>Relational Wikipedia</title>
    <link rel="stylesheet" type="text/css" href="<%request.getContextPath();%>../resources/css/edit_style.css"/>
    <link rel="stylesheet" type="text/css" href="<%request.getContextPath();%>../resources/css/common.css"/>
    <script type="text/javascript" src="<%request.getContextPath();%>../resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%request.getContextPath();%>../resources/js/expanding.js"></script>
    <script type="text/javascript" src="<%request.getContextPath();%>../resources/js/edit_view.js"></script>
</head>
<body>

<div>
    <a style="margin: 10px;" href="<%request.getContextPath();%>../">
        <img src="<%request.getContextPath();%>../resources/images/home_w.png" style="float: right" width="30px" height="30px"/>
    </a>
    <h1 id="page_title" class="page_title" style="margin-left: 5px"><c:out value="${page.title}"></c:out></h1>
    <p class="page_authors" style="margin-left: 5px">by <c:out value="${page.authors}"></c:out></p>
</div>

<form>
    <textarea id="page_content" class="input textarea expanding"
              style="width: 100%; border: 2px solid #1a1a1a;"><c:out value="${page.content}"></c:out></textarea>
</form>

<form id="form" class="rounded" style="margin-top: 30px" method="post" action="#" onsubmit="return savePage()">
    <h3 style="font-family: 'Courier New', Courier, monospace;">Edit Page</h3>

    <div class="field">
        <label for="edit_author_name">Name:</label>
        <input id="edit_author_name" type="text" class="input"/>
        <p class="hint">Enter your name.</p>
    </div>

    <input type="submit" name="Submit"  class="button" value="Save" style="float: right; margin:10px 70px 10px 0;" />
</form>



</body>
</html>