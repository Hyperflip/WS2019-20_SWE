function toLowerRequest() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("toLowerOutput").innerHTML = this.responseText;
        }
    };

    xhttp.open("POST", "/ToLower", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("lower=" + document.getElementById("toLowerInput").value);
}