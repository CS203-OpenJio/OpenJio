import { FunctionComponent, useCallback, useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import NavBarLite from "../components/HomeScreen/NavBarLite";
import { useRef } from "react";
import JWT from "../utils/JWT";

const ForgetPassword: FunctionComponent = () => {
  const navigate = useNavigate();
  const buttonRef = useRef<HTMLButtonElement>(null);

  const [email, setEmail] = useState("");
  const [confirmEmail, setConfirmEmail] = useState("");
  const [isEmailsMatch, setIsEmailsMatch] = useState(true);

  useEffect(() => {
    setIsEmailsMatch(email === confirmEmail);
  }, [email, confirmEmail]);

  const onFrameContainerClick = useCallback(() => {
    navigate("/");
  }, [navigate]);

  const onButtonClick = useCallback(() => {
    navigate("/forgetpassword3");
  }, [navigate]);

  const handleSendResetLink = async () => {
    if (isEmailsMatch) {
      try {
        const response = await JWT.post("/api/v1/forgot-password/token", {
          username: email, // Using 'username' field instead of 'email'
        });
  
        // Handle the response data here. For instance, notify the user if the token was sent successfully.
        if (response.status === 200) {
          alert("Token sent! Please check your email.");
        } else {
          throw new Error(response.data.message || "An error occurred while sending the token.");
        }
      } catch (err: any) {
        // Handle any errors here
        alert(err.response?.data?.message || "Error sending token.");
      }
    } else {
      alert("Emails do not match.");
    }
  };
  
  
  
  
  return (
    <div className="flex flex-col h-screen">
      <NavBarLite />

      <div className="flex-1 relative bg-floralwhite w-full overflow-hidden text-left text-base text-black font-ibm-plex-mono">
        <div className="flex flex-col justify-center items-center h-full space-y-6">
          <div className="flex flex-col items-center space-y-6">
        
          <div className="rounded-11xl bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] box-border w-[434px] h-[342px] overflow-hidden border-[0.5px] border-solid border-black flex flex-col justify-center items-center p-4 space-y-2">
    <h2 className="text-xl font-semibold mb-6">Forget Password</h2>


<div className="w-[300px] h-[60px] overflow-visible relative">
        <div className="absolute top-[-20px] left-[3px] leading-[20px] font-medium inline-block w-[200px] h-[18px] text-black z-10">
            Email Address
        </div>
        <input
            className={`w-full font-medium font-ibm-plex-mono text-xs bg-white rounded-xl box-border py-2.5 px-3 border-[1px] border-solid ${
                !isEmailsMatch ? "border-red-500" : "border-darkslateblue"
            }`}
            type="email"
            placeholder="Enter your email address"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
        />
    </div>

    <div className="w-[300px] h-[60px] overflow-visible relative">
        <div className="absolute top-[-20px] left-[3px] leading-[20px] font-medium inline-block w-[250px] h-[18px] text-black z-10">
            Confirm Email Address
        </div>
        <input
            className={`w-full font-medium font-ibm-plex-mono text-xs bg-white rounded-xl box-border py-2.5 px-3 border-[1px] border-solid ${
                !isEmailsMatch ? "border-red-500" : "border-darkslateblue"
            }`}
            type="email"
            placeholder="Confirm your email address"
            value={confirmEmail}
            onChange={(e) => setConfirmEmail(e.target.value)}
        />
    </div>

    <Link
            className="cursor-pointer [text-decoration:none] rounded flex flex-row py-[3px] pr-1 pl-2 items-center justify-center gap-[2px] text-3xs text-black font-ibm-plex-mono border-[1px] border-solid border-black"
            to="/forgetpassword3"
            onClick={onButtonClick}
          >
            Reset Password
            <img
              className="relative w-3 h-3 object-cover"
              alt=""
              src="/undefined5.png"
            />
          </Link>


   
              <button
                ref={buttonRef}
                className="cursor-pointer rounded-xl bg-floralwhite box-border w-[300px] h-[46px] flex flex-col py-2.5 px-3 items-center justify-center border-[1px] border-solid border-black transition-transform duration-100 ease-in-out"
                onClick={handleSendResetLink}
                onMouseDown={() =>
                  buttonRef.current &&
                  (buttonRef.current.style.transform = "scale(0.95)")
                }
                onMouseUp={() =>
                  buttonRef.current &&
                  (buttonRef.current.style.transform = "scale(1)")
                }
                onMouseLeave={() =>
                  buttonRef.current &&
                  (buttonRef.current.style.transform = "scale(1)")
                }
              >
                Send Reset Link
              </button>
              {!isEmailsMatch && (
        <div className="bg-lightyellow  p-2 text-black font-bold w-[300px] mb-2 text-center">
            Emails do not match!
        </div>
    )}


            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ForgetPassword;