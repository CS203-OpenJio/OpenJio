import { FunctionComponent, useState } from "react";
import { useNavigate } from "react-router-dom";

import NavBar from "../components/NavBar";
import { Link, useSearchParams } from "react-router-dom";

import { useEffect } from "react";
import axios from "axios";

import JWT from "../utils/JWT";

const ChangeProfile: FunctionComponent = () => {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const studentId = searchParams.get("studentId");

    const [name, setName] = useState("");
    const [matricNo, setMatricNo] = useState("");
    const [phone, setPhone] = useState("");
    const dob = null;
    const image = null;
    const smuCreditScore = null;


  

    const handleChangeDetails = async () => {
        if (!studentId) {
            alert("Student ID is missing!");
            return;
        }

        try {
            const response = await JWT.put(`http://localhost:8080/api/v1/students/id/${studentId}`, {
                matricNo: matricNo,
                phone: phone,
                image: image,
                dob: dob,
                smuCreditScore: smuCreditScore
            });

            if (response.status === 200) {
                alert('Profile details updated successfully!');
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
            <div className="flex justify-center items-center h-screen">
                <div className="bg-gray-100 p-8 rounded-lg shadow-md">
                    <h2 className="text-2xl mb-6 text-center">Change Profile Details</h2>
                    <input 
                        className="block w-full mb-4 p-2 border rounded"
                        type="text"
                        placeholder="Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
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
        </div>
    );
};

export default ChangeProfile;