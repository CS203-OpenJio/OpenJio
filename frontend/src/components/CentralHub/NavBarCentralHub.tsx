import { useNavigate } from "react-router-dom";

function NavBarCentralHub() {
  const navigate = useNavigate();

  const handleClick = (path: string) => {
    navigate(path);
  };

  return (
    <div className="flex justify-between items-center fixed w-full bg-white z-50 pt-2 pb-2">
      <img
        onClick={() => handleClick("/")}
        className="object-cover h-20 ml-2 cursor-pointer"
        alt="OpenJio Logo"
        src="/logo.png"
      />
      <div className="flex items-center font-medium text-center text-4xl font-ibm-plex-mono w-full flex-row justify-center gap-32">
        <div onClick={() => handleClick("/events")} className="cursor-pointer">
          Events
        </div>
        {/*drop down to Create an Event, View Created Events*/}
        <div
          onClick={() => handleClick("/schedule")}
          className="cursor-pointer"
        >
          Schedule
        </div>
        <div onClick={() => handleClick("/profile")} className="cursor-pointer">
          Profile
        </div>
      </div>
      <img
        className="object-cover mr-3 cursor-pointer"
        alt="Logout button"
        src="/logout.png"
      />
    </div>
  );
}

export default NavBarCentralHub;
