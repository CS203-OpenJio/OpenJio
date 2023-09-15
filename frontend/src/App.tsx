import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { createContext } from "react";
import HomeScreen from "./pages/HomeScreen";
import CentralHub from "./pages/CentralHub";
import LoginPage from "./pages/LoginPage";
import EventPage from "./pages/EventPage";
import TicketPurchased from "./pages/TicketPurchased";
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
    path: "/login",
    element: <LoginPage />,
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
]);

// Contexts allows components to pass information down without explicitly passing props
// We can pass light/dark mode states through these, and user info as well?
const ThemeContext = createContext('light');
const AuthContext = createContext(null);

const App = () => {
  return (
        <RouterProvider router={router} />
  );
};

export default App;
