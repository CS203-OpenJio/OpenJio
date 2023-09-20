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
      <div className="mt-[240px] font-medium text-31xl font-ibm-plex-mono text-center">
        You have successfully logged out!
      </div>
      <button
        className="mt-10 bg-white hover:translate hover:bg-black hover:text-white font-semibold py-2 px-4 border border-gray-400 rounded shadow text-4xl font-ibm-plex-mono cursor-pointer transform active:scale-75 transition-transform"
        onClick={() => handleClick("/")}
      >
        Back to Homepage
      </button>
    </>
  );
};

export default LogoutPage;
