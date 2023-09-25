import axios from "axios";
import { useNavigate } from "react-router-dom";

interface EventPostProps {
  imgPath: string;
  title: string;
  date: string;
  time: string;
  eventNumber: number;
}

const EventPost: React.FC<EventPostProps> = ({
  imgPath,
  title,
  date,
  time,
  eventNumber,
}) => {
  const navigate = useNavigate();

  const handleClick = (path: string) => {
    navigate(path);
  };

  return (
    <div
      onClick={() => handleClick("/eventpage?id=" + eventNumber)}
      className="w-[300px] h-[500px] bg-white border border-solid rounded-lg border-spacing-[0.5px] flex flex-col justify-around items-center m-5 cursor-pointer hover:-translate-y-0.5 transition ease-out delay-150 hover:shadow-md"
    >
      <img
        className="rounded-3xl w-[270px] h-[350px] pt-3"
        alt="Poster"
        src={imgPath}
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

export default EventPost;
