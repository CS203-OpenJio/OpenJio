import NavBarTest2 from "../components/CentralHub/Section1parts/NavBarTest2";
import { useState, useEffect } from "react";
import axios from "axios";

const Schedule = () => {
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [events, setEvents] = useState([]);

  useEffect(() => {
    // Make the Axios GET request when the component mounts
    axios
      .get("http://localhost:8080/api/v1/events")
      .then((response) => {
        setEvents(response.data); // Store the data in the "data" state variable
        setLoading(false);
      })
      .catch((error) => {
        setError(null);
        setLoading(false);
      });
  }, []);

  return (
    <div>
      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p>{error}</p>
      ) : (
        <div>
          <NavBarTest2 />
          <div className="mt-[80px] pb-3 ml-14 font-source-serif-pro text-darkslateblue text-31xl [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)">
            My Schedule
          </div>
          <div className="flex flex-col bg-white mt-100 ml-14 mr-14 border border-solid border-spacing-[0.5px] rounded-lg cursor-default"></div>

          <h2>Items:</h2>
          <ul>
            {events.map((event) => (
              //   <li key={event.id}>{event.event_name}</li>
              <div></div>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default Schedule;
