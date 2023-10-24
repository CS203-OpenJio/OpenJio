import { FunctionComponent, useState, useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import axios from "axios";
import JWT from "../utils/JWT";
import NavBar from "../components/NavBar";


import jwt_decode, { JwtPayload } from "jwt-decode";

const ChangeProfile: FunctionComponent = () => {
    const token: any = localStorage.getItem("token");
    const objToken: JwtPayload = jwt_decode(token);
    const userEmail = objToken.sub; //ill use this after testing

    const email = "pramitsbabu@gmail.com"; //hardcoded to test 

    const getStudentIdByEmail = async () => { //this is where the error is it wont take my email despite authentication
        try {
            const response = await JWT.post(`http://localhost:8080/api/v1/students/${email}/`);
            if (response.data && response.data.id) {
                return response.data.id;
            } else {
                console.error("Student ID not found in response");
                return null;
            }
        } catch (error) {
            console.error("Error fetching student ID:", error);
            return null;
        }
    };
    



    const [studentId, setStudentId] = useState<number | null>(null);

    useEffect(() => {
        const fetchStudentId = async () => {
            const id = await getStudentIdByEmail();
            if (id) {
                setStudentId(id);
            }
        };

        fetchStudentId();
    }, [email]);

    useEffect(() => {
        const fetchStudentDetails = async () => {
            if (!studentId) return; // Ensure studentId is available
            try {
                const response = await JWT.get(`http://localhost:8080/api/v1/students/id/${studentId}`);
                if (response.data) {
                    setDob(response.data.dob || "");
                    setMatricNo(response.data.matricNo || "");
                    setPhone(response.data.phone || "");
                } else {
                    console.error("Student details not found in response");
                }
            } catch (error) {
                console.error("Error fetching student details:", error);
            }
        };

        fetchStudentDetails();
    }, [studentId]);

    const [dob, setDob] = useState("");
    const [matricNo, setMatricNo] = useState("");
    const [phone, setPhone] = useState("");
    const [isEditing, setIsEditing] = useState(false);

    const handleChangeDetails = async () => {
        if (!studentId) {
            alert("Student ID is missing!");
            return;
        }
        try {
            const response = await JWT.put(`http://localhost:8080/api/v1/students/id/${studentId}`, {
                matricNo: matricNo,
                phone: phone,
                dob: dob,
            });
            if (response.status === 200) {
                alert('Profile details updated successfully!');
                setIsEditing(false);
            } else {
                alert(response.data.message || 'Error updating profile details.');
            }
        } catch (error) {
            console.error("Error handling the response:", error);
            alert('Error updating profile details.');
        }
    };

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
                                onClick={handleChangeDetails}
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