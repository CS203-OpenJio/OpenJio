import { FunctionComponent } from "react";
import Header1 from "../components/Header1";
import Header2 from "../components/Header2";
import Header3 from "../components/Header3";

const HomeScreen: FunctionComponent = () => {
  return (
    <div className="relative bg-floralwhite shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] w-full h-[2900px] overflow-hidden text-center text-51xl text-darkslateblue font-source-serif-pro">
      <div className="absolute top-[-51px] left-[-108px] w-[1496px] h-[1161px] overflow-hidden">
        <div className="absolute top-[1055px] left-[400px] w-[696px] h-[106px] overflow-hidden">
          <b className="absolute top-[0px] left-[0px] leading-[150%] inline-block w-[690px] h-[106px] [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)]">
            About Us
          </b>
        </div>
        <div className="absolute top-[0px] left-[0px] w-[1496px] h-[986px] overflow-hidden">
          <Header1 />
          <Header2 />
        </div>
      </div>
      <div className="absolute top-[1126px] left-[-203px] w-[1686px] h-[1483px] overflow-hidden text-left text-11xl text-black font-ibm-plex-mono">
        <div className="absolute top-[0px] left-[253px] w-[1180px] h-[559px] overflow-hidden">
          <div className="absolute top-[0px] left-[0px] rounded-3xs bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] box-border w-[1178px] h-[559px] overflow-hidden border-[0.5px] border-solid border-gray">
            <div className="absolute top-[52px] left-[26px] leading-[150%] inline-block w-[1122px] h-[495px]">
              <p className="m-0">
                OpenJio is a powerful and user-friendly events website tailored
                for university students, designed to enhance the way you
                discover, create, and participate in events on your campus.
              </p>
              <p className="m-0">&nbsp;</p>
              <p className="m-0">
                We are constantly innovating and in search of ways to improve
                our application and to provide our users the most seamless
                experience. We welcome constructive feedback and your
                suggestions to improve our application and user-experience are
                greatly appreciated. Do stay tuned for more updates!
              </p>
            </div>
          </div>
        </div>
        <Header3 />
      </div>
    </div>
  );
};

export default HomeScreen;
