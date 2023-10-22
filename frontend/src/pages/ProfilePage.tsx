import { FunctionComponent, useState } from "react";
import { useNavigate } from "react-router-dom";
import NavBarLite from "@/components/HomeScreen/NavBarLite";
import NavBar from "../components/NavBar";

const ChangeProfile: FunctionComponent = () => {
    const navigate = useNavigate();
    const [name, setName] = useState("");
    const [matricNo, setMatricNo] = useState("");
    const [phone, setPhone] = useState("");

    const handleChangeDetails = async () => {
        const response = await fetch('http://localhost:8080/api/v1/students/id/2', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                Name: name,
                matricNo: matricNo,
                phone: phone,
                image: null
            }),
        });

        if (response.ok) {
            alert('Profile details updated successfully!');
            // Optionally, navigate to another page after updating
            // navigate('/some-page');
        } else {
            const data = await response.json();
            alert(data.message || 'Error updating profile details.');
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