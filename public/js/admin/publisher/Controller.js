import {PublisherService} from "../../services/PublisherService.js";
import {ZoneService} from "../../services/ZoneService.js";

export class Controller {

    static getAllPublishers() {
        return PublisherService.getAll()
    }

    static deletePublisher(id, listener = null) {
        if (typeof (listener) !== null && typeof (listener) !== "function") throw new TypeError("Function expected.")
        PublisherService.delete(id)
            .then((numOfRowsDeleted) => {
                listener?.(numOfRowsDeleted);
            })
            .catch(e => {
                console.log(e)
                listener?.(-1);
            })
    }

    static editPublisher(id) {
        console.log("Edit " + id)
    }

    static getAllZones() {
        return ZoneService.getAll()
    }
}


