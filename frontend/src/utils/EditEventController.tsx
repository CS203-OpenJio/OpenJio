import JWT from "../utils/JWT";
import { toast } from 'react-toastify';

const getEvents = async (id: string) => {
    try {
        const response = await JWT.get(`/api/v1/events/id/${id}`);
        return response.data;
    } catch (error) {
        console.log(error);
        throw new Error("Error getting event.");
    }
}

// Update event details
const handleChangeEvent = async (id: string, event: {}) => {
    // if event is empty, throw error
    if (!event) {
        throw new Error("Nothing to Update!");
    }
    try {
        let formData = new FormData();
        // make API call
        if ("image" in event && event["image"] !== null) {
            console.log("image in body")
            let image = event["image"] as Blob;
            delete event["image"];
            formData.append("imageFile", image);
            console.log(image)
        } else if ("image" in event) {
            delete event["image"];
        }
        formData.append("event", JSON.stringify(event));
        console.log(JSON.stringify(event));
        const response = await JWT.put(`/api/v1/events/id/${id}`, formData);
        // if response is successful, return data
        if (response.status === 200) {
            toast.success('Event details updated successfully!');
        } else {
            throw new Error("Error updating event details.");
        }
    } catch (error) {
        throw new Error("Error updating event details.");
    }
};


export { getEvents, handleChangeEvent };