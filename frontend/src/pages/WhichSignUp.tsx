import { Link, useNavigate } from "react-router-dom";
import NavBarLite from "../components/HomeScreen/NavBarLite";

const WhichSignUp = () => {
  const navigate = useNavigate();

  const handleSuccess = (input: string) => {
    navigate(input);
  };

  return <div className="w-100"></div>;
};

export default WhichSignUp;
