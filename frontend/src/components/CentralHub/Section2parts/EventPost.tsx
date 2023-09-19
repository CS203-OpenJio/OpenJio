import { useNavigate } from "react-router-dom";

const EventPost = () => {
  const navigate = useNavigate();

  const handleClick = (path: string) => {
    navigate(path);
  };

  return (
    <div
      onClick={() => handleClick("/eventpage")}
      className="bg-white border border-solid border-spacing-[0.5px] flex flex-col justify-around items-center m-5 rounded cursor-pointer"
    >
      <img
        className="w-[360px] h-[400px] pt-3"
        alt="Welfare Drive Poster"
        src="/ellipsiswelfaredriveposter.png"
      />
      <div className="font-ibm-plex-mono pt-3 pb-3 pl-3 pr-3 font-medium text-xl">
        Ellipsis Back2Sku Welfare Drive
      </div>
      <div className="font-ibm-plex-mono text-xl pb-3">
        <div className="">16 Sep 2023 | 5.00pm - 6.30pm</div>
        <div className=""></div>
      </div>
    </div>
  );
};

export default EventPost;
