import { Link, useSearchParams } from "react-router-dom";
import NavBar from "../components/NavBar";
import { useState, useEffect } from "react";
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
import { getEvent, registerEvent } from "../utils/EventController";
import { toast } from "react-toastify";
import MDEditor from "@uiw/react-md-editor";


export default function EventPage() {
  const navigate = useNavigate();
  // does a GET request, sets it in PostData variable
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
    try {
      getEvent(eventId).then((data) => {
        setEvent(data);
      });
    } catch (err: any) {
      toast.error(err.message);
    }
  }, [eventId]);

  return (
    <div>
      <NavBar />
      <div className="h-[35px]"></div>
      <div className=" m-4">
        <div className="" key={event?.id}>
          <div className="flex flex-row justify-normal font-ibm-plex-mono w-[80%] m-auto mt-24">
            <img
              src={event?.image}
              className="rounded-3xl mx-auto object-contain w-[600px] mr-16"
            ></img>
            <div className="flex flex-col flex-grow bg-white font-normal text-4xl p-3 border border-solid border-black rounded-lg mx-auto mt-8">
              <h2 className="text-28xl font-ibm-plex-mono mx-auto">{event?.name}</h2>
              <div className="ml-8 mt-8">
                Date: {new Date(event?.startDateTime).toLocaleDateString()} to {new Date(event?.endDateTime).toLocaleDateString()}
              </div>
              <div className="ml-8">Venue: {event?.venue}</div>
              <div className="ml-8">Event Capacity: {event?.capacity}</div>
              <div className="font-light -mb-8 mx-8 text-xl mt-12">Description</div>
              <div data-color-mode="light" className="mx-8 mt-12 border border-black border-solid p-3">
                <MDEditor.Markdown
                  source={event.description} />
              </div>
              <div className="flex flex-row justify-end mt-8">
                {userType == "STUDENT" && <RegisterButton id={event?.id} />}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
  function RegisterButton({ id }: { id: string }) {
    const navigate = useNavigate();
    async function handleClick() {
      try {
        await registerEvent(id);
        toast.success("Successfully registered for event!");
        navigate("/purchased");
      } catch (err: any) {
        toast.error(err.message);
      }
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
              <Button
                onClick={handleClick}
                className="hover:cursor-pointer font-ibm-plex-mono"
              >
                Confirm
              </Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>
    );
  }
}
