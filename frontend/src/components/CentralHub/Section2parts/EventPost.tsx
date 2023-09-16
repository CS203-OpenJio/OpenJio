const EventPost = () => {
  return (
    <div className="bg-white border border-solid border-spacing-[0.5px] flex-col justify-around items-center m-5 rounded cursor-pointer">
      <img
        className="w-[360px] h-[200px] pr-3 pl-3 pt-3"
        alt="Welfare Drive Poster"
        src="/ellipsiswelfaredriveposter.png"
      />
      <div className="font-ibm-plex-mono pt-3 pb-3 pl-3 pr-3 font-medium text-xl">
        Ellipsis Back2Sku Welfare Drive
      </div>
      <div className="flex flex-row justify-around font-ibm-plex-mono text-xl pb-3">
        <div className="">16 Sep</div>
        <div className="">11.00am - 4.00pm</div>
      </div>
    </div>
  );
};

export default EventPost;
