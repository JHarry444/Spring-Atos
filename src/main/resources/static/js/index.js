"use strict"; // forces ES6 compliance in web browser

const output = document.getElementById("output");

fetch("/hello")
    .then(function (response) {
        return response.text();
    }).then(function (text) {
        console.log(text);
    })
    .catch(function (error) {
        console.error(error);
    });

const form = document.getElementById("createDoggo");

form.addEventListener("submit", function (event) {
    event.preventDefault(); //disables default form submission

    const data = {
        breed: form.breed.value,
        age: form.age.value,
        colour: form.colour.value,
        size: form.size.value
    };

    fetch("/create", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(function (response) {
            return response.json();
        })
        .then(function(doggo){
            debugger;
            console.log("Doggo:", doggo);
            getDoggos();
            form.reset();
        })
        .catch(function (error) {
            console.error(error);
        })

});

const breedFilter = document.getElementById("breedFilter");

breedFilter.addEventListener("change", function() {
    getDoggos();
})

function getDoggos() {
    let url = "/readAll";
    if (breedFilter.value) {
        url += `/${breedFilter.value}`;
    }
    fetch(url)
        .then(function (response) {
            return response.json();
        }).then(function (doggos) {
            output.innerText = ""; // blank the ouput before writing to it
            for (let doggo of doggos) {
                const newDog = document.createElement("p");
                newDog.innerText = `Breed: ${doggo.breed} Age: ${doggo.age}`;
                output.appendChild(newDog);
            }
        }).catch(function (error) {
            console.error(error);
        });
}

getDoggos();