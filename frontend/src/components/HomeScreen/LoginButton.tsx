import axios, { Axios } from "axios";
//TODO: Implement Login

function LoginButton() {
  return (
    <a>
      <button className="bg-white hover:translate hover:bg-black hover:text-white font-semibold py-2 px-4 border border-gray-400 rounded shadow text-3xl font-ibm-plex-mono cursor-pointer transform active:scale-75 transition-transform">
        Login
      </button>
    </a>
  );
}

export default LoginButton;
