import { FunctionComponent, useCallback } from "react";
import { Link, useNavigate } from "react-router-dom";

const ForgetPassword: FunctionComponent = () => {
  const navigate = useNavigate();

  const onFrameContainerClick = useCallback(() => {
    navigate("/");
  }, [navigate]);

  return (
    <div className="relative bg-floralwhite w-full h-[982px] overflow-hidden text-left text-lg text-black font-ibm-plex-mono">
      <div
        className="absolute top-[45px] left-[1365px] w-[121px] h-[892px] overflow-hidden cursor-pointer"
        onClick={onFrameContainerClick}
      >
        <Link
          className="cursor-pointer [text-decoration:none] absolute top-[0px] left-[0px] rounded-xl bg-white box-border w-[121px] h-[41px] flex flex-col py-2.5 px-3 items-center justify-center text-[inherit] border-[1px] border-solid border-darkslateblue"
          to="/"
        >
          <div className="relative leading-[22px] font-medium">{`Sign up `}</div>
        </Link>
      </div>
      <div className="absolute top-[-37px] left-[-101px] w-[519px] h-[1056px] overflow-hidden">
        <img
          className="absolute top-[37px] left-[101px] w-[519px] h-[205px] object-cover"
          alt=""
          src="/undefined.png"
        />
      </div>
      <div className="absolute top-[45px] left-[446px] w-[693px] h-[892px] overflow-hidden text-center text-base">
        <div className="absolute top-[0px] left-[0px] w-[693px] h-[758px] overflow-hidden">
          <div className="absolute top-[533px] left-[114px] w-[465px] h-[46px] overflow-hidden text-left">
            <div className="absolute top-[0px] left-[0px] rounded-xl bg-white box-border w-[438px] h-[46px] flex flex-col py-2.5 px-3 items-center justify-center border-[1px] border-solid border-black">
              <div className="relative leading-[22px] font-medium">
                Send reset Link
              </div>
            </div>
          </div>
          <div className="absolute top-[635px] left-[5px] rounded-md bg-black w-[683px] h-[123px] text-mini text-white">
            <div className="absolute w-[calc(100%_-_32px)] top-[calc(50%_-_7.5px)] left-[16px] leading-[22px] font-medium flex items-center justify-center h-4">
              A password reset link will be sent to your email address. Please
              check your inbox and follow the instructions provided to reset
              your password.
            </div>
            <div className="absolute bottom-[8px] left-[calc(50%_-_21.5px)] flex flex-row items-center justify-center gap-[4px]">
              <div className="relative rounded-81xl bg-white w-5 h-1" />
              <div className="relative rounded-81xl bg-white w-1 h-1" />
              <div className="relative rounded-81xl bg-white w-1 h-1" />
              <div className="relative rounded-81xl bg-white w-1 h-1" />
            </div>
          </div>
          <div className="absolute top-[0px] left-[76px] w-[541px] h-[51px] overflow-hidden text-4xl">
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
          <div className="absolute top-[243px] left-[0px] w-[693px] h-[235px] overflow-hidden">
            <div className="absolute top-[0px] left-[14px] leading-[24px] font-medium inline-block w-[664px] h-[37px]">
              Reset Your Password
            </div>
            <div className="absolute top-[37px] left-[0px] w-[693px] h-[198px] overflow-hidden text-left text-xs">
              <div className="absolute top-[0px] left-[0px] w-[692px] h-[146px] overflow-hidden flex flex-col py-0 px-3 box-border items-start justify-center gap-[4px]">
                <div className="self-stretch relative leading-[20px] font-medium">
                  Email Address
                </div>
                <div className="self-stretch rounded-md flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-darkslateblue">
                  <div className="flex-1 relative leading-[20px] inline-block overflow-hidden text-ellipsis whitespace-nowrap h-5">
                    Enter your email address
                  </div>
                </div>
              </div>
              <div className="absolute top-[123px] left-[1px] w-[692px] h-[75px] overflow-hidden flex flex-col py-0 px-3 box-border items-start justify-center gap-[4px]">
                <div className="self-stretch relative leading-[20px] font-medium">
                  Confirm Email Address
                </div>
                <div className="self-stretch rounded-md flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-black">
                  <div className="flex-1 relative leading-[20px] inline-block overflow-hidden text-ellipsis whitespace-nowrap h-5">
                    Enter your email address
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

export default ForgetPassword;
