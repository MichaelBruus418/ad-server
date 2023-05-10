import {Controller} from "../Controller.js";

export class EditView {

    #elements = new Map()
    #zoneCounter = 0

    constructor(zoneTabledWrapperId) {
        if (typeof (zoneTabledWrapperId) !== "string") throw new TypeError("String expected")

        // Resolve DOM ids
        let elm = document.getElementById(zoneTabledWrapperId);
        if (elm === null) throw new ReferenceError("Unable to resolve DOM id to element: " + zoneTabledWrapperId)
        this.#elements.set("zoneTableWrapper", elm);
    }

    render() {
        Controller.getAllZones()
            .then(dataA => this.#createZoneTable(dataA))
            .catch(e => {
                console.error(e.name + ": " + e.message)
                alert("Error: Unable to fetch zone data.")
            })
    }

    #createZoneTable(dataA) {
        this.#zoneCounter = 0

        let tableWrapper = this.#elements.get("zoneTableWrapper")
        this.#removeChildren(tableWrapper)

        // Table
        let table = this.#createElm("table", tableWrapper)
        table.className = "zoneTable";
        let thead = this.#createElm("thead", table)

        // Table headers
        let tableHeaders = ["Name", ""]
        tableHeaders.forEach(header => {
            let td = this.#createElm("td", thead)
            this.#setInnerHtml(td, this.#capFirstLetter(header))
        })

        // Table Body
        let tbody = this.#createElm("tbody", table)

        // Table rows
        dataA.forEach(zone => {
            let tr = this.#createTableRow(zone)
            tbody.appendChild(tr)
        })

        // Add eventListener to 'Add Zone' btn.
        let elm = document.getElementById("zoneAddBBtn")
        elm.addEventListener("click", () => this.#listenerAddZone())

    }

    #createTableRow(zone) {
        this.#zoneCounter++
        let tr, td, input
        tr = this.#createElm("tr")

        // Name (text input)
        td = this.#createElm("td", tr)
        input = this.#createElm("input", td)
        input.type = "text"
        input.className = "zoneName"
        input.value = zone.name

        // --- Actions ---
        // Remove Zone button
        td = this.#createElm("td", tr)
        input = this.#createElm("input", td)
        input.type = "button"
        input.className = "zoneRemoveBtn"
        input.value = "-"
        input.addEventListener("click", ((self, counterId) => {
            // Closure is necessary.
            return () => self.#listenerRemoveZone(counterId)
        })(this, this.#zoneCounter))

        return tr;
    }

    #createActions(publisher, parentElm) {
        /* let td = this.#createElm("td", parentElm)
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
         a.addEventListener("click", () => this.#deletePublisher(publisher.id))*/
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
        /* Controller.deletePublisher(id, (numOfRowsDeleted) => {
             console.log("numOfRowsDeleted: " + numOfRowsDeleted)
             if (numOfRowsDeleted !== -1) {
                 this.render()
             } else {
                 alert("Error: Unable to delete publisher.")
             }
         })*/
    }

    #listenerRemoveZone(rowId) {
        console.log("remove: " + rowId)
    }

    #listenerAddZone() {
        console.log("Add zone")
    }


}