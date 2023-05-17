import {Controller} from "../Controller.js";

export class EditView {

    #zoneCounter = 0
    // Html doc is expected to have these ids/elements
    #elements = {
        publisherForm: null,
        publisherNameIpt: null,
        zoneTableWrapper: null,
        zoneAddBtn: null,
    }

    constructor() {
        this.#resolveDomIds(this.#elements)
    }

    render() {
        this.#elements.publisherForm.addEventListener("submit", (e) => this.#sumbitForm(e))

        // PublisherId = 0: create new publisher.
        // PublisherId > 0: edit publisher.
        const publisherId = Controller.getPublisherId()
        if (publisherId === 0) {
            this.#createZoneTable([])
        } else {
            this.#fetchPublisherData(publisherId)
                .then(data => {
                    try {
                        this.#elements.publisherNameIpt.value = data.publisher.name
                        this.#createZoneTable(data.zones)
                    } catch (e) {
                        console.error(e)
                    }
                })
                .catch(e => {
                    console.error(e)
                    alert("Error: Unable to fetch data.")
                })
        }
    }

    #sumbitForm(e) {
        e.preventDefault()
        let data = new FormData(this.#elements.publisherForm)
        Controller.addPublisher(data)
            .then(_ => {
                try {
                    Controller.goToRoute("publisherControllerMain")
                } catch (e) {
                    console.error(e)
                }
            })
            .catch(e => {
                console.error(e)
                alert("Error: Unable to add publisher.")
            })
    }

    async #fetchPublisherData(publisherId) {
        const publisher = await Controller.getPublisherById(publisherId)
        if (publisher === null) throw new Error("PublisherId doesn't exist.")
        const zones = await Controller.getZonesByPublisherId(publisherId)
        return {publisher: publisher, zones: zones}
    }

    #createZoneTable(zones) {
        this.#zoneCounter = 0

        let tableWrapper = this.#elements.zoneTableWrapper
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
        zones.forEach(zone => {
            let tr = this.#createTableRow(zone)
            tbody.appendChild(tr)
        })

        // Add eventListener to 'Add Zone' btn.
        let elm = document.getElementById("zoneAddBtn")
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
        input.disabled = true
        input.addEventListener("click", ((self, counterId) => {
            // Closure is necessary.
            return () => self.#listenerRemoveZone(counterId)
        })(this, this.#zoneCounter))

        return tr;
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

    #listenerRemoveZone(rowId) {
        console.log("remove: " + rowId)
    }

    #listenerAddZone() {
        console.log("Add zone")
    }

    #resolveDomIds(elms) {
        for (let id in elms) {
            if (elms[id] !== null) {
                // Recursive callback.
                this.#resolveDomIds(elms[id]);
            } else {
                // Resolve id
                elms[id] = document.getElementById(id);
                if (elms[id] === null) {
                    throw new ReferenceError("Unable to resolve DOM id: " + id);
                }
            }
        }
    }

}