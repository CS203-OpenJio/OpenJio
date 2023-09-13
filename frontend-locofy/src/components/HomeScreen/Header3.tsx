import { FunctionComponent } from "react";

const Header3: FunctionComponent = () => {
  return (
    <div className="absolute top-[606px] left-[0px] w-[1686px] h-[877px] overflow-hidden text-left text-21xl text-black font-ibm-plex-mono">
      <div className="absolute top-[122px] left-[0px] w-[1686px] h-[755px] overflow-hidden">
        <div className="absolute top-[0px] left-[0px] w-[1579px] h-[755px] overflow-hidden">
          <div className="absolute top-[18px] left-[145px] rounded-[50%] bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] w-[1366px] h-[228px]" />
          <div className="absolute top-[257px] left-[0px] rounded-[50%] bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] w-[1310px] h-[239px]" />
          <div className="absolute top-[516px] left-[269px] rounded-[50%] bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] w-[1310px] h-[239px]" />
          <div className="absolute top-[82px] left-[638px] leading-[150%] inline-block w-[718px] h-32">
            <p className="m-0">Central Hub to Discover New Events</p>
          </div>
          <div className="absolute top-[320px] left-[275px] leading-[150%] inline-block w-[883px] h-32">
            Seamless Event Registration and Personalised Queue System
          </div>
          <div className="absolute top-[568px] left-[509px] leading-[150%] inline-block w-[883px] h-32">
            Event Creation for University Clubs and Societies
          </div>
          <img
            className="absolute top-[496px] left-[206px] w-[259px] h-[259px] object-cover"
            alt=""
            src="./eventcreation.png"
          />
          <img
            className="absolute top-[246px] left-[1218px] w-[265px] h-[265px] object-cover"
            alt=""
            src="/registrationandqueue.png"
          />
          <img
            className="absolute top-[0px] left-[297px] w-[283px] h-[283px] object-cover"
            alt=""
            src="/centralhub.png"
          />
        </div>
      </div>
    </div>
  );
};

export default Header3;
