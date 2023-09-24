import EventPost from "./EventPost";

const EventBox = () => {
  return (
    <div className="bg-white border border-1 round border-black flex flex-column justify-center items-center">
      <EventPost />
      <EventPost />
      <EventPost />
      <EventPost />
    </div>
  );
};

export default EventBox;
