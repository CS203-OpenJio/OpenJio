import { Link, useSearchParams } from "react-router-dom";
import NavBarTest2 from "../components/CentralHub/Section1parts/NavBarTest2";
import { useState, useEffect, useContext } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../App";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "../components/ui/dialog";
import { Input } from "../components/ui/input";
import { Label } from "../components/ui/label";
import { Button } from "../components/ui/button";

export default function EventPage() {
  const { user, setUser } = useContext(AuthContext);
  const userName = user.username;
  const password = user.password;
  //need to store id somewhere
  const userID = user.userId;

  const options = {
    method: "GET",
    headers: {
      Authorization: "Basic " + btoa(`${userName}:${password}`),
    },
  };

  // does a GET request, sets it in PostData variable
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [event, setEvent] = useState({} as any);

  // to search for id based on url so we can GET request specific event
  const [searchParams] = useSearchParams();
  const eventId = searchParams.get("id");

  useEffect(() => {
    console.log(user);
    // Make the Axios GET request when the component mounts
    axios
      .get(`http://localhost:8080/api/v1/events/id/${eventId}`, {
        headers: {
          Authorization: "Basic " + btoa(`${userName}:${password}`),
        },
      })
      .then((response) => {
        console.log(response.data);
        setEvent(response.data); // Store the data in the "data" state variable
        setLoading(false);
      })
      .catch((err) => {
        setError(err);
        setLoading(false);
      });
  }, []);

  //   // called whenever Add Event is clicked, sends POST event with the event data.
  //   // i removed this and put under Event Form
  //
  //   async function handleClick() {
  //     const body = {
  //       name: `.Hack MERN Stack Series (MESS) 2022`,
  //       startDate: "2022-12-29",
  //       endDate: "2023-12-30",
  //       description:
  //         "Interested in developing your own application but don't know how to start? Kickstart your software development journey with .Hack's first-ever winter program: MERN Stack Series (MESS). Fulfill your curiosity by attending our exclusive workshops about the production-ready and widely used technological framework of MongoDB, Express, ReactJS, and NodeJS. Apply the insights gained throughout the workshop to build your own portfolio website ",
  //       capacity: 100,
  //       eventType: "Workshop",
  //       venue: "YPHSL Seminar Room 3-09 & 3-12",
  //       registered: false,
  //       visible: false,
  //     };
  //     await axios
  //       .post("http://localhost:8080/api/v1/events", body, {
  //         headers: {
  //           Authorization: "Basic " + btoa(`${userName}:${password}`),
  //         },
  //       })
  //       .catch((err) => {
  //         console.log(err.message);
  //       });
  //     window.location.reload();
  //   }

  // i removed the Add Event Button and put it into EventForm too
  // called whenever Add Event is clicked, sends POST event with the event data

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
      studentId: userID,
      eventId: id,
      isDeregistered: false,
      isSuccessful: false,
    };

    async function handleClick() {
      console.log(body);
      console.log(userName);
      console.log(password);

      await axios
        .post("http://localhost:8080/api/v1/register", body, {
          headers: {
            Authorization: "Basic " + btoa(`${user.username}:${user.password}`),
          },
        })
        .catch((err) => {
          console.log(err.message);
        });
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
