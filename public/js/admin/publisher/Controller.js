import {PublisherService} from "../../services/PublisherService.js";
import {ZoneService} from "../../services/ZoneService.js";

export class Controller {

    static getPublisherId() {
        if (typeof(publisherId) !== "number") throw new ReferenceError("Expected global const 'publisherId' not available.")
        return publisherId
    }

    static getAllPublishers() {
        return PublisherService.getAll()
    }

    static getPublisherById(id) {
        if (typeof(id) !== "number") throw new TypeError("Number expected.")
        return PublisherService.get(id)
    }

    static addPublisher(formData) {
        if (!formData instanceof FormData) throw new TypeError("FormData expected.")
        return PublisherService.add(formData)
    }

    static deletePublisher(id, listener = null) {
        if (typeof (listener) !== null && typeof (listener) !== "function") throw new TypeError("Function expected.")
        PublisherService.delete(id)
            .then((numOfRowsDeleted) => {
                listener?.(numOfRowsDeleted);
            })
            .catch(e => {
                console.error(e)
                listener?.(-1);
            })
    }

    static getAllZones() {
        return ZoneService.getAll()
    }

    static getZonesByPublisherId(publisherId) {
        return ZoneService.getByPublisherId(publisherId)
    }

    static goToRoute(routeKey, params = "") {
        if (typeof(routeKey) !== "string") throw new TypeError("String expected.")
        if (typeof(routes) !== "object") throw new ReferenceError("Global const 'routes' not available.")
        if (!routes.has(routeKey)) throw new ReferenceError("Unrecognized key.")
        document.location.href = routes.get(routeKey) + params
    }
}


