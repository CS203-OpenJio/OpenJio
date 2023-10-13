// use this file for API requests relating to event
import JWT from "./JWT";

const createEvent = async (body: {}) => {

    try {
        if ("image" in body) {
            delete body["image"];
            body = {
                ...body,
                image: "Hello lol"
            }
            if ("date" in body) {
                let date = body["date"] as { from?: string, to?: string };
                delete body["date"];
                let dateTo = new Date(date?.to ?? "").toISOString();
                let dateFrom = new Date(date?.from ?? "").toISOString();
                body = {
                    ...body,
                    startDateTime: dateFrom,
                    endDateTime: dateTo
                }
            }
            console.log(body);
        await JWT.post("http://localhost:8080/api/v1/organisers/create-event", body).catch(
            (err) => {
                console.log(err.message);
            }
        );
        }

    } catch (err) {
        console.log(err);
        return null;
    }
    return null;
};

export { createEvent };


