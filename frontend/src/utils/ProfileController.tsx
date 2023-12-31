import JWT from "../utils/JWT";
import jwt_decode, { JwtPayload } from "jwt-decode";

// Get student details by email
const getStudentByEmail = async () => {
    // Get student email from token
    const token: any = localStorage.getItem("token");
    const objToken: JwtPayload = jwt_decode(token);
    const userEmail = objToken.sub;

    try {
        // make API call
        const response = await JWT.post(`/api/v1/students/email`, {email: userEmail});
        // if response is successful, return data
        if (response.data) {
            console.log(response.data);
            return response.data;
        } else {
            throw new Error("Error fetching student data");
        }
    } catch (error) {
        throw new Error("Error fetching student data");
    }
};

// Update student details
const handleChangeDetails = async (studentId:String, matricNo:String, phone:String, dob:String) => {
    // if studentId is empty, throw error
    if (!studentId) {
        throw new Error("Student ID is missing!");
    }
    try {
        // make API call
        const response = await JWT.put(`/api/v1/students/${studentId}`, {
            matricNo: matricNo,
            phone: phone,
            dob: dob,
        });
        // if response is successful, return data
        if (response.status === 200) {
            alert('Profile details updated successfully!');
        } else {
           throw new Error("Error updating profile details.");
        }
    } catch (error) {
        throw new Error("Error updating profile details.");
    }
};


export { getStudentByEmail, handleChangeDetails };