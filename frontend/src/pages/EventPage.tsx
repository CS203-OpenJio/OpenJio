import { Link, useSearchParams } from "react-router-dom";
import NavBarTest2 from "../components/CentralHub/Section1parts/NavBarTest2";
import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "../components/ui/dialog";
import { Button } from "../components/ui/button";
import JWT from "../utils/JWT";

export default function EventPage() {
  // does a GET request, sets it in PostData variable

  const [event, setEvent] = useState({} as any);

  // to search for id based on url so we can GET request specific event
  const [searchParams] = useSearchParams();
  const eventId = searchParams.get("id");

  useEffect(() => {
    // Make the Axios GET request when the component mounts
    JWT.get(`http://localhost:8080/api/v1/events/id/${eventId}`)
      .then((response) => {
        setEvent(response.data); // Store the data in the "data" state variable
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <div>
      <NavBarTest2 />
      <div className="h-[35px]"></div>
      <div className="gap-10 w-auto m-4">
        {/* <div className="font-ibm-plex-mono mb-5">
          Welcome <span className="text-xl font-bold">{user.username}</span>!
        </div> */}
        <div className="" key={event?.id}>
          <div className="flex flex-col justify-normal items-center font-ibm-plex-mono ml-14 mr-14">
            <h2 className="text-31xl">{event?.name}</h2>
            <img
              src={event?.image}
              className="h-[400px] w-[300px] rounded-3xl mb-5"
            ></img>
            <div className="flex grow whitespace-break-spaces bg-white font-normal text-4xl p-3 border border-solid border-black rounded-lg m-4">
              <div className="">
                Date: {event?.startDate} to {event?.endDate} |
              </div>
              <div className=""> Venue: {event?.venue} | </div>
              <div className=""> Max Event Capacity: {event?.capacity} </div>
            </div>
            <div className="flex grow text-4xl font-light bg-white border border-solid border-black rounded-lg p-3 m-4">
              {event?.description}
            </div>

            <TicketFooter id={event?.id} />
          </div>
        </div>
      </div>
    </div>
  );
  function TicketFooter({ id }: { id: number }) {
    let body = {
      // this is hardcoded right now, waiting for backend followup
      studentId: 1,
      eventId: id,
    };

    async function handleClick() {
      console.log(body);

      await JWT.post("http://localhost:8080/api/v1/register-event", body).catch(
        (err) => {
          console.log(err.message);
        }
      );
    }

    return (
      <div>
        <Dialog>
          <DialogTrigger asChild>
            <Button
              variant="outline"
              className="hover:cursor-pointer font-ibm-plex-mono"
            >
              Register
            </Button>
          </DialogTrigger>
          <DialogContent className="sm:max-w-[425px]">
            <DialogHeader>
              <DialogTitle className="font-ibm-plex-mono">
                Confirm Registration?
              </DialogTitle>
            </DialogHeader>
            <DialogFooter>
              <Link to="/purchased" state={{ TID: id }}>
                <Button
                  onClick={handleClick}
                  className="hover:cursor-pointer font-ibm-plex-mono"
                >
                  Confirm
                </Button>
              </Link>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>
    );
  }
}
