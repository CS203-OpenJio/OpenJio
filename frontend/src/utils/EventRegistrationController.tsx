// use this file for API requests relating to event registration
import JWT from "./JWT";

const createEvent = async (body: {}) => {

    try {
        if ("image" in body) {
            delete body["image"];
            body = {
                ...body,
            }
            if ("date" in body) {
                let date = body["date"] as { from?: string, to?: string };
                delete body["date"];
                //format date below
                let dateTo = new Date(date?.to ?? "");
                var userTimezoneOffset1 = dateTo.getTimezoneOffset() * 60000;
                dateTo = new Date(dateTo.getTime() - userTimezoneOffset1);
                let dateFrom = new Date(date?.from ?? "");
                var userTimezoneOffset2 = dateFrom.getTimezoneOffset() * 60000;
                dateFrom = new Date(dateFrom.getTime() - userTimezoneOffset2);
                body = {
                    ...body,
                    startDateTime: dateFrom,
                    endDateTime: dateTo
                }
            }
            console.log(body);
        await JWT.post("http://localhost:8080/api/v1/organisers/create-event", body).catch(
            (err) => {
                throw err;
            }
        );
        }

    } catch (err) {
        throw err;
    }
};

export { createEvent };


