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
import { getSchedule } from "../utils/ScheduleController";

const Schedule = () => {
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [registeredEvents, setRegisteredEvents] = useState([]);
  const [email, setEmail] = useState(null);

  useEffect(() => {
    // Make the Axios GET request when the component mounts
    getSchedule().then((response: any) => {
      console.log(response)
      setRegisteredEvents(response); // Store the data in the "data" state variable
      setLoading(false);
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
                      <TableHead className="w-[70px]">Event Id</TableHead>
                      <TableHead>Event Name</TableHead>
                      <TableHead>Venue</TableHead>
                      <TableHead>Date</TableHead>
                      <TableHead>Status</TableHead>
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
                        <TableCell>
                          {registeredEvent.startDate}
                        </TableCell>
                        <TableCell>
                          {/* Not sure why registrations for an event is an array? */}
                          {registeredEvent.registrations[0].status} 
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
