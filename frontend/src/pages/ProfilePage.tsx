import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { FunctionComponent, useState, useEffect } from "react";
import NavBar from "../components/NavBar";
import { getStudentByEmail, handleChangeDetails } from "../utils/ProfileController";
// import { getOrganiserByEmail, handleOrganiserChangeDetails } from "../utils/OrganiserController"; // Placeholder imports

const ChangeProfile: FunctionComponent = () => {
    const [dob, setDob] = useState("");
    const [matricNo, setMatricNo] = useState("");
    const [phone, setPhone] = useState("");
    const [id, setId] = useState("");
    const [isEditing, setIsEditing] = useState(false);
    const [userType, setUserType] = useState(""); // New state for user type

    useEffect(() => {
        // Determine user type
        const userType = localStorage.getItem("userType") || "";
        setUserType(userType);

        // Fetch details based on user type
        const fetchDetails = async () => {
            try {
                let response;
                if (userType === "STUDENT") {
                    response = await getStudentByEmail();
                    setMatricNo(response.matricNo); // Only for students
                } else if (userType === "ORGANISER") {
                    console.log("Update organizer details function goes here.");
                    // response = await getOrganiserByEmail();  Placeholder function
                } else {
                    throw new Error("Invalid user type");
                }
                // set common state
                setDob(response.dob);
                setPhone(response.phone);
                setId(response.id);
            } catch (error) {
                console.error("Error handling the response:", error);
                alert('Error fetching profile details.');
            }
        };
        fetchDetails();
    }, [isEditing]);

    // Update details
    const setDetails = async (id: string, matricNo: string, phone: string, dob: string) => {
        try {
            if (!validatePhoneNumber(phone)) {
                return; // Don't proceed if validation fails
            }

            if (userType === "STUDENT" && !validateMatriculationId(matricNo)) {
                return; // Don't proceed if validation fails
            }

            if (userType === "STUDENT") {
                await handleChangeDetails(id, matricNo, phone, dob);
            } else if (userType === "ORGANISER") {
                console.log("Update organizer details function goes here.");
                // await handleOrganiserChangeDetails(id, phone, dob);  Placeholder function
            } else {
                throw new Error("Invalid user type");
            }
            setIsEditing(false);
        } catch (error) {
            console.error("Error handling the response:", error);
            alert('Error updating profile details.');
        }
    }

    return (
        <div className="flex flex-col h-screen">
            <NavBar />
            <div className="flex-1 relative bg-floralwhite w-full overflow-hidden text-left text-base text-black font-ibm-plex-mono">
                <div className="flex flex-col justify-center items-center h-full space-y-6">
                    <div className="rounded-11xl bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] box-border w-[434px] h-auto overflow-hidden border-[0.5px] border-solid border-black flex flex-col justify-center items-center p-4 space-y-2">
                        <h2 className="text-xl font-semibold mb-6">{isEditing ? "Change Profile Details" : "Profile Details"}</h2>
                        {isEditing ? (
                            <div className="flex flex-col items-center space-y-6">
                                <input
                                    className="w-[300px] h-[46px] font-medium font-ibm-plex-mono text-xs bg-white rounded-xl box-border py-2.5 px-3 border-[1px] border-solid border-darkslateblue"
                                    type="date"
                                    placeholder="Date of Birth"
                                    value={dob}
                                    onChange={(e) => setDob(e.target.value)}
                                />
                                <input
                                    className="w-[300px] h-[46px] font-medium font-ibm-plex-mono text-xs bg-white rounded-xl box-border py-2.5 px-3 border-[1px] border-solid border-darkslateblue"
                                    type="text"
                                    placeholder="Phone"
                                    value={phone}
                                    onChange={(e) => setPhone(e.target.value)}
                                />
                                {userType === "STUDENT" && (
                                    <input
                                        className="w-[300px] h-[46px] font-medium font-ibm-plex-mono text-xs bg-white rounded-xl box-border py-2.5 px-3 border-[1px] border-solid border-darkslateblue"
                                        type="text"
                                        placeholder="Matric No."
                                        value={matricNo}
                                        onChange={(e) => setMatricNo(e.target.value)}
                                    />
                                )}
                                <button
                                    className="w-[300px] h-[46px] bg-darkslateblue text-white font-medium font-ibm-plex-mono text-xs rounded-xl hover:bg-black transition-colors duration-200 ease-in-out"
                                    onClick={() => setDetails(id, matricNo, phone, dob)}
                                >
                                    Update Profile
                                </button>
                            </div>
                        ) : (
                            <div className="flex flex-col items-center space-y-4">
                                <div className="text-darkslateblue font-medium">Date of Birth: <span className="text-black">{dob}</span></div>
                                <div className="text-darkslateblue font-medium">Phone: <span className="text-black">{phone}</span></div>
                                {userType === "STUDENT" && (
                                    <div className="text-darkslateblue font-medium">Matriculation Number: <span className="text-black">{matricNo}</span></div>
                                )}
                                <button
                                    className="w-[300px] h-[46px] bg-darkslateblue text-white font-medium font-ibm-plex-mono text-xs rounded-xl hover:bg-black transition-colors duration-200 ease-in-out"
                                    onClick={() => setIsEditing(true)}
                                >
                                    Edit Profile
                                </button>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ChangeProfile;

const validatePassword = (pwd: string) => {
        if (pwd.length < 8 || pwd.length > 20) {
            if (!toast.isActive(PASSWORD_TOAST_ID)) {
                toast.error(
                    "Password must be between 8 and 20 characters long.",
                    { toastId: PASSWORD_TOAST_ID }
                );
            }
            return false;
        } else {
            toast.dismiss(PASSWORD_TOAST_ID);
        }
        return true;
    };

const validatePhoneNumber = (phoneNumber: string) => {
        const pattern = /^\d{8}$/;
        if (!pattern.test(phoneNumber)) {
            if (!toast.isActive(PHONE_TOAST_ID)) {
                toast.error("Phone number must be 8 integers only.", {
                    toastId: PHONE_TOAST_ID,
                });
            }
            return false;
        } else {
            toast.dismiss(PHONE_TOAST_ID);
        }
        return true;
    };

const validateMatriculationId = (matriculationId: string) => {
        const pattern = /^\d{8}$/;
        if (!pattern.test(matriculationId)) {
            if (!toast.isActive(MATRICULATION_TOAST_ID)) {
                toast.error("Matriculation ID must be 8 integers only.", {
                    toastId: MATRICULATION_TOAST_ID,
                });
            }
            return false;
        } else {
            toast.dismiss(MATRICULATION_TOAST_ID);
        }
        return true;
    };

const validateName = (name: string) => {
        if (name.length < 5 || name.length > 15) {
            if (!toast.isActive(NAME_TOAST_ID)) {
                toast.error("Name must be between 5 and 15 characters long.", {
                    toastId: NAME_TOAST_ID,
                });
            }
            return false;
        } else {
            toast.dismiss(NAME_TOAST_ID);
        }
        return true;
    };

const validateEmail = (email: string) => {
        const pattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        if (!pattern.test(email)) {
            if (!toast.isActive(EMAIL_TOAST_ID)) {
                toast.error("Invalid email format.", {
                    toastId: EMAIL_TOAST_ID,
                });
            }
            return false;
        } else {
            toast.dismiss(EMAIL_TOAST_ID);
        }
        return true;
    };

const EMAIL_TOAST_ID = "email_toast_id_validation_toast";

const PHONE_TOAST_ID = "phone_toast_id_validation_toast";

const MATRICULATION_TOAST_ID = "matriculation_toast_id_validation_toast";

const NAME_TOAST_ID = "name_toast_id_validation_toast";

const PASSWORD_TOAST_ID = "password_toast_id_validation_toast";