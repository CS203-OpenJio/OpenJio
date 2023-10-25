import axios from "axios";
import JWT from "./JWT";

const handleLogin = async (username: string, password: string) => {
  console.log(username, password);
  let response = await JWT.post("/api/v1/auth/login", {
    email: username,
    password: password,
  });
  console.log(response);
  if (response.status == 200) {
    const token = response.data.token;
    localStorage.setItem("token", token);

    // Get user type
    try {
      response = await JWT.post(`http://localhost:8080/api/v1/students/email`, { email: username });
    } catch (err) {
      response = await JWT.post(`http://localhost:8080/api/v1/organisers/email`, { email: username });
    }
    if (response.data) {
      console.log(response.data);
      localStorage.setItem("userType", response.data.role);
    } else {
      throw new Error("User not found");
    }
  }
};

const handleSignUp = async (
  name: string,
  email: string,
  password: string,
  matricNo: string,
  phone: string
) => {

  const response = await axios.post("http://localhost:8080/api/v1/auth/register", {
    name: name,
    email: email,
    password: password,
    matricNo: matricNo,
    phone: phone,
    userType: "S",
  });

  if (response.status == 201) {
    const token = response.data.token;
    await localStorage.setItem("token", token);
  }
};

export { handleLogin, handleSignUp };
