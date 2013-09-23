function addBackgroundStyleToCommentTable() {
    return function () {
        var table = document.getElementById('comments_table');
        var tfrow = table.rows.length;
        var tbRow = [];
        for (var i = 1; i < tfrow; i++) {
            tbRow[i] = table.rows[i];
            tbRow[i].onmouseover = function () {
                this.style.backgroundColor = '#ffffff';
            };
            tbRow[i].onmouseout = function () {
                this.style.backgroundColor = '#a9a9a9';
            };
        }
    };
}

$(document.body).ready();

function postComment() {

    var pageTitle = document.getElementById('page_title').value;
    var comment_author_name = document.getElementById('comment_author_name').value;
    var comment_content = document.getElementById('comment_content').value;

    var now = new Date();
    var date = now.getDate() + "/" + (now.getMonth() + 1) + "/" + now.getFullYear()
    var time = now.getHours() + "-" + now.getMinutes() + "-" + now.getSeconds();

    var comment = {
        "author" : comment_author_name,
        "date" : date,
        "time" : time,
        "comment" : comment_content,
        "title" : pageTitle
    };

    var jsonComment = JSON.stringify(comment);

    $.ajax({
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: "../comments/" + pageTitle,
        data: jsonComment,
        dataType: 'json',
        success: function(data) {

            // append new comment to table
            var comments = document.getElementById('comments_table-body');
            comments.appendChild(createCommentElement());

            // clear comment from form
            document.getElementById("comment_author_name").value = "";
            document.getElementById("comment_content").value = "";
        },
        error:   function(jqXHR, textStatus, errorThrown) {
            alert("Error, status = " + textStatus + ", " +
                "error thrown: " + errorThrown
            );
        }
    });

    function createCommentElement() {

        var tr = document.createElement("tr");

        var authorTd = document.createElement("td");
        authorTd.innerText = comment.author;

        var dateTd = document.createElement("td");
        dateTd.innerText = comment.date;

        var timeTd = document.createElement("td");
        timeTd.innerText = comment.time;

        var commentTd = document.createElement("td");
        commentTd.innerText = comment.comment;

        tr.appendChild(authorTd);
        tr.appendChild(dateTd);
        tr.appendChild(timeTd);
        tr.appendChild(commentTd);
        return tr;

    }

    return false;
}