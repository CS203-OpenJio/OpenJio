import LoginButton from "./LoginButton";
import { useNavigate } from "react-router-dom";

function NavBar() {
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

  return (
    <div className="flex justify-between items-center fixed w-full bg-white z-50 pt-2 pb-2">
      <img
        className="object-cover h-20 ml-2 cursor-pointer"
        alt="OpenJio Logo"
        src="/logo.png"
      />
      <div className="font-medium flex flex-row text-center text-4xl font-ibm-plex-mono w-1/3 justify-between cursor-pointer">
        <div onClick={() => scrollToElement("scrollOpenJio")}>OpenJio?</div>
        <div>About Us</div>
        <div>FAQ</div>
      </div>
      <div onClick={() => handleClick("/login")}>
        <LoginButton />
      </div>
    </div>
  );
}

export default NavBar;
