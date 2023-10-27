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
    <div className="bg-white border border-1 round border-black flex flex-column justify-center items-center">
      {events.map((event: any) => (
        event.visible && <EventPost
          key={event?.id}
          eventNumber={event?.id}
          imgPath={event?.image}
          title={event?.name}
          date={event?.startDate}
          time="5.00pm - 6.30pm"
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
