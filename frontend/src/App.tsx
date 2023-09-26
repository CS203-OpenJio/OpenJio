import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { createContext, Dispatch, SetStateAction, useState } from "react";
import HomeScreen from "src/pages/HomeScreen";
import CentralHub from "src/pages/CentralHub";
import LoginPage from "src/pages/LoginPage";
import EventPage from "src/pages/EventPage";
import TicketPurchased from "src/pages/TicketPurchased";
import EventForm from "./pages/EventForm";
import SignUpPage from "./pages/SignUpPage";
import Schedule from "./pages/Schedule";

import ForgetPassword1 from "src/pages/ForgetPassword1";
import ForgetPassword2 from "src/pages/ForgetPassword2";
import LogoutPage from "src/pages/LogoutPage";

// import { Route } from "react-router-dom";

// create the configuration for a router by simply passing
// arguments in the form of an array of routes
type UserType = {
  username: string;
  password: string;
  userId: number;
};

const initialUser: UserType = {
  username: "",
  password: "",
  userId: 0,
};

type AuthContextType = {
  user: UserType;
  setUser: Dispatch<SetStateAction<UserType>>;
};

export const AuthContext = createContext<AuthContextType>({
  user: initialUser,
  setUser: () => {},
});

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomeScreen />,
  },
  {
    path: "/centralhub",
    element: <CentralHub />,
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/eventform",
    element: <EventForm />,
  },
  {
    path: "/forgetpassword2",
    element: <ForgetPassword2 />,
  },
  {
    path: "/forgetpassword1",
    element: <ForgetPassword1 />,
  },
  // might need to update below as they require data passed through them for them to load
  {
    path: "/eventpage",
    element: <EventPage />,
  },
  {
    path: "/purchased",
    element: <TicketPurchased />,
  },
  {
    path: "/signup",
    element: <SignUpPage />,
  },
  {
    path: "/logout",
    element: <LogoutPage />,
  },
  {
    path: "/schedule",
    element: <Schedule />,
  },
]);

const App = () => {
  const [user, setUser] = useState({
    username: "admin",
    password: "admin",
    userId: 1,
  });

  const value = { user, setUser };

  return (
    <AuthContext.Provider value={value}>
      <RouterProvider router={router} />
    </AuthContext.Provider>
  );
};

export default App;
