import { FunctionComponent, useCallback, useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import NavBarLite from "../components/HomeScreen/NavBarLite";
import { useRef } from "react";

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
      const response = await fetch(
        "http://localhost:8080/api/v1/forgot-password/token",
        {
          method: "POST",
          headers: {
            Authorization: "Basic " + btoa("admin@admin.com:admin"), // Assuming the provided Basic auth credentials
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            email: email,
          }),
        }
      );

      const data = await response.json();

      // Handle the response data here. For instance, notify the user if the token was sent successfully or show an error.
      if (response.ok) {
        // Token sent successfully
        alert("Token sent! Please check your email.");
      } else {
        // Handle any errors here
        alert(data.message || "Error sending token.");
      }
    } else {
      alert("Emails do not match.");
    }
  };

  return (
    <div>
      <NavBarLite />

      <div className="flex justify-center items-center h-screen bg-floralwhite text-left text-lg text-black font-ibm-plex-mono">
        <div className="flex flex-col items-center text-center text-base w-3/4 space-y-4">
          {!isEmailsMatch && (
            <div className="bg-lightyellow rounded-lg p-2 shadow-md text-black font-bold">
              Emails do not match!
            </div>
          )}

          <div className="leading-[24px] font-medium">Enter Email Address</div>
          <input
            className="rounded-md flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-darkslateblue w-full"
            type="email"
            placeholder="Enter your email address"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <div className="leading-[20px] font-medium">
            Confirm Email Address
          </div>
          <input
            className="rounded-md flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-black w-full"
            type="email"
            placeholder="Enter your email address"
            value={confirmEmail}
            onChange={(e) => setConfirmEmail(e.target.value)}
          />

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
            className="cursor-pointer rounded-xl bg-white box-border w-[438px] h-[46px] flex flex-col py-2.5 px-3 items-center justify-center border-[1px] border-solid border-black transition-transform duration-100 ease-in-out"
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
            Send reset Link
          </button>

          <div className="rounded-md bg-black w-3/4 text-mini text-white p-4">
            A password reset link will be sent to your email address. Please
            check your inbox and follow the instructions provided to reset your
            password.
          </div>
        </div>
      </div>
    </div>
  );
};

export default ForgetPassword;
