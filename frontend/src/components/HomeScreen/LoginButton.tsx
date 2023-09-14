import axios, { Axios } from "axios";
//TODO: Implement Login

function LoginButton() {
  let info;

  const handleLogin = () => {
    // Implement your login logic here, e.g., show a login modal or navigate to a login page.
    alert("User clicked login button.");
    axios
      .get("http://localhost:8080/api/v1/users")
      .then((response) => {
        info = response;
      })
      .catch((error) => {
        alert(error);
      });
  };

  return (
    <a href="">
      <button
        className="bg-white hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4 border border-gray-400 rounded shadow text-4xl font-ibm-plex-mono"
        onClick={handleLogin}
      >
        Login
        {info}
      </button>
    </a>
  );
}

export default LoginButton;
