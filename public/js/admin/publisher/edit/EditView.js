import {Controller} from "../Controller.js";

export class EditView {

    #elements = new Map()
    #zoneCounter = 0

    constructor(zoneGridWrapperId) {
        if (typeof(zoneGridWrapperId) !== "string") throw new TypeError("String expected")

        // Resolve DOM ids
        let elm = document.getElementById(zoneGridWrapperId);
        if (elm === null) throw new ReferenceError("Unable to resolve DOM id to element: " + zoneGridWrapperId)
        this.#elements.set("zoneGridWrapper", elm);
    }

    render() {
       Controller.getAllZones()
           .then(dataA => this.#createZones(dataA))
           .catch(e => {
               console.error(e.name + ": " + e.message)
               alert("Error: Unable to fetch zone data.")
           })
    }

    #createZones(dataA) {
        this.#zoneCounter = 0
        let gridWrapper = this.#elements.get("zoneGridWrapper")
        this.#removeChildren(gridWrapper)

        dataA.forEach(zone => {
            let div = this.#createZoneGrid(zone)
            gridWrapper.appendChild(div)
        })
    }

    /*
    * Each Zone has its own layout grid.
    * There's only one row per grid.
    * */
    #createZoneGrid(zone) {
        this.#zoneCounter++

        // Create grid/row
        let zoneGrid = this.#createElm("div")
        zoneGrid.id = "grid_" + this.#zoneCounter
        zoneGrid.className = "zoneGrid"
        let zoneRowLabel = this.#createElm("div", zoneGrid)
        zoneRowLabel.className = "zoneRowLabel"
        let zoneRowInput = this.#createElm("div", zoneGrid)
        zoneRowInput.className = "zoneRowInput"
        let zoneRowButton = this.#createElm("div", zoneGrid)
        zoneRowButton.className = "zoneRowButton"

        // --- Add content ---
        // Label
        let label = this.#createElm("label", zoneRowLabel)
        label.for = "row_" + this.#zoneCounter
        label.innerHTML = "Zone:"
        // Text input
        let input
        input = this.#createElm("input", zoneRowInput)
        input.type = "text"
        input.id = label.for
        input.className = "zoneName"
        input.value = zone.name
        // Remove button
        input = this.#createElm("input", zoneRowButton)
        input.type = "button"
        input.className = "zoneAddBtn"
        input.value = "-"
        input.addEventListener("click", () => this.#listenerRemoveZone(zoneGrid.id))

        return zoneGrid
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
      /*  if (value !== "") {
            elm.innerHTML = value
        } else {
            elm.innerHTML = defaultValue
        }*/
    }

    #capFirstLetter(src) {
        // return src.charAt(0).toUpperCase() + src.slice(1);
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

    #listenerRemoveZone(gridId) {
        console.log("remove: " + gridId)
    }

    #listenerAddZone(gridId) {
        console.log("Add zone")
    }



}