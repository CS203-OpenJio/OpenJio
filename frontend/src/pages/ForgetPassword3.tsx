import { FunctionComponent, useState } from "react";
import { useNavigate } from "react-router-dom";
import NavBarLite from "../components/HomeScreen/NavBarLite";

const ResetPassword: FunctionComponent = () => {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [resetToken, setResetToken] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const handleResetPassword = async () => {
    if (newPassword === confirmPassword) {
      try {
        const response = await fetch('http://localhost:8080/api/v1/forgot-password/', {
          method: 'POST',
          headers: {
            'Authorization': 'Basic ' + btoa('admin@admin.com:admin'),
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: email,
            resetPasswordToken: resetToken,
            newPassword: newPassword,
          }),
        });
  
        let data;
        if (response.headers.get('content-type')?.includes('application/json')) {
          data = await response.json();
        } else {
          data = await response.text();
        }
  
        if (response.ok) {
          alert('Password reset successfully!');
          navigate('/login');
        } else {
          alert(data.message || data || 'Error resetting password.');
        }
      } catch (error) {
        console.error("Error fetching or parsing data:", error);
        alert("An error occurred while resetting the password. Please try again.");
      }
    } else {
      alert('Passwords do not match.');
    }
  };

  return (
    <div>
      <NavBarLite />
      <div className="flex justify-center items-center h-screen">
        <div className="bg-gray-100 p-8 rounded-lg shadow-md">
          <h2 className="text-2xl mb-6 text-center">Reset Password</h2>
          <input 
              className="block w-full mb-4 p-2 border rounded"
              type="email" 
              placeholder="Enter Email" 
              value={email} 
              onChange={(e) => setEmail(e.target.value)}
          />
          <input 
              className="block w-full mb-4 p-2 border rounded"
              type="text" 
              placeholder="Enter Reset Token" 
              value={resetToken} 
              onChange={(e) => setResetToken(e.target.value)}
          />
          <input 
              className="block w-full mb-4 p-2 border rounded"
              type="password" 
              placeholder="Enter New Password" 
              value={newPassword} 
              onChange={(e) => setNewPassword(e.target.value)}
          />
          <input 
              className="block w-full mb-4 p-2 border rounded"
              type="password" 
              placeholder="Confirm New Password" 
              value={confirmPassword} 
              onChange={(e) => setConfirmPassword(e.target.value)}
          />
          <button 
              className="block w-full p-2 bg-blue-500 text-white rounded hover:bg-blue-600"
              onClick={handleResetPassword}
          >
              Reset Password
          </button>
        </div>
      </div>
    </div>
  );
};

export default ResetPassword;