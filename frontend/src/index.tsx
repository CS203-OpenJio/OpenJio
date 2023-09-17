import React from "react";
import ReactDOM from "react-dom/client";
import { createRoot } from "react-dom/client";
import App from "./App";
import "./global.css";
import reportWebVitals from "./reportWebVitals";
import { BrowserRouter } from "react-router-dom";

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

const container = document.getElementById("root");
const root = createRoot(container!);

reportWebVitals();