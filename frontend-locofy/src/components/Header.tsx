import LoginButton from "./LoginButton";

function Header() {
    return (
        <div className="flex justify-between m-4 items-center">
            <img
                className="object-cover h-20"
                alt="OpenJio Logo"
                src="/logo.png"
            />
            <div className="font-medium inline-block text-center text-4xl font-ibm-plex-mono w-1/2 flex flex-row justify-between">
                <div className="">
                    OpenJio?
                </div>
                <div>
                    About Us
                </div>
                <div>
                    FAQ
                </div>
            </div>
            <div>
                <LoginButton />
            </div>
        </div>
    );

}

export default Header;