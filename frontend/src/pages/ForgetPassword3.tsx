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
    <div className="flex flex-col h-screen">
      <NavBarLite />
      <div className="flex-1 relative bg-floralwhite w-full overflow-hidden text-left text-base text-black font-ibm-plex-mono">
        <div className="flex flex-col justify-center items-center h-full space-y-6">
          <div className="flex flex-col items-center space-y-6">
          <div className="rounded-11xl bg-white shadow-[0px_4px_4px_rgba(0,_0,_0,_0.25)] box-border w-[434px] h-[490px] overflow-hidden border-[0.5px] border-solid border-black flex flex-col justify-center items-center p-4 space-y-3">
              <h2 className="text-xl font-semibold mb-0">Reset Password</h2>


              <div className="w-[300px] mb-2">
                <label className="block mb-1 font-medium">Email</label>
                <input
                  className="w-full font-medium font-ibm-plex-mono text-xs bg-white rounded-xl box-border py-2.5 px-3 border-[1px] border-solid border-darkslateblue"
                  type="email"
                  placeholder="Enter Email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>

              <div className="w-[300px] mb-2">
                <label className="block mb-1 font-medium">Reset Token</label>
                <input
                  className="w-full font-medium font-ibm-plex-mono text-xs bg-white rounded-xl box-border py-2.5 px-3 border-[1px] border-solid border-darkslateblue"
                  type="text"
                  placeholder="Enter Reset Token"
                  value={resetToken}
                  onChange={(e) => setResetToken(e.target.value)}
                />
              </div>

              <div className="w-[300px] mb-2">
                <label className="block mb-1 font-medium">New Password</label>
                <input
                  className="w-full font-medium font-ibm-plex-mono text-xs bg-white rounded-xl box-border py-2.5 px-3 border-[1px] border-solid border-darkslateblue"
                  type="password"
                  placeholder="Enter New Password"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                />
              </div>

              <div className="w-[300px] mb-2">
                <label className="block mb-1 font-medium">Confirm Password</label>
                <input
                  className="w-full font-medium font-ibm-plex-mono text-xs bg-white rounded-xl box-border py-2.5 px-3 border-[1px] border-solid border-darkslateblue"
                  type="password"
                  placeholder="Confirm New Password"
                  value={confirmPassword}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                />
              </div>

              <button
                className="cursor-pointer rounded-xl bg-floralwhite box-border w-[300px] h-[46px] flex flex-col py-2.5 px-3 items-center justify-center border-[1px] border-solid border-black transition-transform duration-100 ease-in-out mt-4"
                onClick={handleResetPassword}

              >
                Reset Password
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ResetPassword;