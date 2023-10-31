import axios from "axios";
import JWT from "./JWT";

const handleLogin = async (username: string, password: string) => {
  let response;
  try {
    response = await JWT.post("/api/v1/auth/login", {
      email: username,
      password: password,
    });
  } catch (err: any) {
    throw err.response.data.message;
  }
  if (response.status == 200) {
    const token = response.data.token;
    localStorage.setItem("token", token);

    // Get user type
    try {
      response = await JWT.post(`/api/v1/students/email`, { email: username });
    } catch (err) {
      response = await JWT.post(`/api/v1/organisers/email`, { email: username });
    }
    if (response.data) {
      localStorage.setItem("userType", response.data.role);
    } else {
      throw new Error("User not found");
    }
  }
};

interface User {
  name: string;
  email: string;
  password: string;
  matricNo?: string;
  phone: string;
  userType: string;
}

const handleSignUp = async (user: User) => {
  const response = await JWT.post("/api/v1/auth/register", {
    name: user.name,
    email: user.email,
    password: user.password,
    matricNo: user.matricNo,
    phone: user.phone,
    userType: user.userType,
  });

  if (response.status == 201) {
    const token = response.data.token;
    await localStorage.setItem("token", token);
  }
};

export { handleLogin, handleSignUp };
