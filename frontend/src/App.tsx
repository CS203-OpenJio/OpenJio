import { createBrowserRouter, RouterProvider } from "react-router-dom";
import HomeScreen from "./pages/HomeScreen";
import CentralHub from "./pages/CentralHub";
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
]);

const App = () => {
  return <RouterProvider router={router} />;
};

export default App;
