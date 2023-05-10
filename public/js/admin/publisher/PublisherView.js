import {Controller} from "./Controller.js";

export class PublisherView {

    #elements = new Map();

    constructor(publisherTableWrapperId) {
        if (typeof (publisherTableWrapperId) != "string") throw new TypeError("String expected")

        // Resolve DOM id
        let elm = document.getElementById(publisherTableWrapperId);
        if (elm === null) throw new ReferenceError("Unable to resolve DOM id to element: " + publisherTableWrapperId)
        this.#elements.set("publisherTableWrapper", elm);
    }

    render() {
        Controller.getAllPublishers()
            .then(publishers => this.#createTable(publishers))
            .catch(e => {
                console.error(e.name + ": " + e.message)
                alert("Error: Unable to fetch publisher data.")
            })
    }

    error(e) {

    }

    #createTable(publishers) {

        // Prepare table headers
        // We get table headers from publisher properties.
        // Define properties we don't want to print.
        let removePublisherHeaders = []
        // Additional headers (after property headers).
        let additionalHeaders = [""]

        let tableWrapper = this.#elements.get("publisherTableWrapper")
        this.#removeChildren(tableWrapper)

        // Table
        let table = this.#createElm("table", tableWrapper)
        table.className = "publisherTable";
        let thead = this.#createElm("thead", table)

        // Table headers
        let publisherHeaders = []
        if (publishers.length !== 0) {
            publisherHeaders = Object.keys(publishers[0]).filter(v => removePublisherHeaders.indexOf(v) === -1)
        }
        let allHeaders = publisherHeaders.concat(additionalHeaders)
        allHeaders.forEach(header => {
            let td = this.#createElm("td", thead)
            this.#setInnerHtml(td,this.#capFirstLetter(header))
        })

        // Table Body
        let tbody = this.#createElm("tbody", table)

        // Table rows
        publishers.forEach(publisher => {
            let tr = this.#createTableRow(publisher, publisherHeaders, additionalHeaders)
            tbody.appendChild(tr)
        })

    }

    #createTableRow(publisher, publisherHeaders) {
        let tr = this.#createElm("tr")
        publisherHeaders.forEach(header => {
            let td = this.#createElm("td", tr)
            this.#setInnerHtml(td, publisher[header])
        })
        // Add actions
        this.#createActions(publisher, tr)
        return tr;
    }

    #createActions(publisher, parentElm) {
        let td = this.#createElm("td", parentElm)
        let a;
        // Edit
        a = this.#createElm("a", td)
        a.href = "javascript:void(0);"
        a.className = "action edit"
        a.innerHTML = "Edit"
        a.addEventListener("click", () => this.#editPublisher(publisher.id))
        td.insertAdjacentHTML("beforeend", " ")
        // Delete
        a = this.#createElm("a", td)
        a.href = "javascript:void(0);"
        a.className = "action delete"
        a.innerHTML = "Delete"
        a.addEventListener("click", () => this.#deletePublisher(publisher.id))
    }

    #createElm(tagName, parentElm = null) {
        if (typeof (tagName) !== "string") throw new TypeError("String expected.")
        if (parentElm !== null && !parentElm instanceof HTMLElement) throw new TypeError("HTMLElement expected.")
        let elm = document.createElement(tagName)
        if (parentElm !== null) parentElm.appendChild(elm)
        return elm;
    }

    #setInnerHtml(elm, value, defaultValue = "&nbsp;") {
        if (value !== "") {
            elm.innerHTML = value
        } else {
            elm.innerHTML = defaultValue
        }
    }

    #capFirstLetter(src) {
        return src.charAt(0).toUpperCase() + src.slice(1);
    }

    #removeChildren(parentElm) {
        while (parentElm.firstChild) parentElm.removeChild(parentElm.firstChild);
    }

    #deletePublisher(id) {
        Controller.deletePublisher(id, (numOfRowsDeleted) => {
            console.log("numOfRowsDeleted: " + numOfRowsDeleted)
            if (numOfRowsDeleted !== -1) {
                this.render()
            } else {
                alert("Error: Unable to delete publisher.")
            }
        })
    }

    #editPublisher(id) {
        alert("Edit " + id)
    }

}

