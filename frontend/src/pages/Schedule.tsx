import NavBarTest2 from "../components/CentralHub/Section1parts/NavBarTest2";
import { useState, useEffect, useContext } from "react";
import axios from "axios";
import { AuthContext } from "src/App";

const Schedule = () => {
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [registeredEvents, setRegisteredEvents] = useState([]);

  const { user, setUser } = useContext(AuthContext);
  const username = user.username;
  const password = user.password;
  //need to store id somewhere
  const userID = user.userId;

  useEffect(() => {
    console.log(username, password, userID);
    // Make the Axios GET request when the component mounts
    axios
      .get(`http://localhost:8080/api/v1/register/student/${userID}`, {
        headers: {
          Authorization: "Basic " + btoa(`${user.username}:${user.password}`),
        },
      })
      .then((response) => {
        setRegisteredEvents(response.data); // Store the data in the "data" state variable
        setLoading(false);
        console.log(response.data);
      })
      .catch((error) => {
        setError(null);
        setLoading(false);
      });
  }, []);

  const findEvent = () => {};

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
            {registeredEvents.map((registeredEvent: any) => (
              <div>{registeredEvent?.name}</div>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default Schedule;
