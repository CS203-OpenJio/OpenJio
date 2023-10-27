import { useNavigate } from "react-router-dom";
//TODO: Implement Login

function IsOrganiserButton() {
    const navigate = useNavigate();

    const handleClick = () => {
        navigate("/organisersignup");
    };
    return (
        <a>
            <button
                className="bg-white hover:translate hover:bg-black hover:text-white font-semibold py-4 px-8 border border-gray-400 rounded shadow text-6xl font-ibm-plex-mono cursor-pointer transform active:scale-75 transition-transform m-10"
                onClick={handleClick}
            >
                Are you an organiser?
            </button>
        </a>
    );
}

export default IsOrganiserButton;