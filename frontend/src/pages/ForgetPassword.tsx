import {
    FunctionComponent,
    useState,
    useCallback,
    SetStateAction,
  } from "react";
  import { Link, useNavigate } from "react-router-dom";
  
  const ForgetPassword1: FunctionComponent = () => {
    const navigate = useNavigate();
    const [answer, setAnswer] = useState(""); // 1. Create local state to store the answer
  
    const onFrameContainerClick = useCallback(() => {
      navigate("/signup");
    }, [navigate]);
  
    const onPencil71Click = useCallback(() => {
      navigate("/login");
    }, [navigate]);
  
    const handleEnterClick = async () => {
      // Make an API call to the backend to verify the answer
  
      if (answer == "fluffy") {
        // Here, navigate to the desired page
        navigate("/forgetpassword1"); // <-- EDIT THIS with the route of your next page
      }
    };
  
    const handleInputChange = (event: {
      target: { value: SetStateAction<string> };
    }) => {
      setAnswer(event.target.value);
    };
  
    return (
      <div className="relative bg-floralwhite w-full h-[982px] overflow-hidden text-center text-base text-black font-ibm-plex-mono">
        <div
          className="absolute top-[45px] left-[1300px] w-[121px] h-[892px] overflow-hidden cursor-pointer"
          onClick={onFrameContainerClick}
        >
          <Link
            className="cursor-pointer [text-decoration:none] absolute top-[0px] left-[0px] rounded-xl bg-white box-border w-[121px] h-[41px] flex flex-col py-2.5 px-3 items-center justify-center text-[inherit] border-[1px] border-solid border-black"
            to="/signup"
          >
            <div className="relative leading-[18px] font-medium inline-block w-[84px]">{`Sign up `}</div>
          </Link>
        </div>
        <div className="absolute top-[-37px] left-[-101px] w-[519px] h-[1056px] overflow-hidden flex flex-col items-center justify-start">
          <Link
            className="cursor-pointer [text-decoration:none] relative w-[519px] h-[205px] bg-[url('/public/undefined.png')] bg-cover bg-no-repeat bg-[top]"
            to="/login"
            onClick={onPencil71Click}
          />
        </div>
        <div className="absolute top-[45px] left-[445px] w-[621px] h-[892px] overflow-hidden flex flex-col items-center justify-start text-4xl">
          <div className="w-[621px] h-[617px] overflow-hidden shrink-0 flex flex-col items-center justify-center gap-[224px]">
            <div className="w-[479px] h-[51px] overflow-hidden shrink-0 ml-3">
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
            <div className="relative rounded-11xl bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] box-border w-[621px] h-[342px] overflow-hidden shrink-0 text-[20px] font-roboto border-[0.5px] border-solid border-black">
              <div className="absolute top-[85px] left-[22px] w-[579px] flex flex-col py-0 px-3 box-border items-center justify-center">
                <div className="w-[567px] flex flex-row py-3 px-0 box-border items-center justify-center relative gap-[8px]">
                  <div className="relative rounded-2xl bg-blue-100 w-8 h-8 z-[0]">
                    <div className="absolute top-[calc(50%_-_16px)] left-[calc(50%_-_16px)] leading-[32px] flex items-center justify-center overflow-hidden text-ellipsis whitespace-nowrap w-8 h-8">
                      ❓
                    </div>
                  </div>
                  <div className="flex-1 flex flex-col items-start justify-start z-[1] text-left text-sm font-ibm-plex-mono">
                    <div className="self-stretch relative leading-[20px]">
                      What is your pet's name?
                    </div>
                  </div>
                  <img
                    className="absolute my-0 mx-[!important] w-full right-[0px] bottom-[-0.5px] left-[0px] max-w-full overflow-hidden h-px object-cover z-[2]"
                    alt=""
                    src="/undefined3.png"
                  />
                </div>
              </div>
  
              <div className="absolute top-[31px] left-[31px] w-[567px] h-[275px] overflow-hidden text-left text-sm font-ibm-plex-mono">
                <div className="absolute top-[140px] left-[0px] rounded-md box-border w-[567px] h-16 flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-darkslateblue">
                  <input
                    type="text"
                    value={answer} // bind value to answer
                    onChange={handleInputChange} // update answer on input change
                    className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                    placeholder="Enter your answer"
                  />
                </div>
                <div className="absolute top-[234px] left-[219px] w-[129px] h-[41px] overflow-hidden">
                  <button
                    onClick={handleEnterClick}
                    className="cursor-pointer absolute top-[0px] left-[0px] rounded-xl bg-floralwhite box-border w-[121px] h-[41px] flex flex-col py-2.5 px-3 items-center justify-center border-[1px] border-solid border-darkslateblue"
                  >
                    Enter
                  </button>
                </div>
                <div className="absolute top-[0px] left-[141px] w-[285px] h-[41px] overflow-hidden text-lg">
                  <div className="absolute top-[0px] left-[0px] w-[270px] h-[41px] overflow-hidden">
                    <div className="absolute top-[0px] left-[0px] w-[202px] h-6" />
                    <div className="absolute top-[0px] left-[8px] leading-[24px] font-medium inline-block w-[262px] h-[41px]">
                      Answer Security Question
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
  
  export default ForgetPassword1;
  