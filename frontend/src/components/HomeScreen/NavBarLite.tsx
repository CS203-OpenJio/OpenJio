import LoginButton from "./LoginButton";
import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

function NavBarLite() {
  const navigate = useNavigate();

  const handleClick = (path: string) => {
    if(localStorage.getItem("token") != null) {
      navigate("/centralhub");
    } else {
      navigate(path);
    }
  };

  // depreciated scroll method
  // const scrollToElement = (scrollTarget: string) => {
  //   const targetElement = document.getElementById(scrollTarget);

  //   if (targetElement) {
  //     targetElement.scrollIntoView({ behavior: "smooth" });
  //   }
  // };

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

  const navigateToSection = (sectionId: string) => {
    navigate(`/#${sectionId}`);
  };


  return (
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

      <div className="font-medium flex flex-row text-center text-3xl font-ibm-plex-mono w-1/3 justify-between cursor-pointer">
        <div
          onClick={() => navigateToSection("scrollOpenJio")}
          className="hover:bg-black hover:text-white"
        >
          OpenJio?
        </div>
        <div
          onClick={() => navigateToSection("scrollAboutUs")}
          className="hover:bg-black hover:text-white"
        >
          About Us
        </div>
        <div
          onClick={() => navigateToSection("scrollFAQ")}
          className="hover:bg-black hover:text-white"
        >
          FAQ
        </div>
      </div>
      <div className="mr-5" onClick={() => handleClick("/login")}>
        <LoginButton />
      </div>
    </div>
  );
}

export default NavBarLite;
