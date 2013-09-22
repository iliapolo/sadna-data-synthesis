<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>Relational Wikipedia</title>
    <link rel="stylesheet" type="text/css" href="<%request.getContextPath();%>../resources/css/comments_style.css"/>
    <link rel="stylesheet" type="text/css" href="<%request.getContextPath();%>../resources/css/common.css"/>
    <script type="text/javascript" src="<%request.getContextPath();%>../resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%request.getContextPath();%>../resources/js/expanding.js"></script>
    <script type="text/javascript" src="<%request.getContextPath();%>../resources/js/comments_view.js"></script>
</head>
<body>

<div>
    <a style="margin: 10px;" href="<%request.getContextPath();%>../">
        <img src="<%request.getContextPath();%>../resources/images/home_w.png" style="float: right" width="30px" height="30px"/>
    </a>
    <h1 id="page_title" class="page_title" style="margin-left: 5px"><c:out value="${title}"></c:out></h1>
    <p class="page_authors" style="margin-left: 5px">by <c:out value="${authors}"></c:out></p>
</div>
<table id="comments_table" class="comments_table" border="1">
    <tr><th>Name</th><th>Time</th><th>Comment</th></tr>
    <c:forEach var="comment" items="${comments}">
        <tr>
            <td><c:out value="${comment.author}"></c:out></td>
            <td><c:out value="${comment.date}"></c:out></td>
            <td><c:out value="${comment.comment}"></c:out></td>
        </tr>
    </c:forEach>
</table>

<form id="form" class="rounded" style="margin-top: 30px" method="post" action="#" onsubmit="return postComment()">
    <h3 style="font-family: 'Courier New', Courier, monospace;">Leave a comment</h3>

    <div class="field">
        <label for="comment_author_name">Name:</label>
        <input id="comment_author_name" type="text" class="input"/>
        <p class="hint">Enter your name.</p>
    </div>

    <div class="field">
        <label for="comment_content">Message:</label>
        <textarea id="comment_content" class="input textarea expanding"></textarea>
        <p class="hint">Write your message.</p>
    </div>

    <input type="submit" name="Submit"  class="button" value="Post" style="float: right; margin:10px 70px 10px 0;" />
</form>

</body>
</html>