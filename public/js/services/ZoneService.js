
export class ZoneService {

    static #baseUrl = "/api/zone"

    static async getAll() {
        let url = this.#baseUrl
        const response = await fetch(url, {
            method: 'GET',
            mode: 'cors',
            credentials: 'same-origin',
            cache: 'no-cache'
        });

        if (!response.ok) {
            let msg = response.status + " " + response.statusText ;
            if (response.headers.get("content-type")?.includes("text/plain")) msg += "\n" + await(response.text())
            throw new Error(msg)
        }

        return response.json();
    }

    static async getByPublisherId(publisherId) {
        let url = this.#baseUrl + "/publisher/" + publisherId
        const response = await fetch(url, {
            method: 'GET',
            mode: 'cors',
            credentials: 'same-origin',
            cache: 'no-cache',
        });

        if (!response.ok) {
            let msg = response.status + " " + response.statusText ;
            if (response.headers.get("content-type")?.includes("text/plain")) msg += "\n" + await(response.text())
            throw new Error(msg)
        }

        return response.json();
    }

}