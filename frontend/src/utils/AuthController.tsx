import JWT from "./JWT";

const handleLogin = async (username: string, password: string) => {
  try {
    console.log(username, password);
    const response = await JWT.post("/api/v1/auth/login", {
      email: username,
      password: password,
    });
    console.log(response);
    if (response.status == 200) {
      const token = response.data.token;
      await localStorage.setItem("token", token);
      console.log("new", token);
    }
  } catch (err) {
    console.log(err);
  }
};

const handleSignUp = async (
  name: string,
  email: string,
  password: string,
  matricNo: string,
  phone: string
) => {
  const body = {
    name: name,
    email: email,
    password: password,
    matricNo: matricNo,
    phone: phone,
    userType: "S",
  };

  const response = await JWT.post("/api/v1/auth/register", body);

  console.log(response.data);
};

export { handleLogin, handleSignUp };
