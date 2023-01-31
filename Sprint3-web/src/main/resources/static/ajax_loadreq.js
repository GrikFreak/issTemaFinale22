function callback(theXhr)
{
    if(theXhr.readyState===4 && theXhr.status===200)
    {
        var risposta = theXhr.responseText;
        //console.log(risposta);
        document.getElementById("response").value=risposta;
    }
}

function sendloadreqAjax(theXhr, url, parametri){
    theXhr.onreadystatechange = function(){ callback(theXhr); };
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