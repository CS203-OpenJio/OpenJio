import { useNavigate } from "react-router-dom";

const EventPost = () => {
  const navigate = useNavigate();

  const handleClick = (path: string) => {
    navigate(path);
  };

  return (
    <div
      onClick={() => handleClick("/eventpage")}
      className="bg-white border border-solid rounded-lg border-spacing-[0.5px] flex flex-col justify-around items-center m-5 cursor-pointer hover:-translate-y-0.5 transition ease-out delay-150 hover:shadow-md"
    >
      <img
        className="w-[270px] h-[300px] pt-3"
        alt="Welfare Drive Poster"
        src="/ellipsiswelfaredriveposter.png"
      />
      <div className="font-ibm-plex-mono pt-3 pb-3 pl-3 pr-3 font-medium text-[23px] text-center">
        Ellipsis Back2Sku Welfare Drive
      </div>
      <div className="font-ibm-plex-mono text-xl font-normal text-center pb-3">
        <div className="">16 Sep 2023 | 5.00pm - 6.30pm</div>
        {/* 
        <div className="border border-solid border-spacing-1 font-source-serif-pro m-2 style={{ flex: 'none' }}">
          <div className="text-darkslateblue">16 Sep</div>
        </div> */}
      </div>
    </div>
  );
};

export default EventPost;
