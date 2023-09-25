import axios from "axios";
import { useNavigate } from "react-router-dom";

interface EventPostProps {
  title: string;
  date: string;
  time: string;
}

const EventPost2: React.FC<EventPostProps> = ({ title, date, time }) => {
  const navigate = useNavigate();

  const handleClick = (path: string) => {
    navigate(path);
  };

  return (
    <div
      onClick={() => handleClick("/eventpage")}
      className="w-[300px] h-[450px] bg-white border border-solid rounded-lg border-spacing-[0.5px] flex flex-col justify-around items-center m-5 cursor-pointer hover:-translate-y-0.5 transition ease-out delay-150 hover:shadow-md"
    >
      <img
        className="w-[270px] h-[300px] pt-3"
        alt="Welfare Drive Poster"
        src="/ellipsiswelfaredriveposter.png"
      />
      <div className="overflow-hidden text-left text-ellipsis font-ibm-plex-mono pt-3 pb-3 pl-3 pr-3 font-medium text-[22px]">
        {title}
      </div>
      <div className="font-ibm-plex-mono text-xl font-normal text-center pb-3">
        <div className="">
          {date} | {time}
        </div>
        {/* 
        <div className="border border-solid border-spacing-1 font-source-serif-pro m-2 style={{ flex: 'none' }}">
          <div className="text-darkslateblue">16 Sep</div>
        </div> */}
      </div>
    </div>
  );
};

export default EventPost2;
