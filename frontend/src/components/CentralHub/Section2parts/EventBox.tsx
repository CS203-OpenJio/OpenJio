import EventPost from "./EventPost";
import { useEffect, useState } from "react";
import axios from "axios";

const EventBox: React.FC = () => {
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [events, setEvents] = useState([]);

  useEffect(() => {
    // Make the Axios GET request when the component mounts
    axios
      .get(`http://localhost:8080/api/v1/events`, {
        headers: {
          Authorization: "Basic " + btoa(`admin@admin.com:admin`),
        },
      })
      .then((response) => {
        console.log(response.data);
        setEvents(response.data); // Store the data in the "data" state variable
        setLoading(false);
      })
      .catch((err) => {
        setError(err);
        setLoading(false);
      });
  }, []);

  return (
    <div className="bg-white border border-1 round border-black flex flex-column justify-center items-center">
      {events.map((event: any) => (
        <EventPost
          key={event?.id}
          eventNumber={event?.id}
          imgPath={event?.image}
          title={event?.name}
          date={event?.startDate}
          time="5.00pm - 6.30pm"
        />
      ))}
      {/* <EventPost title="" date="" time="" />
      <EventPost title="" date="" time="" />
      <EventPost title="" date="" time="" />
      <EventPost title="" date="" time="" /> */}
    </div>
  );
};

export default EventBox;
