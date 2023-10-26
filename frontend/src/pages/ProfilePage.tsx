import { FunctionComponent, useState, useEffect } from "react";
import NavBar from "../components/NavBar";
import { getStudentByEmail, handleChangeDetails } from "../utils/ProfileController";
import { setEngine } from "crypto";

const ChangeProfile: FunctionComponent = () => {
    const [dob, setDob] = useState("");
    const [matricNo, setMatricNo] = useState("");
    const [phone, setPhone] = useState("");
    const [studentId, setStudentId] = useState("");
    const [isEditing, setIsEditing] = useState(false);



    useEffect(() => {
        // Fetch student details
        const fetchStudentDetails = async () => {
            try {
                // make API call
                const response = await getStudentByEmail();
                // set state
                setDob(response.dob);
                setMatricNo(response.matricNo);
                setPhone(response.phone);
                setStudentId(response.id);
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
        <div className="h-screen bg-floralwhite text-darkslateblue font-ibm-plex-mono">
            <NavBar />
            <div className="flex justify-center items-center h-full">
                <div className="bg-white p-8 rounded-xl shadow-xl w-full max-w-md border-4 border-darkslateblue shadow-2xl">
                    <h2 className="text-2xl mb-6 text-center text-darkslateblue font-semibold">
                        {isEditing ? "Change Profile Details" : "Profile Details"}
                    </h2>
                    {isEditing ? (
                        <div className="flex flex-col items-center">
                            <input
                                className="w-4/5 p-2 mb-4 border rounded-xl focus:ring focus:ring-darkslateblue transition ease-in-out border-darkslateblue"
                                type="date"
                                placeholder="Date of Birth"
                                value={dob}
                                onChange={(e) => setDob(e.target.value)}
                            />
                            <input
                                className="w-4/5 p-2 mb-4 border rounded-xl focus:ring focus:ring-darkslateblue transition ease-in-out border-darkslateblue"
                                type="text"
                                placeholder="Matric No."
                                value={matricNo}
                                onChange={(e) => setMatricNo(e.target.value)}
                            />
                            <input
                                className="w-4/5 p-2 mb-4 border rounded-xl focus:ring focus:ring-darkslateblue transition ease-in-out border-darkslateblue"
                                type="text"
                                placeholder="Phone"
                                value={phone}
                                onChange={(e) => setPhone(e.target.value)}
                            />
                            <button
                                className="w-4/5 p-2 bg-darkslateblue text-white rounded-xl hover:bg-black focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-darkslateblue"
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
                                className="w-full p-2 bg-darkslateblue text-white rounded-xl hover:bg-black focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-darkslateblue"
                                onClick={() => setIsEditing(true)}
                            >
                                Edit Profile
                            </button>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ChangeProfile;