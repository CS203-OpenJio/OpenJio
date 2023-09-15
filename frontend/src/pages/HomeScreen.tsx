import { FunctionComponent } from "react";
import NavBar from "../components/HomeScreen/NavBar";
import { Link } from "react-router-dom";

const HomeScreen: FunctionComponent = () => {
  return (
    <div>
      <NavBar />

      <div className="min-h-screen">
        <div className="h-20"></div>
        {/* place components under this line for testing! */}
        <Link to="/eventpage">
          <button>EventPage</button>
        </Link>
        {/* place components above this line for testing! */}
        <div className="w-[70%] m-auto mt-[5vw] flex flex-row justify-between flex-wrap">
          <div className="flex flex-col">
            <span className="text-left text-xl text-black font-roboto-serif">
              Empower Your Campus Experience
            </span>
            <b className="italic font-pacifico leading-[150%] inline-block w-[400px] h-[244px] text-51xl [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)] text-darkslateblue">
              Join, Create, Customize
            </b>
          </div>

          <div className="flex flex-col justify-center">
            <div className="font-roboto-serif">
              <b className="text-darkslateblue text-31xl top-[107px] leading-[150%] inline-block w-[100px] h-[60px] [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)]">
                3k+
              </b>
              <div className="inline-block text-6xl leading-[150%] text-black">
                events created
              </div>
            </div>
            <div className="font-roboto-serif">
              <b className="text-darkslateblue text-31xl top-[107px] leading-[150%] inline-block w-[100px] h-[60px] [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)]">
                110
              </b>
              <div className="inline-block text-6xl leading-[150%] text-black">
                clubs and societies
              </div>
            </div>
          </div>
        </div>

        <div className="m-auto w-[90%] flex-wrap flex justify-around overflow-hidden text-center text-11xl text-darkslateblue font-roboto-serif mt-[2vw]">

          <div className="flex flex-col">
            <b className="min-h-[50px]">Workshops</b>
            <img className="rounded-11xl min-w-[392px] w-[29vw] object-cover"
              alt=""
              src="/workshop.png"
            />
          </div>
          <div className="flex flex-col">
            <b className="min-h-[50px]">Orientation Camps</b>
            <img className="rounded-11xl min-w-[392px] w-[29vw] object-cover"
              alt=""
              src="/orientation.png"
            />
          </div>
          <div className="flex flex-col">
            <b className="min-h-[50px]">Welfare Drives</b>
            <img
              className="rounded-11xl min-w-[392px] w-[29vw] object-cover"
              alt=""
              src="/welfaredrive.png"
            />
          </div>
        </div>

      </div>

      <hr id="scrollAboutUs" />

      <div className="w-[60%] m-auto">
        <div>
          <div className="text-center">
            <b className="mt-20 inline-block font-source-serif-pro leading-[150%] inline-block text-51xl [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)] text-darkslateblue">
              About Us
            </b>
          </div>
          <div className="rounded-xl bg-white m-auto overflow-hidden text-left text-xl font-ibm-plex-mono border-solid border-[0.5px]">
            <div className="m-10">
              OpenJio is a powerful and user-friendly events website tailored for
              university students, designed to enhance the way you discover, create,
              and participate in events on your campus. We are constantly innovating
              and in search of ways to improve our application and to provide our
              users the most seamless experience.
            </div>
            <div className="m-10">
              We welcome constructive feedback
              and your suggestions to improve our application and user-experience
              are greatly appreciated. Do stay tuned for more updates!
            </div>

          </div>
        </div>

        <div id="scrollOpenJio">
          <div className="text-center mt-[5vw]">
            <b className="inline-block font-source-serif-pro leading-[150%] inline-block text-51xl [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)] text-darkslateblue">
              What can you expect?
            </b>
          </div>
          <div className="flex flex-col gap-10 mt-[3vw]">
            <div className="items-center flex flex-row justify-around rounded-[50%] bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] w-full h-[10vw]">
              <img
                className="h-[120%]"
                alt=""
                src="/centralhub.png"
              />
              <div className="text-xl font-ibm-plex-mono text-left">
                Central Hub to Discover New Events
              </div>
            </div>
            <div className="items-center flex flex-row justify-around rounded-[50%] bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] w-full h-[10vw]">
              <div className="text-xl font-ibm-plex-mono text-left">
                Seamless Event Registration and Personalised Queue System
              </div>
              <img
                className="h-[120%]"
                alt=""
                src="/registrationandqueue.png"
              />
            </div>
            <div className="items-center flex flex-row justify-around rounded-[50%] bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] w-full h-[10vw]">
              <img
                className="h-[120%]"
                alt=""
                src="./eventcreation.png"
              />
              <div className="text-xl font-ibm-plex-mono text-left">
                Event Creation for University Clubs and Societies
              </div>
            </div>

          </div>

        </div>
      </div>

      <div className="h-20">
      </div>
    </div>
  );
};

export default HomeScreen;
