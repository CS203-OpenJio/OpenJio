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
          }
        console.log(body);
        await JWT.post("http://localhost:8080/api/v1/organisers/create-event", body).catch(
            (err) => {
                console.log(err.message);
            }
        );

    } catch (err) {
        console.log(err);
        return null;
    }
    return null;
};

export { createEvent };


