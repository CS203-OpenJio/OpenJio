import EventPost from "./EventPost";

const EventBox = () => {
  return (
    <div className="bg-white rounded-md border border-1 border-black cursor-pointer flex flex-column items-center ">
      <EventPost />
      <EventPost />
      <EventPost />
    </div>
  );
};

export default EventBox;
