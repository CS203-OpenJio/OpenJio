import LoginButton from "./LoginButton";

function NavBar() {
  return (
    <div className="flex justify-between items-center fixed w-full bg-white z-50 pt-2 pb-2">
      <img
        className="object-cover h-20 ml-2"
        alt="OpenJio Logo"
        src="/logo.png"
      />
      <div className="font-medium inline-block text-center text-4xl font-ibm-plex-mono w-1/3 flex flex-row justify-between">
        <div className="">OpenJio?</div>
        <div>About Us</div>
        <div>FAQ</div>
      </div>
      <div>
        <LoginButton />
      </div>
    </div>
  );
}

export default NavBar;
