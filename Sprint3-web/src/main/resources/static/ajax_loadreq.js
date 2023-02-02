function sendfreeindoor(){
    var xhr= new XMLHttpRequest();
    var url="/free_indoor";
    var parametri = "FREEEE";
    //console.log(parametri)
    if(xhr)
    {
        sendloadreqAjax(xhr, url, parametri);
    }
    else
    {
        alert("Impossibile usare ajax per le richieste");
    }
}

function callback(theXhr)
{
    if(theXhr.readyState===4 && theXhr.status===200)
    {
        var risposta = theXhr.responseText;
        console.log(risposta);

        if (risposta.includes("loadaccept") || risposta.includes("loadrejected")){
            document.getElementById("response").value=risposta;
            if (risposta.includes("loadaccept")){
                sendfreeindoor();
            }
        }else{
            console.log("free_indoor"+risposta);
            document.getElementById("free_indoor").value=risposta;
        }
    }
}
function callbackFreeIndoor(theXhr)
{
    if(theXhr.readyState===4 && theXhr.status===200)
    {
        var risposta = theXhr.responseText;
        console.log(risposta);

        if (risposta.includes("loadaccept") || risposta.includes("loadrejected")){
            document.getElementById("response").value=risposta;
            if (risposta.includes("loadaccept")){
                sendfreeindoor();
            }
        }else{
            console.log("free_indoor"+risposta);
            document.getElementById("free_indoor").value=risposta;
        }
    }
}

function callbackStopResume(theXhr)
{
    console.log("ARRIVATA" + theXhr.responseText)
}

function sendloadreqAjax(theXhr, url, parametri){
    if (url.includes("waste_request")){
        console.log("sono entrato 2");
        theXhr.onreadystatechange = function(){ callback(theXhr); };
    }else{
        console.log("sono entrato 3");
        theXhr.onreadystatechange = function(){ callbackFreeIndoor(theXhr); };
    }


    try
    {
        theXhr.open("POST", url, true);
    }
    catch(e)
    {
        alert(e);
    }
    theXhr.setRequestHeader("connection", "close");
    theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    theXhr.send(parametri);
}

function sendstopresumeAjax(theXhr, url, parametri){

    theXhr.onreadystatechange = function(){ callbackStopResume(theXhr); }

    try
    {
        theXhr.open("POST", url, true);
    }
    catch(e)
    {
        alert(e);
    }
    theXhr.setRequestHeader("connection", "close");
    theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    theXhr.send(parametri);
}


function sendloadreq(){
    var xhr= new XMLHttpRequest();
    var url="/waste_request";
    var tipo = document.getElementById("type").value;
    var qta = document.getElementById("quantity").value;
    var parametri = "type="+tipo+"&quantity="+qta;
    //console.log(parametri)
    if(xhr)
    {
        sendloadreqAjax(xhr, url, parametri);
    }
    else
    {
        alert("Impossibile usare ajax per le richieste");
    }
}

function sendstopresume(){
    var xhr= new XMLHttpRequest();
    var url="/stop_resume";
    var distance = document.getElementById("distance").value;
    var parametri = "distance="+distance;
    //console.log(parametri)
    if(xhr)
    {
        sendstopresumeAjax(xhr, url, parametri);
    }
    else
    {
        alert("Impossibile usare ajax per le richieste");
    }
}
