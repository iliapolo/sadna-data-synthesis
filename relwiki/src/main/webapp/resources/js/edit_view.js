function savePage() {

    var pageTitle = document.getElementById('page_title').innerText;
    var edit_author_name = document.getElementById('edit_author_name').value;
    var edit_content = document.getElementById('page_content').value;

    var pageEdit = {
        "author" : edit_author_name,
        "content" : edit_content
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

            // clear the name input
            document.getElementById('edit_author_name').value = "";

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