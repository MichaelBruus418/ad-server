"use strict";

window.addEventListener("DOMContentLoaded", () => {
    const elm = document.getElementById("dynamic");
    if (!elm instanceof HTMLElement) throw new ReferenceError("Unable to retrieve img element.")
    elm.style.display = "inline-block";

    const images = [
        "sjec-danmark_size_930x600px_version_01.jpg",
        "sjec-danmark_size_930x600px_version_02.jpg",
    ];

    let randIdx = Math.floor(Math.random() * images.length);
    elm.src = "./images/" + images[randIdx];
});

