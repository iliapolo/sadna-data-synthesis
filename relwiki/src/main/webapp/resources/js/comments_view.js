$(document.body).ready(
    function() {
        var table = document.getElementById('comments_table');
        var tfrow = table.rows.length;
        var tbRow=[];
        for (var i=1;i<tfrow;i++) {
            tbRow[i]=table.rows[i];
            tbRow[i].onmouseover = function(){
                this.style.backgroundColor = '#ffffff';
            };
            tbRow[i].onmouseout = function() {
                this.style.backgroundColor = '#a9a9a9';
            };
        }
    }
);

function postComment() {

    var pageTitle = document.getElementById('page_title').value;
    var comment_author_name = document.getElementById('comment_author_name').value;
    var comment_content = document.getElementById('comment_content').value;

    var comment = {
        "name" : comment_author_name,
        "time" : new Date(),
        "content" : comment_content
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
            var comments = document.getElementById('comments_table');
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
        var nameTd = document.createElement("td");
        nameTd.innerText = comment.name;
        var timeTd = document.createElement("td");
        timeTd.innerText = comment.time;
        var contentTd = document.createElement("td");
        contentTd.innerText = comment.content;
        tr.appendChild(nameTd);
        tr.appendChild(timeTd);
        tr.appendChild(contentTd);
        return tr;

    }

    return false;
}