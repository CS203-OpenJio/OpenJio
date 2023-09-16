import EventPost from "./EventPost";

const EventBox = () => {
  return (
    <div className="bg-white rounded-lg border border-1 border-black flex flex-row justify-center items-center">
      <EventPost />
      <EventPost />
      <EventPost />
    </div>
  );
};

export default EventBox;
