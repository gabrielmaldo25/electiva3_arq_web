import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import Root, {
  loader as rootLoader,
  action as rootAction,
} from "./routes/clientes/root";
import ErrorPage from "./error-page";
import Cliente, { loader as clienteLoader } from "./routes/clientes/cliente";
import EditCliente, { action as editAction } from "./routes/clientes/edit";
import { action as destroyAction } from "./routes/clientes/destroy";
import Index from "./routes/clientes/index";
import IndexDashboard from "./routes/dashboard/index";
import IndexPuntos from "./routes/puntos/index";

export default function Main() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <IndexDashboard />,
      errorElement: <ErrorPage />,
    },
    {
      path: "clientes",
      element: <Root />,
      errorElement: <ErrorPage />,
      loader: rootLoader,
      action: rootAction,

      children: [
        { index: true, element: <Index />, errorElement: <ErrorPage /> },

        {
          path: ":clienteId",
          element: <Cliente />,
          loader: clienteLoader,
          errorElement: <ErrorPage />,
        },
        {
          path: ":clienteId/edit",
          element: <EditCliente />,
          loader: clienteLoader,
          action: editAction,
          errorElement: <ErrorPage />,
        },
        {
          path: ":clienteId/destroy",
          action: destroyAction,
          errorElement: <ErrorPage />,
        },
      ],
    },
    {
      path: "puntos",
      element: <IndexPuntos />,
      errorElement: <ErrorPage />,
    }
  ]);
  return <RouterProvider router={router} />;
}
