//Establish the WebSocket connection and set up event handlers
var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat/");
webSocket.onmessage = function (msg) {updateChat(msg);};
webSocket.onclose = function () {
alert("WebSocket connection closed.")
};

id("leaveChannel").style.visibility = "hidden"
id("title3").style.visibility = "hidden";
id("message").style.visibility = "hidden";
id("send").style.visibility = "hidden";

id("leaveChannel").addEventListener("click", function(){
    window.location.reload();
});

id("saveChannel").addEventListener("click", function() {
    sendChannel(id("newChannel").value);
    id("saveChannel").style.visibility = "hidden";
    id("title2").style.visibility = "hidden";
    id("newChannel").style.visibility = "hidden";
    id("channelList").style.visibility = "hidden";
    id("leaveChannel").style.visibility = "visible";
    id("title3").style.visibility = "visible";
    id("message").style.visibility = "visible";
    id("send").style.visibility = "visible";
});

id("newChannel").addEventListener("keypress", function(e) {
    if(e.keyCode === 13) {
        sendChannel(id("newChannel").value);
         id("saveChannel").style.visibility = "hidden";
         id("title2").style.visibility = "hidden";
         id("newChannel").style.visibility = "hidden";
         id("channelList").style.visibility = "hidden";
         id("leaveChannel").style.visibility = "visible";
         id("title3").style.visibility = "visible";
         id("message").style.visibility = "visible";
         id("send").style.visibility = "visible";
    }
});


//Send message if "Send" is clicked
id("send").addEventListener("click", function () {
    sendMessage(id("message").value);
});

//Send message if enter is pressed in the input field
id("message").addEventListener("keypress", function (e) {
    if (e.keyCode === 13 && e.target.id == "message") { sendMessage(e.target.value); }
});

//Send a message if it's not empty, then clear the input field
function sendMessage(message) {
    var obj = {
        msg: message
    }
    if (message !== "") {
        webSocket.send(JSON.stringify(obj));
        id("message").value = "";
    } else {
        if(!alert('Nie spamuj pustymi wiadomościami!'));
    }
}

function sendChannel(channel) {
    var obj = {
        channelName: channel
    }
    if(channel !== "") {
        webSocket.send(JSON.stringify(obj));
        $("#allChannels").addClass("hidden");
        $("#userlist").removeClass("hidden");
        $("#chatControls").removeClass("hidden");
        $("#chat").removeClass("hidden");
    }
    else {
        if(!alert('Podaj nazwę kanału!')){
            window.location.reload();
        }
    }
}

function updateChat(msg) {
    var data = JSON.parse(msg.data);

    if(data.hasOwnProperty("channels")) {
        id("channelList").innerHTML = "";
        data.channels.forEach(function(channel) {
            insert("channelList", "<li id=\"ch"+channel+"\" class=\"channel\">" + channel + "</li>");
            id("ch"+channel).addEventListener("click", function () {
                sendNickChannel(id("nickname").value, channel)
                $("#allChannels").addClass("hidden");
                $("#userlist").removeClass("hidden");
                $("#chatControls").removeClass("hidden");
                $("#chat").removeClass("hidden");
            });
        });
    } else {
        if(data.hasOwnProperty("userlist")) {
            id("userlist").innerHTML = "";
            data.userlist.forEach(function (user) {
                insert("userlist", "<li>" + user + "</li>");
            });
        } else {
            if(data.hasOwnProperty("userMessage")) {
                insert("chat", data.userMessage);
            }
        }
    }

}

//Helper function for inserting HTML as the first child of an element
function insert(targetId, message) {
    id(targetId).insertAdjacentHTML("afterbegin", message);
}

//Helper function for selecting element by id
function id(id) {
    return document.getElementById(id);
}
