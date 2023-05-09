
export class CookieHelper {

    static getCookie(name) {
        if (typeof(name) !== "string") throw new TypeError("String expected")
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
        return null
    }

}
