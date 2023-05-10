import {CookieHelper} from "../helpers/CookieHelper.js";

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
        if (!response.ok) throw new Error(response.status.toString());
        return response.json();
    }

    static async getByPublihserId() {
       /* let url = this.#baseUrl
        const response = await fetch(url, {
            method: 'GET',
            mode: 'cors',
            edentials: 'same-origin',
            cache: 'no-cache',
        });
        if (!response.ok) throw new Error(response.status.toString());
        return response.json();*/
    }

    /*
    * Returns num of rows deleted
    * */
    static async delete(id) {
        /*if (typeof(id) !== "number") throw new TypeError("Number expected")
        let url = this.#baseUrl + "/delete/" + id
        const response = await fetch(url, {
            method: 'DELETE',
            mode: 'cors',
            credentials: 'same-origin',
            cache: 'no-cache',
            headers: {
                'X-CSRF-Token': CookieHelper.getCookie("AD-SERVER_CSRF-Token"),
            }
        })
        if (!response.ok) throw new Error(response.status.toString() + " " + response.statusText.toString());
        return response.text().then(v => parseInt(v));*/
    }

}