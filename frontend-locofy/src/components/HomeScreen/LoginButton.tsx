import { Link } from "react-router-dom";
//TODO: Implement Login

function LoginButton() {

    const handleLogin = () => {
        // Implement your login logic here, e.g., show a login modal or navigate to a login page.
        alert('User clicked login button.');
      };

    return (
        <a href="">  
            <button className="bg-white hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4 border border-gray-400 rounded shadow text-4xl font-ibm-plex-mono">
                Login
            </button>
        </a>
    );
}

export default LoginButton;