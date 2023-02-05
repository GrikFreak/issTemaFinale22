/*
ioutils.js
*/

const trolley_status     = document.getElementById("trolley_status");
const trolley_position = document.getElementById("trolley_position");
const led    = document.getElementById("led");
const container_p = document.getElementById("container_p");
const container_g = document.getElementById("container_g");

console.log("ID: ", trolley_status, trolley_position)

function setMessageToWindow(outfield, message) {
    var output = message
    console.log("outfield.id: ", outfield.id)
    if(outfield.id.includes("trolley") || outfield.id.includes("led")){
        outfield.innerHTML = `<tt>${output.split(":")[1]}</tt>`
    } else {
        outfield.innerHTML = `<tt>${output}</tt>`
    }

    if(outfield.id.includes("led")) {
        if(outfield.innerHTML.includes("ON")){
            outfield.parentElement.className = "led stopped";
        } else if(outfield.innerHTML.includes("BLINK")) {
            outfield.parentElement.className = "led active";
        }    else {
            outfield.parentElement.className = "led";
        }
    }
}

function addMessageToWindow(outfield, message) {
    var output = message.replace("\n", "<br/>")
    outfield.innerHTML += `<div>${output}</div>`

}
