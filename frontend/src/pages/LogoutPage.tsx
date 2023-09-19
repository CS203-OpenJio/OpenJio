import { useNavigate } from "react-router-dom";
import LogoutNavBar from "../components/LogoutPage/LogoutNavBar";

const LogoutPage = () => {
  const navigate = useNavigate();

  const handleClick = (path: string) => {
    navigate(path);
  };

  return (
    <>
      <LogoutNavBar />
      <div className="flex flex-column justify-normal items-center">
        <div className="mt-75 font-medium text-11xl font-ibm-plex-mono">
          You have successfully logged out!
        </div>
        <button
          className="bg-white hover:translate hover:bg-black hover:text-white font-semibold py-2 px-4 border border-gray-400 rounded shadow text-6xl font-ibm-plex-mono cursor-pointer transform active:scale-75 transition-transform"
          onClick={() => handleClick("/")}
        >
          Back to Homepage
        </button>
      </div>
    </>
  );
};

export default LogoutPage;
