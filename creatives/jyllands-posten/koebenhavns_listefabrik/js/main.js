"use strict";

window.addEventListener("DOMContentLoaded", () => {
    const elm = document.getElementById("dynamic");
    if (!elm instanceof HTMLElement) throw new ReferenceError("Unable to retrieve img element.")
    elm.style.display = "inline-block";

    const masterList = [
        "koebenhavns-listefabrik_size_300x600px_version_01.jpg",
        "koebenhavns-listefabrik_size_300x600px_version_02.jpg",
        "koebenhavns-listefabrik_size_300x600px_version_03.jpg",
        "koebenhavns-listefabrik_size_300x600px_version_04.jpg",
        "koebenhavns-listefabrik_size_300x600px_version_05.jpg",
    ];

    let workingList = [];
    let currImage = null;

    randomImageCycle()
    setInterval(randomImageCycle, 5000)

    function randomImageCycle() {
        if (workingList.length === 0) workingList = masterList.slice();
        let randIdx = Math.floor(Math.random() * workingList.length);
        if (currImage === workingList[randIdx]) randIdx = (randIdx + 1) % workingList.length
        currImage = workingList[randIdx]
        workingList.splice(randIdx, 1)
        elm.src = "images/" + currImage;
    }

});

