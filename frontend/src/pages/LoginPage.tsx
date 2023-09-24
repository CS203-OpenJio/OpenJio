import { FunctionComponent, useCallback, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import NavBar from "src/components/HomeScreen/NavBar";
import axios from "axios";

// IMPORTANT: LOGIN CUURRENTLY DOES NOT SET USERNAME AND PASSWORD FOR WEBSITE,  ONLY CHECKS IF VALID USRNAME/PWD

const LoginPage: FunctionComponent = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const navigate = useNavigate();

  const handleSuccess = (path: string) => {
    navigate(path);
  };

  const handleSubmit = async () => {
    //login logic
    try {
      setLoading(true);
      setErrorMessage(null);

      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/signin",
        
        {
          email:username,
          password:password,
        }, {
          headers: {
            Authorization: "Basic " + btoa(`${username}:${password}`),
          },
        }
        
      );

      if (response.status == 200) {
        handleSuccess("/centralhub");
      }
      // Handle the successful login response here
    } catch (err) {
      setErrorMessage("Login failed. Please check your credentials.");
      
    } finally {
      setLoading(false);
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
    navigate("/forgetpassword1");
  }, [navigate]);

  return (
    <div>
      <NavBar />

      <div className="relative bg-floralwhite w-full h-[982px] overflow-hidden text-left text-base text-black font-ibm-plex-mono">
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
              {/* ERROR MESSAGE IS CREATED FROM LINE BELOW */}
              <div className="absolute top-[30px] left-[87px] w-[300px] h-[41px]"> {errorMessage}</div>
              
                <div className="absolute top-[81px] left-[107px] w-[220px] h-[41px] overflow-hidden">
                
                  <input
                    className={`font-medium font-ibm-plex-mono text-xs bg-white absolute top-[0px] left-[0px] rounded-xl box-border w-[220px] h-[41px] overflow-hidden flex flex-col py-2.5 px-3 items-center justify-center border-[1px] border-solid ${errorMessage ? "border-red-500":"border-darkslateblue"}`}
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Enter your Email Address"
                    type="text"
                  />
                </div>
                <div className="absolute top-[150px] left-[107px] w-[220px] h-[41px] overflow-hidden">
                  <input
                    className={`font-medium font-ibm-plex-mono text-xs bg-white absolute top-[0px] left-[0px] rounded-xl box-border w-[220px] h-[41px] overflow-hidden flex flex-row py-2.5 px-3 items-center justify-center border-[1px] border-solid ${errorMessage ? "border-red-500":"border-darkslateblue"} `}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Enter your Password"
                    type="password"
                  />
                </div>

                <div className="absolute top-[219px] left-[98px] w-[238px] h-[412px] overflow-hidden">
                  <div
                    className="button-container"
                    style={{
                      display: "flex",
                      justifyContent: "center",
                      padding: "0",
                      position: "relative",
                      top: "0px",
                      gap: "10px",
                    }}
                  >
                    <div className="button-container flex justify-center p-0 relative top-0 gap-2.5">
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
                  </div>
                </div>
                <div className="absolute top-[60px] left-[117px] w-[218px] h-[18px] overflow-hidden">
                  <div className="absolute top-[0px] left-[0px] leading-[20px] font-medium inline-block w-[70px] h-[18px] text-black">
                    Email Id
                  </div>
                </div>
                <div className="absolute top-[130px] left-[117px] w-[218px] h-[18px] overflow-hidden">
                  <div className="absolute top-[0px] left-[0px] leading-[20px] font-medium inline-block w-[70px] h-[18px] text-black">
                    Password
                  </div>
                </div>
                <div className="absolute top-[279px] left-[156px] w-[140px] h-[22px] overflow-hidden" />
                <Link
                  className="cursor-pointer [text-decoration:none] absolute top-[calc(50%_+_108px)] right-[156px] rounded flex flex-row py-[3px] pr-1 pl-2 items-center justify-start gap-[2px] text-3xs text-black font-ibm-plex-mono border-[1px] border-solid border-black"
                  to="/forgetpassword1"
                  onClick={onButtonClick}
                >
                  <div className="relative leading-[16px]">
                    Forgot Password?
                  </div>
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
    </div>
  );
  
};

export default LoginPage;
