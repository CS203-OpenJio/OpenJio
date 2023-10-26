import { useNavigate } from "react-router-dom";
//TODO: Implement Login

function LoginButton() {
  const navigate = useNavigate();
  
  const handleClick = () => {
    if(localStorage.getItem("token") != null) {
      navigate("/centralhub");
    } else {
      navigate("/login");
    }
  };
  return (
    <a>
      <button className="bg-white hover:translate hover:bg-black hover:text-white font-semibold py-2 px-4 border border-gray-400 rounded shadow text-3xl font-ibm-plex-mono cursor-pointer transform active:scale-75 transition-transform"
      onClick={handleClick}>
        Login
      </button>
    </a>
  );
}

export default LoginButton;
