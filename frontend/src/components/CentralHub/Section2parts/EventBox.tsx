import EventPost from "./EventPost";

const EventBox = () => {
  return (
    <div className="bg-white rounded-lg border border-1 border-black cursor-pointer flex flex-column justify-center items-center">
      <EventPost />
      <EventPost />
      <EventPost />
    </div>
  );
};

export default EventBox;
