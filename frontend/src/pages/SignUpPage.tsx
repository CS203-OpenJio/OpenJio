import { FunctionComponent, useCallback, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const SignUpPage: FunctionComponent = () => {
  const navigate = useNavigate();

  const onSecondayClick = useCallback(() => {
    navigate("/login");
  }, [navigate]);

  const [fullname, setFullname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [school, setSchool] = useState("");
  const [matriculationid, setMatriculationid] = useState("");

  const handleSubmit = async () => {
    //sign up logic w backend api
  };

  return (
    <div className="relative bg-floralwhite w-full h-[982px] overflow-hidden text-center text-4xl text-black font-ibm-plex-mono">
      <Link
        className="cursor-pointer [text-decoration:none] absolute top-[45px] left-[1300px] rounded-xl bg-white box-border w-[121px] h-[41px] flex flex-col py-2.5 px-3 items-center justify-center text-left text-lg text-[inherit] border-[1px] border-solid border-darkslateblue"
        to="/login"
        onClick={onSecondayClick}
      >
        <div className="relative leading-[22px] font-medium inline-block w-[54px]">
          Login
        </div>
      </Link>
      <div className="absolute top-[45px] left-[892px] leading-[150%] font-medium inline-block w-[109px] h-[50px]">
        FAQ
      </div>
      <div className="absolute top-[46px] left-[718px] leading-[150%] font-medium inline-block w-[131px] h-[50px]">
        About Us
      </div>
      <b className="absolute top-[181px] left-[136px] text-[40px] leading-[150%] inline-block font-source-serif-pro w-[236px] h-[34px]">
        Sign Up
      </b>
      <div className="absolute top-[45px] left-[522px] leading-[150%] font-medium inline-block w-[131px] h-[50px]">
        OpenJio?
      </div>
      <img
        className="absolute top-[0px] left-[0px] w-[519px] h-[205px] object-cover"
        alt=""
        src="/undefined.png"
      />
      <img
        className="absolute top-[198px] left-[711px] rounded-[10px] w-[622px] h-[406px] object-cover"
        alt=""
        src="/undefined1.png"
      />
      <div className="absolute top-[632px] left-[582px] rounded-[50%] bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] box-border w-[871px] h-[257px] border-[0.5px] border-solid border-black" />
      <i className="absolute top-[686px] left-[624px] text-[26px] leading-[150%] inline-block w-[796px] h-[215px]">
        <p className="m-0">
          OpenJio is a powerful and user-friendly events website tailored for
          university students, designed to enhance the way you discover, create,
          and participate in events on your campus.
        </p>
        <p className="m-0">&nbsp;</p>
        <p className="m-0">&nbsp;</p>
      </i>
      <div className="absolute top-[259px] left-[64px] rounded-11xl bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] box-border w-[393px] h-[663px] text-left text-xs border-[1px] border-solid border-gray-200">
        <div className="absolute top-[439px] left-[3px] w-[387px] overflow-hidden flex flex-col items-end justify-center">
          <div className="self-stretch overflow-hidden flex flex-col py-0 px-3 items-center justify-start gap-[4px]">
            <div className="relative leading-[20px] font-medium inline-block w-[363px]">{`Year of matriculation `}</div>
            <div className="self-stretch flex flex-row items-start justify-between text-sm font-roboto">
              <button className="cursor-pointer rounded-md bg-floralwhite flex flex-col p-2 items-center justify-center">
                <div className="relative leading-[20px]">2022/2023</div>
              </button>

              <button className="cursor-pointer rounded-md bg-floralwhite flex flex-col p-2 items-center justify-center">
                <div className="relative leading-[20px]">2021/2022</div>
              </button>

              <button className="cursor-pointer rounded-md bg-floralwhite flex flex-col p-2 items-center justify-center">
                <div className="relative leading-[20px]">2019/2020</div>
              </button>

              <button className="cursor-pointer rounded-md bg-floralwhite flex flex-col p-2 items-center justify-center">
                <div className="relative leading-[20px]">2018/2022</div>
              </button>
            </div>
          </div>
        </div>

        <button onClick={handleSubmit}>
          <div className="absolute top-[528px] left-[13px] w-[367px] overflow-hidden flex flex-col items-center justify-center text-sm">
            <div className="self-stretch overflow-hidden flex flex-row py-0 px-3 items-start justify-start">
              <div className="cursor-pointer flex-1 rounded-xl bg-floralwhite flex flex-col py-2.5 px-3 items-center justify-center border-[0.5px] border-solid border-black">
                <div className="relative leading-[22px] font-medium">
                  Sign Up
                </div>
              </div>
            </div>
          </div>
        </button>
        <div className="absolute top-[19px] left-[13px] w-[367px] overflow-hidden flex flex-col items-start justify-center">
          <div className="self-stretch overflow-hidden flex flex-col py-0 px-3 items-start justify-center gap-[4px]">
            <div className="self-stretch relative leading-[20px] font-medium">
              Full Name
            </div>
            <div className="self-stretch rounded-md flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-black">
              <div className="flex-1 relative leading-[20px] inline-block overflow-hidden text-ellipsis whitespace-nowrap h-5">
                <input
                  className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                  value={fullname}
                  onChange={(e) => setFullname(e.target.value)}
                  placeholder="Enter your full name"
                  type="text"
                />
              </div>
            </div>
          </div>
        </div>
        <div className="absolute top-[91px] left-[13px] w-[367px] overflow-hidden flex flex-col items-start justify-center">
          <div className="self-stretch overflow-hidden flex flex-col py-0 px-3 items-start justify-center gap-[4px]">
            <div className="self-stretch relative leading-[20px] font-medium">
              School Email Address
            </div>
            <div className="self-stretch rounded-md flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-black">
              <div className="flex-1 relative leading-[20px] inline-block overflow-hidden text-ellipsis whitespace-nowrap h-5">
                <input
                  placeholder="Enter your email address"
                  className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                  type="text"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>
            </div>
          </div>
        </div>
        <div className="absolute top-[163px] left-[13px] w-[367px] overflow-hidden flex flex-col items-start justify-center">
          <div className="self-stretch overflow-hidden flex flex-col py-0 px-3 items-start justify-center gap-[4px]">
            <div className="self-stretch relative leading-[20px] font-medium">
              Password
            </div>
            <div className="self-stretch rounded-md flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-black">
              <div className="flex-1 relative leading-[20px] inline-block overflow-hidden text-ellipsis whitespace-nowrap h-5">
                <input
                  placeholder="Enter your password"
                  className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                  type="text"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>
            </div>
          </div>
        </div>
        <div className="absolute top-[235px] left-[13px] w-[367px] overflow-hidden flex flex-col items-start justify-center">
          <div className="self-stretch overflow-hidden flex flex-col py-0 px-3 items-start justify-center gap-[4px]">
            <div className="self-stretch relative leading-[20px] font-medium">
              Re-Enter Password
            </div>
            <div className="self-stretch rounded-md flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-black">
              <div className="flex-1 relative leading-[20px] inline-block overflow-hidden text-ellipsis whitespace-nowrap h-5">
                <input
                  placeholder="Enter your password"
                  className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                  type="text"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>
            </div>
          </div>
        </div>
        <div className="absolute top-[302px] left-[13px] w-[367px] overflow-hidden flex flex-col items-start justify-center">
          <div className="self-stretch overflow-hidden flex flex-col py-0 px-3 items-start justify-center gap-[4px]">
            <div className="self-stretch relative leading-[20px] font-medium">
              School
            </div>
            <div className="self-stretch rounded-md flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-black">
              <div className="flex-1 relative leading-[20px] inline-block overflow-hidden text-ellipsis whitespace-nowrap h-5">
                <input
                  placeholder="Enter school's name"
                  className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                  type="text"
                  value={school}
                  onChange={(e) => setSchool(e.target.value)}
                />
              </div>
            </div>
          </div>
        </div>
        <div className="absolute top-[369px] left-[13px] w-[367px] overflow-hidden flex flex-col items-start justify-center">
          <div className="self-stretch overflow-hidden flex flex-col py-0 px-3 items-start justify-center gap-[4px]">
            <div className="self-stretch relative leading-[20px] font-medium">
              Matriculation Id
            </div>
            <div className="self-stretch rounded-md flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-black">
              <div className="flex-1 relative leading-[20px] inline-block overflow-hidden text-ellipsis whitespace-nowrap h-5">
                <input
                  placeholder="Enter your id"
                  className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                  type="text"
                  value={matriculationid}
                  onChange={(e) => setMatriculationid(e.target.value)}
                />
              </div>
            </div>
          </div>
        </div>
        <div className="absolute top-[596px] left-[28px] w-[337px] overflow-hidden flex flex-col items-start justify-center text-mini">
          <div className="h-14 overflow-hidden shrink-0 flex flex-row items-center justify-end gap-[12px]">
            <div className="flex-1 flex flex-col items-start justify-start">
              <div className="self-stretch relative leading-[24px] font-medium">
                Why join us?
              </div>
              <div className="self-stretch relative text-3xs leading-[16px]">
                Sign up now to enjoy exclusive benefits.
              </div>
            </div>

            <button className="cursor-pointer rounded box-border flex flex-row py-[3px] pr-1 pl-2 items-center justify-start gap-[0px] w-[55px] [align-self:start] mt-[9px] text-3xs border-[1px] border-solid border-black">
              <div className="relative leading-[16px]">Learn More</div>
              <img
                className="relative w-3 h-3 object-cover"
                alt=""
                src="/undefined2.png"
              />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignUpPage;
