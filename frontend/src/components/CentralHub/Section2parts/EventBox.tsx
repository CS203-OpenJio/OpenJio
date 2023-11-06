import EventPost from "./EventPost";
import { useEffect, useState } from "react";
import { getAllEvents } from "src/utils/EventController";

const EventBox: React.FC = () => {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    const getEvents = async () => {
      setEvents(await getAllEvents());
    };
    getEvents();
  }, []);


  return (
    <div className="bg-white border border-1 round border-black flex justify-start items-left overflow-scroll">
      {events.map((event: any) => (
        event.visible && <EventPost
          key={event?.id}
          eventNumber={event?.id}
          imgPath={event?.image}
          title={event?.name}
          date={new Date(event?.startDateTime).toDateString()}
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
