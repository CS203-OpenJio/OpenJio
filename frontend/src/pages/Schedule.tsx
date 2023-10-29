import NavBar from "../components/NavBar";
import { useState, useEffect } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "src/components/ui/table";
import { getSchedule } from "../utils/ScheduleController";
import { register } from "module";
import { FormControlLabel, Switch, SwitchProps, styled } from "@mui/material";

const Schedule = () => {
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [registeredEvents, setRegisteredEvents] = useState([]);
  const [completed, setCompleted] = useState(false);

  useEffect(() => {
    // Make the Axios GET request when the component mounts
    getSchedule().then((response: any) => {
      setRegisteredEvents(response); // Store the data in the "data" state variable
      setLoading(false);
    })
      .catch((error: any) => {
        setError(null);
        setLoading(false);
      });
  }, []);

  const findEvent = () => { };

  return (
    <div>
      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p>{error}</p>
      ) : (
        <div>
          <NavBar />
          <div className="flex justify-between mt-[80px]">
            <div className="pb-3 ml-14 font-source-serif-pro text-darkslateblue text-31xl [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)">
              My Schedule
            </div>
            <div className="mr-14">
              <FormControlLabel
                control={<IOSSwitch sx={{ m: 2 }} defaultChecked={completed} />}
                label="View Completed Events"
                labelPlacement="start"
                onChange={() => setCompleted(!completed)}
              />
            </div>
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
                      completed === registeredEvent.completed && <TableRow key={registeredEvent.name}>
                        <TableCell className="font-medium">
                          {registeredEvent.id}
                        </TableCell>
                        <TableCell>{registeredEvent.name}</TableCell>
                        <TableCell>{registeredEvent.venue}</TableCell>
                        <TableCell>
                          {new Date(registeredEvent.startDateTime).toLocaleString()}
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

const IOSSwitch = styled((props: SwitchProps) => (
  <Switch focusVisibleClassName=".Mui-focusVisible" disableRipple {...props} />
))(({ theme }) => ({
  width: 42,
  height: 26,
  padding: 0,
  '& .MuiSwitch-switchBase': {
    padding: 0,
    margin: 2,
    transitionDuration: '300ms',
    '&.Mui-checked': {
      transform: 'translateX(16px)',
      color: '#fff',
      '& + .MuiSwitch-track': {
        backgroundColor: theme.palette.mode === 'dark' ? '#2ECA45' : '#65C466',
        opacity: 1,
        border: 0,
      },
      '&.Mui-disabled + .MuiSwitch-track': {
        opacity: 0.5,
      },
    },
    '&.Mui-focusVisible .MuiSwitch-thumb': {
      color: '#33cf4d',
      border: '6px solid #fff',
    },
    '&.Mui-disabled .MuiSwitch-thumb': {
      color:
        theme.palette.mode === 'light'
          ? theme.palette.grey[100]
          : theme.palette.grey[600],
    },
    '&.Mui-disabled + .MuiSwitch-track': {
      opacity: theme.palette.mode === 'light' ? 0.7 : 0.3,
    },
  },
  '& .MuiSwitch-thumb': {
    boxSizing: 'border-box',
    width: 22,
    height: 22,
  },
  '& .MuiSwitch-track': {
    borderRadius: 26 / 2,
    backgroundColor: theme.palette.mode === 'light' ? '#E9E9EA' : '#39393D',
    opacity: 1,
    transition: theme.transitions.create(['background-color'], {
      duration: 500,
    }),
  },
}));

export default Schedule;
