import {MainView} from "./MainView.js";
import {PublisherService} from "../../services/PublisherService.js";

export class Controller {

    static #mainView;

    static main() {
        this.#mainView = new MainView("publisherTableWrapper")
        this.#mainView.render()
    }

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
}


