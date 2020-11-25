"use strict";

const output = document.getElementById("output");

fetch("/hello")
    .then(res => res.text())
    .then(text => console.log(text))
    .catch(err => console.error(err));

document.getElementById("createTicket").addEventListener("submit", function (event) {
    event.preventDefault();
    const data = {
        breed: this.breed.value,
        age: this.age.value,
        colour: this.colour.value,
        size: this.size.value
    };

    fetch("/create", {
        method: "POST",
        body: JSON.stringify(data),
        mode: "same-origin",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => res.json())
        .then(json => {
            console.log(json);
            this.reset();
            getDoggos();
        }).catch(err => console.error(err));
});

function getDoggos() {
    fetch("/read")
        .then(res => res.json())
        .then(doggos => {
            output.innerText = "";
            doggos.forEach(doggo => {
                const newDog = document.createElement("pre");
                newDog.innerText = JSON.stringify(doggo, null, 2);
                output.appendChild(newDog);
            });
        }).catch(err => console.error(err));
}

getDoggos();