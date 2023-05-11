import {CookieHelper} from "../helpers/CookieHelper.js";

export class PublisherService {

    static #baseUrl = "/api/publisher"
    static #tokenCookieName = "AD-SERVER_CSRF-Token"

    static async getAll() {
        let url = this.#baseUrl
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

    static async get(id) {
        if (typeof (id) !== "number") throw new TypeError("Number expected.")
        let url = this.#baseUrl + "/" + id
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

    /*
    * Returns inserted id.
    * */
    static async add(formData) {
        if (!formData instanceof FormData) throw new TypeError("FormData expected.")
        let url = this.#baseUrl + "/add"
        const response = await fetch(url, {
            method: 'POST',
            mode: 'cors',
            credentials: 'same-origin',
            cache: 'no-cache',
            headers: {
                'X-CSRF-Token': CookieHelper.getCookie(this.#tokenCookieName),
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams(formData)
        });

        if (!response.ok) {
            let msg = response.status + " " + response.statusText ;
            if (response.headers.get("content-type")?.includes("text/plain")) msg += "\n" + await(response.text())
            throw new Error(msg)
        }

        return response.text().then(v => parseInt(v));
    }

    /*
    * Returns num of rows deleted.
    * */
    static async delete(id) {
        if (typeof (id) !== "number") throw new TypeError("Number expected.")
        let url = this.#baseUrl + "/delete/" + id
        const response = await fetch(url, {
            method: 'DELETE',
            mode: 'cors',
            credentials: 'same-origin',
            cache: 'no-cache',
            headers: {
                'X-CSRF-Token': CookieHelper.getCookie(this.#tokenCookieName),
            }
        })

        if (!response.ok) {
            let msg = response.status + " " + response.statusText ;
            if (response.headers.get("content-type")?.includes("text/plain")) msg += "\n" + await(response.text())
            throw new Error(msg)
        }

        return response.text().then(v => parseInt(v));
    }

}