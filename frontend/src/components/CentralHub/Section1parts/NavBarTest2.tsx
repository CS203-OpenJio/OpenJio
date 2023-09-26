import { useNavigate } from "react-router-dom";
import { useState, useEffect, useContext } from "react";
import LogoutButton from "./LogoutButton";
import { AuthContext } from "src/App";

function NavBarTest2() {
  const navigate = useNavigate();

  const { user, setUser } = useContext(AuthContext);

  const handleClick = (path: string) => {
    setUser({
      username: "NOT LOGGED IN",
      password: "",
      userId: 0,
    });
    console.log(user);
    navigate(path);
  };

  const handleClick1 = (path: string) => {
    navigate(path);
  };

  const scrollToElement = (scrollTarget: string) => {
    const targetElement = document.getElementById(scrollTarget);

    if (targetElement) {
      targetElement.scrollIntoView({ behavior: "smooth" });
    }
  };

  //For NavBar transparency effect
  const [isScrolled, setIsScrolled] = useState(false);

  const [hasHovered1, setHasHovered1] = useState(false);
  const [hasHovered2, setHasHovered2] = useState(false);

  const OpenedDropDown1 = () => {
    setHasHovered1(true);
  };

  const OpenedDropDown2 = () => {
    setHasHovered2(true);
  };

  const ClosedDropDown1 = () => {
    setHasHovered1(false);
  };

  const ClosedDropDown2 = () => {
    setHasHovered2(false);
  };

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 20) {
        setIsScrolled(true);
      } else {
        setIsScrolled(false);
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <div
      className={`top-0 flex justify-between items-center fixed w-full ${
        isScrolled ? "bg-white shadow-sm" : ""
      } z-50 pt-2 pb-2`}
      style={{
        transition: "background-color 0.2s ease-in-out",
      }}
    >
      <img
        onClick={() => handleClick1("/centralhub")}
        className="object-cover h-12 ml-5 cursor-pointer"
        alt="OpenJio Logo"
        src="/logo.png"
      />
      <div className="font-medium flex flex-row text-center text-3xl font-ibm-plex-mono w-1/3 justify-between cursor-pointer">
        <div
          className=" flex justify-center"
          onMouseOver={OpenedDropDown1}
          onMouseOut={ClosedDropDown1}
        >
          <div className="hover:bg-black hover:text-white">Events</div>
          {hasHovered1 && (
            <div className="absolute h-32 w-[200px]">
              <div className="absolute translate-y-[35%] border border-solid border-black border-spacing-[0.5px] p-4 bg-white text-black">
                <div>
                  <div
                    onClick={() => scrollToElement("All Events")}
                    className="hover:bg-black hover:text-white mb-2"
                  >
                    View All Events
                  </div>
                  <div
                    onClick={() => handleClick1("/myevents")}
                    className="hover:bg-black hover:text-white mb-2"
                  >
                    View Your Events
                  </div>
                  <div
                    onClick={() => handleClick1("/eventform")}
                    className="hover:bg-black hover:text-white"
                  >
                    Create New Event
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>

        <div
          onClick={() => handleClick1("/schedule")}
          className="hover:bg-black hover:text-white"
        >
          Schedule
        </div>
        <div className="hover:bg-black hover:text-white">
          <div
            className=" flex justify-center"
            onMouseOver={OpenedDropDown2}
            onMouseOut={ClosedDropDown2}
          >
            Profile
          </div>
          {hasHovered2 && (
            <div className="absolute h-32 w-[200px]">
              <div className="absolute translate-y-[27%] translate-x-[-29%] border border-solid border-black border-spacing-[0.5px] p-4 bg-white text-black">
                <div>
                  <div
                    onClick={() => handleClick1("/account")}
                    className="hover:bg-black hover:text-white mb-2"
                  >
                    View My Account
                  </div>
                  <div
                    onClick={() => handleClick1("/settings")}
                    className="hover:bg-black hover:text-white"
                  >
                    Settings
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
      <div
        className="mr-5 cursor-pointer"
        onClick={() => handleClick("/logout")}
      >
        <LogoutButton />
      </div>
    </div>
  );
}

export default NavBarTest2;
