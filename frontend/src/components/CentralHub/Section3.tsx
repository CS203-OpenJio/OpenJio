import EventBox from "./Section2parts/EventBox";

const Section3 = () => {
  return (
    <div className="flex flex-col bg-white mt-100 ml-14 mr-14 border border-solid border-spacing-[0.5px] rounded-lg cursor-default mb-5">
      <EventBox />
      <EventBox />
      <button className="bg-white hover:translate hover:bg-black hover:text-white font-semibold py-2 px-4 border border-gray-400 rounded shadow text-3xl font-ibm-plex-mono cursor-pointer transform active:scale-75 transition-transform mb-5 ml-5 mr-5">
        See All
      </button>
    </div>
  );
};

export default Section3;
