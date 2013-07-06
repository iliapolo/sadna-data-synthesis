$(document).ready(function()
{
    slide("#sliding-navigation-keywords", 25, 15, 150, .8);
    slide("#sliding-navigation-authors", 25, 15, 150, .8);
    slide("#sliding-navigation-pages", 25, 15, 150, .8);

    var author = getParameterByName("author");
    var keywords = getParameterByName("keywords");


    // populate the active search header
    document.getElementById("active-search-author").innerText = author;
    document.getElementById("active-search-keywords").innerText = keywords

});

function query(anchor, type) {
    var searchFilter = anchor.innerText;
    var authorFilter = document.getElementById("active-search-author").innerText;
    var keywordsFilter = "";
    if (type == "author") {
        authorFilter = searchFilter;
    } else {
        // keyword
        var innerText = document.getElementById("active-search-keywords").innerText;
        if (innerText != null && innerText != "") {
            keywordsFilter = innerText + "," + anchor.innerText;
        } else {
            keywordsFilter = anchor.innerText;
        }
    }
    window.location.href="search?author=" + authorFilter + "&keywords=" + keywordsFilter;

}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    var s = results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    if (s == "undefined") {
        return "";
    }
    return  s;
}


function slide(navigation_id, pad_out, pad_in, time, multiplier) {
    // creates the target paths
    var list_elements = navigation_id + " li.sliding-element";
    var link_elements = list_elements + " a";

    // initiates the timer used for the sliding animation
    var timer = 0;

    // creates the slide animation for all list elements
    $(list_elements).each(function(i)
    {
        // margin left = - ([width of element] + [total vertical padding of element])
        $(this).css("margin-left","-180px");
        // updates timer
        timer = (timer*multiplier + time);
        $(this).animate({ marginLeft: "0" }, timer);
        $(this).animate({ marginLeft: "15px" }, timer);
        $(this).animate({ marginLeft: "0" }, timer);
    });

    // creates the hover-slide effect for all link elements
    $(link_elements).each(function(i)
    {
        $(this).hover(
            function()
            {
                $(this).animate({ paddingLeft: pad_out }, 150);
            },
            function()
            {
                $(this).animate({ paddingLeft: pad_in }, 150);
            });
    });
}


