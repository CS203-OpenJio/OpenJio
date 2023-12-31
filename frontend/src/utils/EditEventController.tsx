import JWT from "../utils/JWT";
import { toast } from 'react-toastify';

const getEvents = async (id: string) => {
    try {
        const response = await JWT.get(`/api/v1/events/${id}`);
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
            let image = event["image"] as Blob;
            delete event["image"];
            formData.append("imageFile", image);
        } else if ("image" in event) {
            delete event["image"];
        }
        formData.append("event", JSON.stringify(event));
        const response = await JWT.put(`/api/v1/events/${id}`, formData);
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

const deleteEvent = async (id: string) => {
    try {
        const response = await JWT.delete(`/api/v1/events/${id}`)
        if (response.status === 200) {
            toast.success('Event deleted successfully');
        } else {
            throw new Error("Error deleting event.");
        }
    } catch (error: any) {
        throw new Error("Error connecting to server.");
    }


}


export { getEvents, handleChangeEvent, deleteEvent };