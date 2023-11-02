// use this file for API requests relating to event
import JWT from "./JWT";

const getAllEvents = async () => {
  const response = await JWT.get(`/api/v1/events`);

  return response.data;
};

export { getAllEvents };
