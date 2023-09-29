// use this file for API requests relating to event
import JWT from "./JWT";

const getAllEvents = async () => {
  const response = await JWT.get(`http://localhost:8080/api/v1/events`);

  return response.data;
};

export { getAllEvents };
