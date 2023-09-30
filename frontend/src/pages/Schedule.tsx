import NavBarTest2 from "../components/CentralHub/Section1parts/NavBarTest2";
import { useState, useEffect } from "react";
import axios from "axios";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "src/components/ui/table";
import JWT from "../utils/JWT";

const Schedule = () => {
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [registeredEvents, setRegisteredEvents] = useState([]);

  useEffect(() => {
    // Make the Axios GET request when the component mounts
    JWT.get(`http://localhost:8080/api/v1/register-event/student/${1}`) // CURRENTLY HARD CODED UPDATE SOON!
      .then((response: any) => {
        setRegisteredEvents(response.data); // Store the data in the "data" state variable
        setLoading(false);
        console.log(response.data);
      })
      .catch((error: any) => {
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
          <div className="flex flex-col bg-white mt-100 ml-14 mr-14 border border-solid border-spacing-[0.5px] rounded-lg cursor-default">
            {" "}
            <div className="font-ibm-plex-mono text-4xl">
              <div>
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead className="w-[100px]">Event Id</TableHead>
                      <TableHead>Event Name</TableHead>
                      <TableHead>Venue</TableHead>
                      <TableHead className="text-right">Date</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {registeredEvents.map((registeredEvent: any) => (
                      <TableRow key={registeredEvent.name}>
                        <TableCell className="font-medium">
                          {registeredEvent.id}
                        </TableCell>
                        <TableCell>{registeredEvent.name}</TableCell>
                        <TableCell>{registeredEvent.venue}</TableCell>
                        <TableCell className="text-right">
                          {registeredEvent.startDate}
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Schedule;
