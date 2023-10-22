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

  const handleSendResetLink = async () => {
    if (isEmailsMatch) {
        const response = await fetch('http://localhost:8080/api/v1/forgot-password/token', {
            method: 'POST',
            headers: {
                'Authorization': 'Basic ' + btoa('admin@admin.com:admin'), // Assuming the provided Basic auth credentials
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: email,
            }),
        });

        const data = await response.json();

        // Handle the response data here. For instance, notify the user if the token was sent successfully or show an error.
        if (response.ok) {
            // Token sent successfully
            alert('Token sent! Please check your email.');
        } else {
            // Handle any errors here
            alert(data.message || 'Error sending token.');
        }
    } else {
        alert('Emails do not match.');
    }
};

  return (
    <div>
      <NavBarLite />

      <button
    className="cursor-pointer absolute top-[650px] left-[150px] rounded-xl bg-green-500 text-white box-border w-[438px] h-[46px] flex flex-col py-2.5 px-3 items-center justify-center border-[2px] border-solid border-black transition-transform duration-100 ease-in-out z-50"
    onClick={() => navigate('/forgetpassword3')}
    style={{ transform: "scale(1)" }}
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
    Go to Reset Password Page
</button>

      <div className="relative bg-floralwhite w-full h-[982px] overflow-hidden text-left text-lg text-black font-ibm-plex-mono">
        <div className="absolute top-[45px] left-[446px] w-[693px] h-[892px] overflow-hidden text-center text-base">
          <div className="absolute top-[0px] left-[0px] w-[693px] h-[758px] overflow-hidden">
            <div className="absolute top-[480px] left-[225px] w-[465px] h-[25px] overflow-hidden text-left">
              {!isEmailsMatch && (
                <div className="bg-lightyellow rounded-lg p-2 shadow-md text-black font-bold">
                  Emails do not match!
                </div>
              )}
            </div>
            <button
              ref={buttonRef} // Assign the ref to the button
              className="cursor-pointer absolute top-[530px] left-[125px] rounded-xl bg-white box-border w-[438px] h-[46px] flex flex-col py-2.5 px-3 items-center justify-center border-[1px] border-solid border-black transition-transform duration-100 ease-in-out"
              onClick={handleSendResetLink}
              style={{ transform: "scale(1)" }} // Initial scale
              onMouseDown={() =>
                buttonRef.current &&
                (buttonRef.current.style.transform = "scale(0.95)")
              } // Scale down when pressed
              onMouseUp={() =>
                buttonRef.current &&
                (buttonRef.current.style.transform = "scale(1)")
              } // Scale back to original size when released
              onMouseLeave={() =>
                buttonRef.current &&
                (buttonRef.current.style.transform = "scale(1)")
              } // Scale back to original size when mouse leaves
            >
              Send reset Link
            </button>
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

            <div className="absolute top-[243px] left-[0px] w-[693px] h-[235px] overflow-hidden">
              <div className="absolute top-[0px] left-[10px] leading-[24px] font-medium inline-block w-[664px] h-[37px]">
                Reset Your Password
              </div>
              <div className="absolute top-[37px] left-[0px] w-[693px] h-[198px] overflow-hidden text-left text-xs">
                <div className="absolute top-[0px] left-[0px] w-[692px] h-[146px] overflow-hidden flex flex-col py-0 px-3 box-border items-start justify-center gap-[4px]">
                  <div className="self-stretch relative leading-[20px] font-medium">
                    Email Address
                  </div>
                  <input
                    className="self-stretch rounded-md flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-darkslateblue"
                    type="email"
                    placeholder="Enter your email address"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
                <div className="absolute top-[123px] left-[1px] w-[692px] h-[75px] overflow-hidden flex flex-col py-0 px-3 box-border items-start justify-center gap-[4px]">
                  <div className="self-stretch relative leading-[20px] font-medium">
                    Confirm Email Address
                  </div>
                  <input
                    className="self-stretch rounded-md flex flex-row py-2 px-3 items-center justify-start border-[1px] border-solid border-black"
                    type="email"
                    placeholder="Enter your email address"
                    value={confirmEmail}
                    onChange={(e) => setConfirmEmail(e.target.value)}
                  />
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
