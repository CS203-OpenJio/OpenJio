import LoginButton from "../HomeScreen/LoginButton";
import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

function LogoutNavBar() {
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
    <div className="flex">
      <div
        className={`flex justify-between items-center fixed w-full ${
          isScrolled ? "bg-white shadow-sm" : ""
        } z-50 pt-2 pb-2`}
        style={{
          transition: "background-color 0.2s ease-in-out",
        }}
      >
        <Link to="/">
          <img
            className="object-cover h-12 ml-5 cursor-pointer"
            alt="OpenJio Logo"
            src="/logo.png"
          />
        </Link>
      </div>
      <div className="mr-5" onClick={() => handleClick("/login")}>
        <LoginButton />
      </div>
    </div>
  );
}

export default LogoutNavBar;
