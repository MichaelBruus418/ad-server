import {PublisherService} from "../../../services/PublisherService.js";

export class DisplayHelper {
    static render(wrapperId) {
        if (typeof(wrapperId) != "string") throw new TypeError()

        let publishers = PublisherService.getAll()
        try {
            publishers.then(dataA => this.#createTable(wrapperId, dataA))
        }
        catch (e) {
            console.error(e.name + ": " + e.message)
        }
    }

    static #createTable(wrapperId, dataA) {

        let wrapper = document.getElementById(wrapperId)
        if (wrapper == null) {
            throw new ReferenceError("Unable to resolve DOM id: " + wrapperId);
        }

        let table = this.#createElm("table")
        let thead = this.#createElm("thead")
        let tr = this.#createElm("tr")
        let tbody = this.#createElm("tbody")

        table.className = "publisherTable";

        wrapper.appendChild(table)
        table.appendChild(thead)
        thead.appendChild(tr)
        table.appendChild(tbody)

        let properties = Object.keys(dataA[0]);

        // Populate Table Header
        properties.forEach(key => {
            let td = this.#createElm("td")
            td.appendChild(document.createTextNode(this.#capFirstLetter(key)))
            tr.appendChild(td)
        })

        // Populate Table Body
        for (let publisher of dataA) {
            let tr = this.#createElm("tr")
            tbody.appendChild(tr)
            properties.forEach(key => {
                let td = this.#createElm("td")
                tr.appendChild(td)
                td.appendChild(document.createTextNode(publisher[key]))
            })
        }
    }

    static #createElm(tag) {
        return document.createElement(tag)
    }

    static #capFirstLetter(src) {
        return src.charAt(0).toUpperCase() + src.slice(1);
    }

}

