id("acceptName").addEventListener("click", function () {
    if(id("name").value === ""){
        if(!alert('Nie podałeś imienia!')){
            window.location.reload();
        }
    } else {
    document.cookie = "username=" + id("name").value;
    location.href = "chat.html";
    }
});

//Send message if enter is pressed in the input field
id("name").addEventListener("keypress", function (e) {
    if (e.keyCode === 13) {
        if(id("name").value === ""){
            if(!alert('Nie podałeś imienia!')){
                window.location.reload();
            }
        } else {
        document.cookie = "username=" + (e.target.value);
        location.href = "chat.html";
        }
    }
});

function id(id) {
    return document.getElementById(id);
}