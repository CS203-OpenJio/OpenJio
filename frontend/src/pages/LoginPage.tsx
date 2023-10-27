import {
  FunctionComponent,
  useCallback,
  useContext,
  useEffect,
  useState,
} from "react";
import { Link, useNavigate } from "react-router-dom";
import NavBarLite from "../components/HomeScreen/NavBarLite";
import { handleLogin } from "../utils/AuthController";

// IMPORTANT: LOGIN CUURRENTLY DOES NOT SET USERNAME AND PASSWORD FOR WEBSITE,  ONLY CHECKS IF VALID USRNAME/PWD

const LoginPage: FunctionComponent = () => {
  const navigate = useNavigate();

  const handleSuccess = (path: string) => {
    navigate(path);
  };

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const handleSubmit = async () => {
    //login logic
    try {
      await handleLogin(username, password);
      handleSuccess("/centralhub");
      // Handle the successful login response here
    } catch (err: any) {
      setErrorMessage(
        err?.message || "Login failed. Please check your credentials."
      );
    }
  };

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
    navigate("/forgetpassword2");
  }, [navigate]);

  return (
    <div className="flex flex-col h-screen">
      <NavBarLite />

      <div className="flex-1 relative bg-floralwhite w-full overflow-hidden text-left text-base text-black font-ibm-plex-mono">
        <img
          className="absolute top-[82px] left-[2524px] w-[980px] h-[818px] overflow-hidden object-cover"
          alt=""
          src="/undefined4.png"
        />

        <div className="flex flex-col justify-center items-center h-full">
          <div className="flex flex-col items-center">
            <img
              className="mb-4 w-[386px] h-[168px] object-cover"
              alt=""
              src="/pencil7-1.png"
            />

            <div className="rounded-11xl bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] box-border w-[434px] h-[342px] overflow-hidden border-[0.5px] border-solid border-black flex flex-col items-center p-4">
              <div className="mb-4 w-[300px] h-[41px]"> {errorMessage} </div>

              <div className="mb-4 w-[220px] h-[60px] overflow-visible relative">
                <div
                  className="absolute top-[-20px] left-[3px] leading-[20px] font-medium inline-block w-[90px] h-[18px] text-black z-10"
                  style={{ color: "black", opacity: 1 }}
                >
                  Email Id
                </div>
                <input
                  className={`w-full font-medium font-ibm-plex-mono text-xs bg-white rounded-xl box-border py-2.5 px-3 border-[1px] border-solid ${
                    errorMessage ? "border-red-500" : "border-darkslateblue"
                  }`}
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  placeholder="Enter your Email Address"
                  type="text"
                />
              </div>

              <div className="mb-4 w-[220px] h-[60px] overflow-visible relative">
                <div
                  className="absolute top-[-20px] left-[3px] leading-[20px] font-medium inline-block w-[70px] h-[18px] text-black z-10"
                  style={{ color: "black", opacity: 1 }}
                >
                  Password
                </div>
                <input
                  className={`w-full font-medium font-ibm-plex-mono text-xs bg-white rounded-xl box-border py-2.5 px-3 border-[1px] border-solid ${
                    errorMessage ? "border-red-500" : "border-darkslateblue"
                  }`}
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="Enter your Password"
                  type="password"
                />
              </div>

              <div className="button-container flex justify-center gap-2.5 mb-4">
                <button
                  className="group cursor-pointer py-2.5 px-4 bg-floralwhite rounded-xl box-border overflow-visible flex flex-col items-center justify-center border border-solid border-darkslateblue transition-transform duration-100 ease-in-out transform active:scale-95"
                  onClick={handleSubmit}
                  style={{ width: "105px", height: "30px" }}
                >
                  <div className="relative text-xs leading-[10px] font-medium font-ibm-plex-mono text-black text-left">
                    Login
                  </div>
                </button>

                <Link
                  className="group cursor-pointer py-2 px-2.5 bg-floralwhite rounded-xl box-border overflow-visible flex flex-col items-center justify-center border border-solid border-darkslateblue transition-transform duration-100 ease-in-out transform active:scale-95 no-underline"
                  to="/signup"
                  onClick={onFrameContainerClick}
                  style={{ width: "100px", height: "31px" }}
                >
                  <div className="relative text-xs leading-[10px] font-medium font-ibm-plex-mono text-black text-left">
                    Sign up
                  </div>
                </Link>
              </div>

              <Link
                className="cursor-pointer [text-decoration:none] rounded flex flex-row py-[3px] pr-1 pl-2 items-center justify-start gap-[2px] text-3xs text-black font-ibm-plex-mono border-[1px] border-solid border-black"
                to="/forgetpassword2"
                onClick={onButtonClick}
              >
                Forgot Password?
                <img
                  className="relative w-3 h-3 object-cover"
                  alt=""
                  src="/undefined5.png"
                />
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default LoginPage;
