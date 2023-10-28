import { Link, useSearchParams } from "react-router-dom";
import NavBar from "../components/NavBar";
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
import { set } from "date-fns";

export default function EventPage() {
  // does a GET request, sets it in PostData variable
  const navigate = useNavigate();
  const [event, setEvent] = useState({} as any);

  // to search for id based on url so we can GET request specific event
  const [searchParams] = useSearchParams();
  const eventId = searchParams.get("id");
  const [userType, setUser] = useState({} as any);

  useEffect(() => {
    // Make the Axios GET request when the component mounts
    const userType = localStorage.getItem("userType") || "";
    setUser(userType);
    // if unauthorized, redirect to login page
    if (userType === "") {
      navigate("/unauthorized");
    }
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
      <NavBar />
      <div className="h-[35px]"></div>
      <div className=" m-4">
        {/* <div className="font-ibm-plex-mono mb-5">
          Welcome <span className="text-xl font-bold">{user.username}</span>!
        </div> */}
        <div className="" key={event?.id}>
          <div className="flex flex-row justify-normal font-ibm-plex-mono w-[70%] m-auto mt-24">
            <img
              src={event?.image}
              className="rounded-3xl mx-auto object-contain w-[500px] mr-16"
            ></img>
            <div className="flex flex-col bg-white font-normal text-4xl p-3 border border-solid border-black rounded-lg mx-auto mt-8">
            <h2 className="text-28xl font-ibm-plex-mono mx-auto">{event?.name}</h2>
              <div className="ml-8 mt-8">
                Date: {new Date(event?.startDateTime).toLocaleDateString()} to {new Date(event?.endDateTime).toLocaleDateString()}
              </div>
              <div className="ml-8">Venue: {event?.venue}</div>
              <div className="ml-8">Event Capacity: {event?.capacity}</div>
              <div className="font-light -mb-8 mx-8 text-xl mt-12">Description</div>
              <p className="flex grow text-lg font-mono font-light bg-white border border-solid border-black rounded-lg p-3 mt-8 tracking-tight mx-8">
                {event?.description}
              </p>
              <div className="flex flex-row justify-end mt-8">
                 {userType == "STUDENT" && <TicketFooter id={event?.id} />}
              </div>

            </div>



          </div>
        </div>
      </div>
    </div>
  );
  function TicketFooter({ id }: { id: number }) {
    let body = {
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
              className="hover:cursor-pointer font-ibm-plex-mono bg-black text-white shadow-lg"
            >
              Register
            </Button>
          </DialogTrigger>
          <DialogContent className="sm:max-w-[425px]">
            <DialogHeader>
              <DialogTitle className="font-ibm-plex-mono tracking-wide">
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
