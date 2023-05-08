

export class PublisherService {

    static async getAll() {
        const response = await fetch("/api/publisher");

        if (!response.ok) {
            throw new Error(response.status.toString())
        }

        return await response.json();
    }

}