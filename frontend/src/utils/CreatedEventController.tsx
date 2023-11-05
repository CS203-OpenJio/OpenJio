// use this file for API requests relating to registrations
import JWT from "./JWT";
import jwt_decode from "jwt-decode";

interface JwtPayload {
    sub: string; // Assuming 'sub' is the user's email address
    // Add other properties from your JWT payload if needed
  }

const getEvents = async () => {
    const token:any = localStorage.getItem("token");
    try {
        const objToken:JwtPayload = jwt_decode(token);
        const userEmail:string = objToken.sub;

        const response = await JWT.post(`/api/v1/organisers/email/events`, {email: userEmail});
        return response.data;
    } catch(err) {
        console.log(err);
        return null;
    }
};

const getParticipants = async (id: string) => {
    try {
        const response = await JWT.post(`/api/v1/events/registrations`, {eventId: id});
        return response.data;
    } catch (error) {
        console.log(error);
        throw new Error("Error getting participants.");
    }
};

const allocateSlots = async (id: string, algo:string | null) => {
    console.log(id)
    try {
        await JWT.post(`/api/v1/organisers/events/allocation`, {eventId: id, algo: algo}); 
    } catch (error:any) {
        console.log(error);
        if (error.response.data.details) {
        throw new Error(error.response.data.details);
        } else {
        throw new Error("Error allocating slots.");
        }
    }
}

const getStatusParticipants = async (id: string, status:string) => {
    try {
        const response = await JWT.post(`/api/v1/events/registrations`, {eventId: id, status: status});
        return response.data;
    } catch (error) {
        console.log(error);
        throw new Error("Error getting participants.");
    }
}

const closeEvent = async (id: string) => {
    try {
        await JWT.post(`/api/v1/organisers/events/complete`, {eventId: id});
    } catch (error) {
        console.log(error);
        throw new Error("Error closing event.");
    }
}   

export { getEvents, getParticipants, allocateSlots, getStatusParticipants, closeEvent };
