import { FunctionComponent, useState } from "react";
import { useNavigate } from "react-router-dom";

const ResetPassword: FunctionComponent = () => {
  const navigate = useNavigate();


const [resetToken, setResetToken] = useState("");
const [newPassword, setNewPassword] = useState("");
const [confirmPassword, setConfirmPassword] = useState("");

const handleResetPassword = async () => {
  if (newPassword === confirmPassword) {
      const response = await fetch('http://localhost:8080/api/v1/forgot-password/', {
          method: 'POST',
          headers: {
              'Authorization': 'Basic ' + btoa('admin@admin.com:admin'), // Assuming the provided Basic auth credentials
              'Content-Type': 'application/json',
          },
          body: JSON.stringify({
              email: "", // You might need to provide the email here if required by the backend
              resetPasswordToken: resetToken,
              newPassword: newPassword,
          }),
      });

      const data = await response.json();

      if (response.ok) {
          alert('Password reset successfully!');
          navigate('/login'); // Redirect to login page or any other page
      } else {
          alert(data.message || 'Error resetting password.');
      }
  } else {
      alert('Passwords do not match.');
  }
};

return (
  <div>
    <h2>Reset Password</h2>
    <input 
        type="text" 
        placeholder="Enter Reset Token" 
        value={resetToken} 
        onChange={(e) => setResetToken(e.target.value)}
    />
    <input 
        type="password" 
        placeholder="Enter New Password" 
        value={newPassword} 
        onChange={(e) => setNewPassword(e.target.value)}
    />
    <input 
        type="password" 
        placeholder="Confirm New Password" 
        value={confirmPassword} 
        onChange={(e) => setConfirmPassword(e.target.value)}
    />
    <button onClick={handleResetPassword}>
        Reset Password
    </button>
  </div>
);
};

export default ResetPassword;