import NavBarLite from "../components/HomeScreen/NavBarLite";
import IsStudentButton from "../components/SignUp/IsStudentButton";
import IsOrganiserButton from "../components/SignUp/IsOrganiserButton";

const WhichSignUp = () => {
    return (
        <>
            <NavBarLite />
            <div className="flex flex-col w-screen h-screen items-center justify-center">
                <div className="font-ibm-plex-mono font-bold text-[50px] text-center">
                    Let's make an account!
                </div>
                <p className="font-ibm-plex-mono text-[18px] m-10 w-[60%] text-center leading-8">
                    A student account provides access to view and register for
                    events, as well as check your upcoming activities. In
                    contrast, an organizer account offers capabilities to design
                    events, oversee registrations, and utilize a customizable
                    algorithm for participants.
                </p>
                <IsStudentButton />
                <IsOrganiserButton />
            </div>
        </>
    );
};

export default WhichSignUp;
