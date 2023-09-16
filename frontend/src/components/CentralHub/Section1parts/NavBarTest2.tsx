import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

function NavBarTest2() {
  const navigate = useNavigate();

  const handleClick = (path: string) => {
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
        className="object-cover h-12 ml-5 cursor-pointer"
        alt="OpenJio Logo"
        src="/logo.png"
      />
      <div className="font-medium flex flex-row text-center text-3xl font-ibm-plex-mono w-1/3 justify-between cursor-pointer">
        <div
          onClick={() => handleClick("/events")}
          className="hover:bg-black hover:text-white"
        >
          Events
        </div>
        <div
          onClick={() => handleClick("/schedule")}
          className="hover:bg-black hover:text-white"
        >
          Schedule
        </div>
        <div
          className="hover:bg-black hover:text-white"
          onClick={() => handleClick("/profile")}
        >
          Profile
        </div>
      </div>
      <div className="mr-5" onClick={() => handleClick("/logout")}>
        <img
          className="object-cover mr-11 cursor-pointer"
          alt="Logout button"
          src="/logout.png"
        />
      </div>
    </div>
  );
}

export default NavBarTest2;
