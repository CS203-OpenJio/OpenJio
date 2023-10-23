import { createBrowserRouter, RouterProvider } from "react-router-dom";
import HomeScreen from "src/pages/HomeScreen";
import CentralHub from "src/pages/CentralHub";
import LoginPage from "src/pages/LoginPage";
import EventPage from "src/pages/EventPage";
import TicketPurchased from "src/pages/TicketPurchased";
import EventForm from "./pages/EventForm";
import SignUpPage from "./pages/SignUpPage";
import Schedule from "./pages/Schedule";

import ForgetPassword2 from "src/pages/ForgetPassword2";
import ForgetPassword3 from "src/pages/ForgetPassword3";
import Profilepage from "src/pages/ProfilePage";
import LogoutPage from "src/pages/LogoutPage";
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from "react-toastify";

// import { Route } from "react-router-dom";

// create the configuration for a router by simply passing
// arguments in the form of an array of routes

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
    path: "/profilepage",
    element: <Profilepage />,
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

  // might need to update below as they require data passed through them for them to load
  {
    path: "/forgetpassword3",
    element: <ForgetPassword3 />
  },
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
  return (
  <>
  <ToastContainer />
  <RouterProvider router={router} />;
  </>
  );
}

export default App;
