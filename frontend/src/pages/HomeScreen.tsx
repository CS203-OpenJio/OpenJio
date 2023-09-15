import { FunctionComponent } from "react";
import NavBar from "../components/HomeScreen/NavBar";

const HomeScreen: FunctionComponent = () => {
  return (
    <div>
      <NavBar />
      <div className="h-20">
        {/* place components under this line for testing! */}
      </div>
      <div>
        <div className="mt-24 flex flex-row justify-between">
          <div className="flex flex-col ml-24">
            <span className="text-left text-xl text-black font-roboto-serif">
              Empower Your Campus Experience
            </span>
            <b className="leading-[150%] inline-block w-[571px] h-[244px] text-51xl [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)] text-darkslateblue">
              Join, Create, Customize
            </b>
          </div>
          <div className="flex flex-col justify-center">
          <div className="font-roboto-serif">
          <b className="text-darkslateblue text-31xl top-[107px] left-[736px] leading-[150%] inline-block w-[110px] h-[60px] [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)]">
              3k+
            </b>
            <div className="inline-block text-6xl leading-[150%] text-black">
              events created
            </div>
          </div>
          <div className="font-roboto-serif">
          <b className="text-darkslateblue text-31xl top-[107px] left-[736px] leading-[150%] inline-block w-[110px] h-[60px] [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)]">
              110
            </b>
            <div className="inline-block text-6xl leading-[150%] text-black">
              clubs and societies
            </div>
          </div>
          </div>
          
          
          <div>
          </div>
        </div>
        <div className="flex justify-around overflow-hidden text-left text-11xl text-darkslateblue font-roboto-serif mt-[55px]">
          <b>Workshops</b>
          <b>Orientation Camps</b>
          <b>Welfare Drives</b>
        </div>
        <div className="flex justify-around overflow-hidden text-left text-11xl text-darkslateblue font-roboto-serif mt-[55px]">
          <img className="rounded-11xl w-[392px] h-[244px] object-cover"
            alt=""
            src="/workshop.png"
          />
          <img className="rounded-11xl w-[392px] h-[244px] object-cover"
            alt=""
            src="/orientation.png"
          />
          <img
            className="rounded-11xl w-[392px] h-[244px] object-cover"
            alt=""
            src="/welfaredrive.png"
          />
        </div>
        <div id="scrollAboutUs" className="text-center mt-[50px]">
        <b className="mt-20 inline-block font-source-serif-pro leading-[150%] inline-block text-51xl [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)] text-darkslateblue">
              About Us
            </b>
        </div>
        <div className="rounded-xl bg-white w-[80%] m-auto overflow-hidden text-left text-xl text-black font-ibm-plex-mono border-solid border-[0.5px]">
        <div className="m-20">
            OpenJio is a powerful and user-friendly events website tailored for
            university students, designed to enhance the way you discover, create,
            and participate in events on your campus. We are constantly innovating
            and in search of ways to improve our application and to provide our
            users the most seamless experience.
          </div>
          <div className="m-20">
            We welcome constructive feedback
            and your suggestions to improve our application and user-experience
            are greatly appreciated. Do stay tuned for more updates!
          </div>
          
        </div>
        <div className="bg-white text-center mb-[50px]">
        <b className="inline-block font-source-serif-pro leading-[150%] inline-block text-51xl [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)] text-darkslateblue">
              What can you expect?
            </b>
        </div>
        <div className="h-screen bg-blue-500">
        </div>
      <p className=" m-0">
          
        </p>        
      </div>
    </div>
  );
};

export default HomeScreen;
