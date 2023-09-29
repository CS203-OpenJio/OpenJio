import { FunctionComponent, useCallback, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import NavBar from "src/components/HomeScreen/NavBar";
import axios, { AxiosError } from "axios";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "src/components/ui/alert-dialog";
import { handleSignUp } from "../utils/AuthController";

const SignUpPage: FunctionComponent = () => {
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleSuccess = (input: string) => {
    navigate(input);
  };

  const [fullname, setFullname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [matriculationid, setMatriculationid] = useState("");
  const [selectedYear, setSelectedYear] = useState("");
  const [phonenumber, setPhonenumber] = useState("");

  const [open, setOpen] = useState(false);

  const handleSubmit = async () => {
    //sign up logic w backend api
    // Use the selectedYear state here along with other state variables
    // to send the data to the backend or perform other sign-up logic.
    try {
      console.log(phonenumber, matriculationid);

      setLoading(true);
      await handleSignUp(
        fullname,
        email,
        password,
        matriculationid,
        phonenumber
      );

      handleSuccess("/login");
      // Handle the successful login response here
    } catch (err: any) {
      console.log(err);
      setOpen(true);
      if (err.response.data.message) {
        setErrorMessage(err.response.data.message);
      } else {
        setErrorMessage("An expected error occured!");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex">
      <NavBar />
      <div className="flex flex-row items-center justify-between w-full px-0">
        <div className="flex flex-col bg-floralwhite w-full h-screen p-4 text-2xl text-black font-ibm-plex-mono">
          {/* Sub Container */} {/* Split into Two Columns */}
          {/* left Column Container */}{" "}
          <div className="flex flex-col items-center w-[50%] pl-0">
            <b className="text-[40px] leading-[150%] font-source-serif-pro w-[200px] h-[34px] mt-[50px] mb-[30px] ml-[210px]">
              Sign Up
            </b>
            <div className="flex flex-col items-center bg-white rounded-11xl shadow-[0px_4px_4px_rgba(0,0,0,0.25)] border-[1px] border-solid border-gray-200 p-[24px] max-h-[80vh] w-full space-y-4 ml-[160px] overflow-auto">
              <div className="flex flex-col items-start w-full px-4 space-y-4 mb-4 overflow-auto overflow-hidden">
                {" "}
                {/* Adjusted to flex column, added spacing, and overflow hidden */}
                <div className="flex flex-col items-start w-[100%] space-y-4 mb-4 overflow-hidden">
                  <div className="flex flex-col w-full px-3 space-y-4">
                    <div className="text-left font-medium leading-[20px]">
                      Full Name
                    </div>

                    <div className="w-[90%] rounded-md flex items-center px-3 border-[1px] border-solid border-black">
                      <input
                        placeholder="Enter your full name"
                        className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                        type="text"
                        value={fullname}
                        onChange={(e) => setFullname(e.target.value)}
                      />
                    </div>
                  </div>
                  <div className="flex flex-col w-full px-3 space-y-4">
                    <div className="text-left font-medium leading-[20px]">
                      School Email Address
                    </div>
                    <div className="w-[90%] rounded-md flex items-center px-3 border-[1px] border-solid border-black">
                      <input
                        placeholder="Enter your email address"
                        className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                        type="text"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                      />
                    </div>
                  </div>
                  {/* Password */}
                  <div className="flex flex-col w-full px-3 space-y-4">
                    <div className="text-left font-medium leading-[20px]">
                      Password
                    </div>
                    <div className="w-[90%] rounded-md flex items-center px-3 border-[1px] border-solid border-black">
                      <input
                        placeholder="Enter your password"
                        className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                        type="text"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                      />
                    </div>
                  </div>
                  {/* Adjusted the container to flex column and added spacing between children elements */}
                  {/* Phone Number */}
                  <div className="flex flex-col w-full px-3 space-y-4">
                    <div className="text-left font-medium leading-[20px]">
                      Phone Number
                    </div>
                    <div className="w-[90%] rounded-md flex items-center px-3 border-[1px] border-solid border-black">
                      <input
                        placeholder="Enter your phone number"
                        className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                        type="text"
                        onChange={(e) => setPhonenumber(e.target.value)}
                      />
                    </div>
                  </div>{" "}
                  {/* Adjusted the container to flex column and added spacing between children elements */}
                  {/* Matriculation Id */}
                  <div className="flex flex-col w-full px-3 space-y-4">
                    <div className="text-left font-medium leading-[20px]">
                      Matriculation Id
                    </div>
                    <div className="w-[90%] rounded-md flex items-center px-3 border-[1px] border-solid border-black">
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
                <div className="font-medium w-full text-center mb-2">
                  Year of matriculation
                </div>
                <div className="flex flex-row items-center justify-between w-full px-3">
                  {/* Flex container in row orientation with equally spaced items */}
                  <button
                    onClick={() => setSelectedYear("2022/2023")}
                    className={`cursor-pointer rounded-md flex flex-col p-2 items-center justify-center 
        ${
          selectedYear === "2022/2023"
            ? "bg-blue-500 text-white"
            : "bg-floralwhite text-black"
        } 
        transform active:scale-95`}
                  >
                    <div className="relative leading-[20px]">2022/2023</div>
                  </button>
                  <button
                    onClick={() => setSelectedYear("2021/2022")}
                    className={`cursor-pointer rounded-md flex flex-col p-2 items-center justify-center 
        ${
          selectedYear === "2021/2022"
            ? "bg-blue-500 text-white"
            : "bg-floralwhite text-black"
        } 
        transform active:scale-95`}
                  >
                    <div className="relative leading-[20px]">2021/2022</div>
                  </button>
                  <button
                    onClick={() => setSelectedYear("2020/2021")}
                    className={`cursor-pointer rounded-md flex flex-col p-2 items-center justify-center 
        ${
          selectedYear === "2020/2021"
            ? "bg-blue-500 text-white"
            : "bg-floralwhite text-black"
        } 
        transform active:scale-95`}
                  >
                    <div className="relative leading-[20px]">2020/2021</div>
                  </button>
                  <button
                    onClick={() => setSelectedYear("2019/2020")}
                    className={`cursor-pointer rounded-md flex flex-col p-2 items-center justify-center 
        ${
          selectedYear === "2019/2020"
            ? "bg-blue-500 text-white"
            : "bg-floralwhite text-black"
        } 
        transform active:scale-95`}
                  >
                    <div className="relative leading-[20px]">2019/2020</div>
                  </button>
                </div>
                <div className="flex flex-col items-stretch w-full">
                  <button
                    onClick={handleSubmit}
                    className="flex items-center justify-center w-[360px] overflow-hidden text-sm bg-floralwhite rounded-xl border-[0.5px] border-solid border-black transform active:scale-95 cursor-pointer mx-auto"
                  >
                    <div className="flex items-center justify-center flex-1 py-2.5 px-3">
                      <div className="leading-[22px] font-medium">Sign Up</div>
                    </div>
                  </button>
                </div>
                <AlertDialog open={open}>
                  <AlertDialogContent className="font-ibm-plex-mono">
                    <AlertDialogHeader>
                      <AlertDialogTitle>You have an error</AlertDialogTitle>
                      <AlertDialogDescription>
                        {errorMessage}
                      </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                      <AlertDialogCancel
                        onClick={() => {
                          setOpen(false);
                          setErrorMessage("");
                        }}
                        className="hover:cursor-pointer font-ibm-plex-mono"
                      >
                        Close
                      </AlertDialogCancel>
                    </AlertDialogFooter>
                  </AlertDialogContent>
                </AlertDialog>
                <div className="flex flex-col w-[337px] space-y-4 items-start text-mini">
                  {" "}
                  {/* Adjusted container to flex column and added spacing between children elements */}
                  <div className="flex flex-row w-full items-center justify-between space-x-4 h-14">
                    {" "}
                    {/* Adjusted to flex row and added spacing between children elements */}
                    <div className="flex flex-col items-start ml-[40px]">
                      <div className="leading-[24px] font-medium  ">
                        Why join us?
                      </div>
                      <div className="text-3xs leading-[16px]">
                        Sign up now to enjoy exclusive benefits.
                      </div>
                    </div>
                    <button
                      onClick={() => navigate("/")}
                      className="flex items-center gap-1 cursor-pointer rounded py-[3px] px-2 text-3xs border-[1px] border-solid border-black mt-2"
                    >
                      <div className="leading-[16px]">Learn More</div>
                      <img
                        className="w-3 h-3 object-cover"
                        alt=""
                        src="/undefined2.png"
                      />
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="flex flex-col items-center w-[70%] p-4 pr-[100px]">
          {" "}
          {/* Right Column Container */}
          {/* Image Container */}
          <div className="flex items-center justify-center rounded-[10px] overflow-hidden mt-[50px] mb-[40px]">
            <img
              className="w-full h-auto object-cover max-w-[822px] max-h-[706px] overflow-auto"
              alt=""
              src="/undefined1.png"
            />
          </div>
          {/* Text Section Below Image */}
          <div className="flex flex-col items-center bg-white rounded-[50%] shadow-box-border max-w-[600px] max-h-[400px] w-full border-[0.5px] border-solid border-black p-[32px] space-y-4 overflow-auto">
            <p className="text-[26px] leading-[150%] max-w-[580px] max-h-[500px] w-full pl-[32px] py-8 overflow-auto">
              OpenJio is a powerful and user-friendly events website tailored
              for university students, designed to enhance the way you discover,
              create, and participate in events on your campus.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignUpPage;
