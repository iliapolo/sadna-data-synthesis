function savePage() {

    var pageTitle = document.getElementById('page_title').innerText;
    var edit_author_name = document.getElementById('edit_author_name').value;
    var edit_content = document.getElementById('page_content').value;
    var edit_keywords = document.getElementById('edit_keywords').value;

    var pageEdit = {
        "currentText": edit_content,
        "author": edit_author_name,
        "keywords": edit_keywords.trim().split(",")
    }

    var jsonPageEdit = JSON.stringify(pageEdit);

    $.ajax({
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: "../edit/" + pageTitle,
        data: jsonPageEdit,
        success: function(data) {

            // clear the name and keywords input
            document.getElementById('edit_author_name').value = "";
            document.getElementById('edit_keywords').value = "";
            alert("Page updated successfully")

        },
        dataType: 'json',
        error:   function(jqXHR, textStatus, errorThrown) {
            alert("Error, status = " + textStatus + ", " +
                "error thrown: " + errorThrown
            );
        }
    });

    return false;
}