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
        // make API call
        if ("image" in event) {
            let image = event["image"] as string;
            delete event["image"];
            event = {
                event:{...event},
                image: image
            }
        }
        const response = await JWT.put(`/api/v1/events/id/${id}`, event);
        // if response is successful, return data
        if (response.status === 200) {
            console.log(JSON.stringify(event));
            toast(JSON.stringify(event));
            toast.success('Event details updated successfully!');
        } else {
            throw new Error("Error updating event details.");
        } 
    } catch (error) {
        toast(JSON.stringify(event));
        console.log(error);
        throw new Error("Error updating event details.");
    }
};


export { getEvents, handleChangeEvent };