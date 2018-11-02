var seats = [];
var section = document.querySelector('section');

function colourChange(id) {

    var x = document.getElementById(id.toString());
    if (x.style.color === 'black') {
        x.style.color = 'red';
        if (id.charAt(id.length-1) !== "z") {
            seats.push(id)
        }
    } else {
        if (id.charAt(id.length-1) !== "z") {
            x.style.color = 'black'
        }

        removeElement(seats, id)
    }
    console.log(seats)
}

function removeElement(seat,id) {
    for(var i = seat.length - 1; i >= 0; i--) {
        if(seat[i] === id) {
            seat.splice(i, 1);
        }
    }
}

function httpGet(id)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "http://localhost:9000/book/" + id, true ); // false for synchronous request
    xmlHttp.send( null );

}

function bookAll() {
    for (var i = 0; i < seats.length; i++){
        httpGet(seats[i]);
        console.log(seats[i])
    }

    window.alert("Booking successful for the seats: " + seats);
    window.location.reload()

}

function httpGets()
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "http://localhost:9000/listSeats", false ); // false for synchronous request
    xmlHttp.send( null );
    console.log(xmlHttp.responseText.split(","));
    return xmlHttp.responseText.split(",");

}

window.onload = function() {

    var a = httpGets();
    console.log(a);
    var myArticle = document.createElement('article');
    var myButton = document.createElement('button');

    for (var i = 0; i < a.length; i++) {

        var icon = document.createElement('i');
        icon.setAttribute("class", "fas fa-couch");
        icon.setAttribute("id", a[i]);
        console.log(a[i].charAt(a[i].length-1));
        if (a[i].charAt(a[i].length-1) === "z"){
            icon.setAttribute("style", "font-size:48px;color:red");
        }
        else {
            icon.setAttribute("style", "font-size:48px;color:black");

        }
        icon.setAttribute("onclick", "colourChange("+"\'"+a[i]+"\'"+")");

        myArticle.appendChild(icon);

    }

    myButton.setAttribute('id', 'loll');
    myButton.setAttribute('onclick', 'bookAll()');
    myButton.setAttribute('type', 'submit');
    myButton.textContent = "Book";
    myArticle.appendChild(myButton);

    console.log(myArticle);
    document.getElementById("seats").innerHTML = myArticle.innerHTML;


    console.log(section)

}



