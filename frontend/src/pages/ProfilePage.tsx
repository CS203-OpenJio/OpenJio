import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { FunctionComponent, useState, useEffect } from "react";
import NavBar from "../components/NavBar";
import { getStudentByEmail, handleChangeDetails } from "../utils/ProfileController";
import { Progress } from "../components/ui/progress"

const ChangeProfile: FunctionComponent = () => {
    const [dob, setDob] = useState("");
    const [matricNo, setMatricNo] = useState("");
    const [phone, setPhone] = useState("");
    const [studentId, setStudentId] = useState("");
    const [score, setScore] = useState(0);
    const [isEditing, setIsEditing] = useState(false);




    useEffect(() => {
        // Fetch student details
        const fetchStudentDetails = async () => {
            try {
                // make API call
                const response = await getStudentByEmail();
                console.log(response)
                // set state
                setDob(response.dob);
                setMatricNo(response.matricNo);
                setPhone(response.phone);
                setStudentId(response.id);
                setScore(response.smuCreditScore);
            } catch (error) {
                console.error("Error handling the response:", error);
                alert('Error fetching profile details.');
            }
        };
        fetchStudentDetails();
    }, [isEditing]);

    // Update student details
    const setDetails = (studentId: string, matricNo: string, phone: string, dob: string) => {
        try {
            if (
!validatePhoneNumber(phone) ||
!validateMatriculationId(matricNo) ) {
    return; // Don't proceed if validation fails
}

// make API call
            handleChangeDetails(studentId, matricNo, phone, dob);
            // set state
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
                <div className="flex flex-col justify-center items-center h-full">
                    <div className="bg-white p-8 rounded-xl shadow-xl w-full max-w-md border-4 border-darkslateblue">
                        <h2 className="text-2xl mb-6 text-center text-darkslateblue font-semibold">
                            {isEditing ? "Change Profile Details" : "Profile Details"}
                        </h2>
                        {isEditing ? (
                            <div className="flex flex-col items-center">
                                <input
                                    className="w-4/5 p-2 mb-4 rounded-xl focus:ring focus:ring-darkslateblue transition ease-in-out border border-darkslateblue"
                                    type="date"
                                    placeholder="Date of Birth"
                                    value={dob}
                                    onChange={(e) => setDob(e.target.value)}
                                />
                                <input
                                    className="w-4/5 p-2 mb-4 rounded-xl focus:ring focus:ring-darkslateblue transition ease-in-out border border-darkslateblue"
                                    type="text"
                                    placeholder="Matric No."
                                    value={matricNo}
                                    onChange={(e) => setMatricNo(e.target.value)}
                                />
                                <input
                                    className="w-4/5 p-2 mb-4 rounded-xl focus:ring focus:ring-darkslateblue transition ease-in-out border border-darkslateblue"
                                    type="text"
                                    placeholder="Phone"
                                    value={phone}
                                    onChange={(e) => setPhone(e.target.value)}
                                />
                                <button
                                    className="w-4/5 p-2 bg-darkslateblue text-white rounded-xl hover:bg-black focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-darkslateblue transition-transform duration-100 ease-in-out transform active:scale-95"
                                    onClick={() => setDetails(studentId, matricNo, phone, dob)}
                                >
                                    Update Profile
                                </button>
                            </div>
                        ) : (
                            <>
                                <div className="mb-4 text-darkslateblue font-medium">Date of Birth: <span className="text-black">{dob}</span></div>
                                <div className="mb-4 text-darkslateblue font-medium">Matriculation Number: <span className="text-black">{matricNo}</span></div>
                                <div className="mb-4 text-darkslateblue font-medium">Phone: <span className="text-black">{phone}</span></div>
                                <button
                                    className="w-full p-2 bg-darkslateblue text-white rounded-xl hover:bg-black focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-darkslateblue transition-transform duration-100 ease-in-out transform active:scale-95"
                                    onClick={() => setIsEditing(true)}
                                >
                                    Edit Profile
                                </button>
                            </>
                        )}
                        <div className="flex flex-row items-center mt-12">
                        </div>
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