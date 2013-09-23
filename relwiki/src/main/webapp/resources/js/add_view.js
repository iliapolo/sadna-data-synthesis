function addPage() {

    var pageTitle = document.getElementById('page_title').value;
    var pageKeywords = document.getElementById('page_keywords').value;
    var pageContent = document.getElementById('page_content').value;
    var pageAuthorName = document.getElementById('page_author_name').value;

    var newPage = {
        "title" : pageTitle,
        "author" : pageAuthorName,
        "wikitext" : pageContent,
        "keywords": pageKeywords.trim().split(",")
    };

    var jsonPage = JSON.stringify(newPage);

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

            document.getElementById('page_title').value = "";
            document.getElementById('page_keywords').value = "";
            document.getElementById('page_content').value = "";
            document.getElementById('page_author_name').value = "";

            alert("Page added successfully")

        },
        error:   function(jqXHR, textStatus, errorThrown) {
            alert("Error, status = " + textStatus + ", " +
                "error thrown: " + errorThrown
            );
        }
    });
}