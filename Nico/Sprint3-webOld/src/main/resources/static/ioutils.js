/*
ioutils.js
*/

const trolley_status     = document.getElementById("trolley_status");
const trolley_position = document.getElementById("trolley_position");
const led    = document.getElementById("led");
const container_p = document.getElementById("container_p");
const container_g = document.getElementById("container_g");

function setMessageToWindow(outfield, message) {
    var output = message
    outfield.innerHTML = `<tt>${output}</tt>`
}

function addMessageToWindow(outfield, message) {
    var output = message.replace("\n", "<br/>")
    outfield.innerHTML += `<div>${output}</div>`
}
