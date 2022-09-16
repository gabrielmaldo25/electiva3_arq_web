import React from "react";
import ReactDOM from "react-dom/client";
import {
  createBrowserRouter,
  RouterProvider,
  Route,
  Routes,
  BrowserRouter,
} from "react-router-dom";
import "./index.css";
import Root, {
  loader as rootLoader,
  action as rootAction,
} from "./routes/root";
import ErrorPage from "./error-page";
import Contact, { loader as contactLoader } from "./routes/contact";
import EditContact, { action as editAction } from "./routes/edit";
import { action as destroyAction } from "./routes/destroy";
import Index from "./routes/index";
import Layout from "./components/layout";
import IndexClientes from "./routes/clientes/index";
import IndexDashboard from "./routes/dashboard/index";

export default function Main() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <IndexDashboard />,
      errorElement: <ErrorPage />,
    },
    {
      path: "contacts",
      element: <Root />,
      errorElement: <ErrorPage />,
      loader: rootLoader,
      action: rootAction,

      children: [
        { index: true, element: <Index />, errorElement: <ErrorPage /> },

        {
          path: ":contactId",
          element: <Contact />,
          loader: contactLoader,
          errorElement: <ErrorPage />,
        },
        {
          path: ":contactId/edit",
          element: <EditContact />,
          loader: contactLoader,
          action: editAction,
          errorElement: <ErrorPage />,
        },
        {
          path: ":contactId/destroy",
          action: destroyAction,
          errorElement: <ErrorPage />,
        },
      ],
    },
  ]);
  return <RouterProvider router={router} />;
}
