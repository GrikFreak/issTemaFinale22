/*
wsminimal.js
*/

    var socket;

    function sendMessage(message) {
        var jsonMsg = JSON.stringify( {'name': message});
        socket.send(jsonMsg);
        console.log("Sent Message: " + jsonMsg);
    }

    function connect(){
        var host     =  document.location.host;
        var pathname =  "/"                   //document.location.pathname;
        var addr     = "ws://" +host  + pathname + "socket"  ;
        //alert("connect addr=" + addr   );

        // Assicura che sia aperta un unica connessione
        if(socket !== undefined && socket.readyState !== WebSocket.CLOSED){
             alert("WARNING: Connessione WebSocket già stabilita");
        }
        socket = new WebSocket(addr);

        socket.onopen = function (event) {
            //console.log("Connected to " + addr);
            //setMessageToWindow(infoDisplay,"Connected to " + addr);
        };

        socket.onmessage = function (event) {
            //alert(`Got Message: ${event.data}`);
            msg = event.data;
            //alert(`Got Message: ${msg}`);
            //console.log("ws-status:" + msg);
            if(msg.includes("LED")){
                setMessageToWindow(led,msg);
            }else if(msg.includes("TROLLEY_POS")){
                setMessageToWindow(trolley_position,msg);
            }else if(msg.includes("TROLLEY_STATUS")){
                setMessageToWindow(trolley_status,msg);
            }else if(msg.includes("ContainersWeight")){
                var plastic = msg.split(":")[1].split(",")[0];
                var glass = msg.split(":")[1].split(",")[1].trim();
                console.log("weights: " + plastic + glass);
                setMessageToWindow(container_p, plastic);
                setMessageToWindow(container_g, glass);
            }
         };
    }//connect

// LED,TROLLEY_POS,TROLLEY_STATUS, ContainersWeight