function addPage() {

    var pageTitle = document.getElementById('page_title').value;
    var pageKeywords = document.getElementById('page_keywords').value;
    var pageContent = document.getElementById('page_content').value;
    var pageAuthorName = document.getElementById('page_author_name').value;

    var page = {
        "title" : pageTitle,
        "authors" : [pageAuthorName],
        "content" : pageContent,
        "keywords": [pageKeywords]
    };

    var jsonPage = JSON.stringify(page);

    $.ajax({
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: "./add",
        data: jsonPage,
        dataType: 'json',
        success: function(data) {

            alert("success");

        },
        error:   function(jqXHR, textStatus, errorThrown) {
            alert("Error, status = " + textStatus + ", " +
                "error thrown: " + errorThrown
            );
        }
    });



}