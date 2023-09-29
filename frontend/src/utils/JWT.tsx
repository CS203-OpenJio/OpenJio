import axios from "axios";

// this is to set the JWT token

// import this file whenever you want to use the JWT token
// and the interceptor will make sure the token is always in
// header

const JWT = axios.create({
  baseURL: "http://localhost:8080/",
});

JWT.interceptors.request.use(
  (config: any) => {
    // console.log("config", config);
    if (
      config.url === "/api/v1/auth/login" ||
      config.url === "/api/vi/auth/register"
    )
      return config;

    const token = localStorage.getItem("token");
    // console.log("old", token);
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default JWT;
