import NavBarLite from "../components/HomeScreen/NavBarLite";
import IsStudentButton from "../components/SignUp/IsStudentButton";
import IsOrganiserButton from "../components/SignUp/IsOrganiserButton";

const WhichSignUp = () => {
    return (
        <>
            <NavBarLite />
            <div className="flex flex-col w-screen h-screen items-center justify-center">
                <div className="font-ibm-plex-mono font-bold text-[50px] m-10">Let's make an account!</div>
                <IsStudentButton />
                <IsOrganiserButton />
            </div>
        </>
    );
};

export default WhichSignUp;
