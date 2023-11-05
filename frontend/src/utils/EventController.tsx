// use this file for API requests relating to event
import JWT from "./JWT";

const getAllEvents = async () => {
	const response = await JWT.get(`/api/v1/events`);

	return response.data;
};

const getEvent = async (eventId: string | null) => {
	const response = await JWT.get(`/api/v1/events/id/${eventId}`)

	console.log(response.data);
	return response.data;
};

const registerEvent = async (eventId: string | null) => {
	await JWT.post("/api/v1/register-event", {
		eventId: eventId,
	}).catch(
		(err) => {
			throw new Error("Error: " + err.response.data.details);
		}
	);
}

const createEvent = async (body: {}) => {

	try {
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

			if ("image" in body) {
				console.log("image in body")
				let image = body["image"] as Blob;
				delete body["image"];
				let formData = new FormData();
				formData.append("imageFile", image);
				// print body to console
				formData.append("event", JSON.stringify(body));
				console.log(image)
				console.log(JSON.stringify(body))
				await JWT.post("/api/v1/organisers/create-event", formData).catch(
					(err) => {
						throw err;
					}
				);
			} else {
				throw new Error("Error: Image is required");
			}
		}

	} catch (err) {
		throw err;
	}
};

export { getAllEvents, getEvent, registerEvent, createEvent };
