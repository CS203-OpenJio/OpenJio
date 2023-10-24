import { FunctionComponent, useState, useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import axios from "axios";
import JWT from "../utils/JWT";
import NavBar from "../components/NavBar";

const ChangeProfile: FunctionComponent = () => {
    const navigate = useNavigate();
    const studentId = 3; //hardcoded

    useEffect(() => {
        const fetchStudentDetails = async () => {
            if (!studentId) return; // Ensure studentId is available
            try {
                const response = await JWT.get(`http://localhost:8080/api/v1/students/id/3`); //hardcoded
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
                setIsEditing(false);  // This line makes it go back to display mode
            } else {
                alert(response.data.message || 'Error updating profile details.');
            }
        } catch (error) {
            console.error("Error handling the response:", error);
            alert('Error updating profile details.');
        }
    };

    return (
        <div>
            <NavBar />
            {isEditing ? (
                <div className="flex justify-center items-center h-screen">
                    <div className="bg-gray-100 p-8 rounded-lg shadow-md">
                        <h2 className="text-2xl mb-6 text-center">Change Profile Details</h2>
                        <input 
                            className="block w-full mb-4 p-2 border rounded"
                            type="date"
                            placeholder="Date of Birth"
                            value={dob}
                            onChange={(e) => setDob(e.target.value)}
                        />
                        <input 
                            className="block w-full mb-4 p-2 border rounded"
                            type="text"
                            placeholder="Matric No."
                            value={matricNo}
                            onChange={(e) => setMatricNo(e.target.value)}
                        />
                        <input 
                            className="block w-full mb-4 p-2 border rounded"
                            type="text"
                            placeholder="Phone"
                            value={phone}
                            onChange={(e) => setPhone(e.target.value)}
                        />
                        <button 
                            className="block w-full p-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                            onClick={handleChangeDetails}
                        >
                            Update Profile
                        </button>
                    </div>
                </div>
            ) : (
                <div className="flex justify-center items-center h-screen">
                    <div className="bg-gray-100 p-8 rounded-lg shadow-md">
                        <h2 className="text-2xl mb-6 text-center">Profile Details</h2>
                        <div className="mb-4">Date of Birth: {dob}</div>
                        <div className="mb-4">Matriculation Number: {matricNo}</div>
                        <div className="mb-4">Phone: {phone}</div>
                        <button 
                            className="block w-full p-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                            onClick={() => setIsEditing(true)}
                        >
                            Edit Profile
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ChangeProfile;