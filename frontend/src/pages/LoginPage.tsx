import { FunctionComponent, useCallback, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import NavBar from "@/components/HomeScreen/NavBar";

const LoginPage: FunctionComponent = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async () => {
    //login logic
  };

  const navigate = useNavigate();
  useEffect(() => {
    const scrollAnimElements = document.querySelectorAll(
      "[data-animate-on-scroll]"
    );


    const observer = new IntersectionObserver(
      (entries) => {
        for (const entry of entries) {
          if (entry.isIntersecting || entry.intersectionRatio > 0) {
            const targetElement = entry.target;
            targetElement.classList.add("animate");
            observer.unobserve(targetElement);
          }
        }
      },
      {
        threshold: 0.15,
      }
    );

    for (let i = 0; i < scrollAnimElements.length; i++) {
      observer.observe(scrollAnimElements[i]);
    }

    return () => {
      for (let i = 0; i < scrollAnimElements.length; i++) {
        observer.unobserve(scrollAnimElements[i]);
      }
    };
  }, []);

  const onFrameContainerClick = useCallback(() => {
    navigate("/signup");
  }, [navigate]);

  const onButtonClick = useCallback(() => {
    navigate("/forgetpassword1");
  }, [navigate]);

  return (
    <div className="relative bg-floralwhite w-full h-[982px] overflow-hidden text-left text-base text-black font-ibm-plex-mono">
      <div
        className="absolute top-[45px] right-[50px] w-[121px] h-[892px] overflow-hidden [&.animate]:animate-[1s_ease_0s_1_normal_forwards_fade-in] opacity-[0] cursor-pointer"
        onClick={onFrameContainerClick}
        data-animate-on-scroll
      >
        <Link
          className="cursor-pointer [text-decoration:none] absolute top-[0px] left-[0px] rounded-xl bg-white box-border w-[121px] h-[41px] flex flex-col py-2.5 px-3 items-center justify-center text-[inherit] border-[1px] border-solid border-black"
          to="/login"
        >
          <div className="relative leading-[16px] font-medium">{`Sign up `}</div>
        </Link>
      </div>
      <img
        className="absolute top-[82px] left-[2524px] w-[980px] h-[818px] overflow-hidden object-cover"
        alt=""
        src="/undefined4.png"
      />
      <div className="absolute top-[58px] left-[527px] w-[457px] h-[866px] overflow-hidden text-sm text-darkslateblue font-roboto">
        <div className="absolute top-[0px] left-[0px] w-[457px] h-[775px] overflow-hidden">
          <img
            className="absolute top-[200px] left-[36px] w-[386px] h-[168px] object-cover"
            alt=""
            src="/pencil7-1.png"
          />
          <div className="absolute top-[0px] left-[0px] w-[457px] h-[775px] overflow-hidden">
            <div className="absolute top-[433px] left-[12px] rounded-11xl bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] box-border w-[434px] h-[342px] overflow-hidden border-[0.5px] border-solid border-black">
              <div className="absolute top-[81px] left-[107px] w-[220px] h-[41px] overflow-hidden">
                <input
                  className="font-medium font-ibm-plex-mono text-xs bg-white absolute top-[0px] left-[0px] rounded-xl box-border w-[220px] h-[41px] overflow-hidden flex flex-col py-2.5 px-3 items-center justify-center border-[1px] border-solid border-darkslateblue"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="Enter your Email Address"
                  type="text"
                />
              </div>
              <div className="absolute top-[150px] left-[107px] w-[220px] h-[41px] overflow-hidden">
                <input
                  className="font-medium font-ibm-plex-mono text-xs bg-white absolute top-[0px] left-[0px] rounded-xl box-border w-[220px] h-[41px] overflow-hidden flex flex-row py-2.5 px-3 items-center justify-center border-[1px] border-solid border-darkslateblue"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="Enter your Password"
                  type="password"
                />
              </div>
              <div className="absolute top-[219px] left-[107px] w-[238px] h-[41px] overflow-hidden">
                <button
                  className="cursor-pointer py-2.5 px-3 bg-floralwhite absolute top-[0px] left-[26px] rounded-xl box-border w-[166px] h-[41px] overflow-hidden flex flex-col items-center justify-center border-[1px] border-solid border-darkslateblue"
                  onClick={handleSubmit}
                >
                  <div className="relative text-xs leading-[22px] font-medium font-ibm-plex-mono text-black text-left">
                    Login
                  </div>
                </button>
              </div>
              <div className="absolute top-[60px] left-[117px] w-[218px] h-[18px] overflow-hidden">
                <div className="absolute top-[0px] left-[0px] leading-[20px] font-medium inline-block w-[70px] h-[18px]">
                  Email Id
                </div>
              </div>
              <div className="absolute top-[130px] left-[117px] w-[218px] h-[18px] overflow-hidden">
                <div className="absolute top-[0px] left-[0px] leading-[20px] font-medium inline-block w-[70px] h-[18px]">
                  Password
                </div>
              </div>
              <div className="absolute top-[279px] left-[156px] w-[140px] h-[22px] overflow-hidden" />
              <Link
                className="cursor-pointer [text-decoration:none] absolute top-[calc(50%_+_108px)] right-[156px] rounded flex flex-row py-[3px] pr-1 pl-2 items-center justify-start gap-[2px] text-3xs text-black font-ibm-plex-mono border-[1px] border-solid border-black"
                to="/forgetpassword1"
                onClick={onButtonClick}
              >
                <div className="relative leading-[16px]">Forgot Password?</div>
                <img
                  className="relative w-3 h-3 object-cover"
                  alt=""
                  src="/undefined5.png"
                />
              </Link>
            </div>
            <div className="absolute top-[0px] left-[0px] w-[457px] h-[52px] overflow-hidden text-center text-4xl text-black font-ibm-plex-mono">
              <div className="absolute top-[0px] left-[0px] w-[479px] h-[52px] overflow-hidden">
                <div className="absolute top-[0px] left-[0px] w-[479px] h-[51px] overflow-hidden">
                  <div className="absolute top-[0px] left-[0px] leading-[150%] font-medium inline-block w-[131px] h-[50px]">
                    OpenJio?
                  </div>
                  <div className="absolute top-[0px] left-[370px] leading-[150%] font-medium inline-block w-[109px] h-[50px]">
                    FAQ
                  </div>
                  <div className="absolute top-[1px] left-[196px] leading-[150%] font-medium inline-block w-[131px] h-[50px]">
                    About Us
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
