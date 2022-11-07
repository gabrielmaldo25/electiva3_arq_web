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
import IndexReglas from "./routes/reglas/index";
import IndexConceptos, {
  action as conceptoRootAction,
  loader as conceptoRootLoader,
} from "./routes/conceptos/index";
import ABMConcepto, {
  loader as conceptoLoader,
  action as conceptoAction,
  destroyConcepto,
} from "./routes/conceptos/abmConcepto";

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
          path: ":idCliente",
          element: <Cliente />,
          loader: clienteLoader,
          errorElement: <ErrorPage />,
        },
        {
          path: ":idCliente/edit",
          element: <EditCliente />,
          loader: clienteLoader,
          action: editAction,
          errorElement: <ErrorPage />,
        },
        {
          path: "new",
          element: <EditCliente />,
          loader: clienteLoader,
          action: editAction,
          errorElement: <ErrorPage />,
        },
        {
          path: ":idCliente/destroy",
          action: destroyAction,
          errorElement: <ErrorPage />,
        },
      ],
    },
    {
      path: "reglas",
      element: <IndexReglas />,
      errorElement: <ErrorPage />,
    },
    {
      path: "conceptos",
      element: <IndexConceptos />,
      errorElement: <ErrorPage />,
      action: conceptoRootAction,
      loader: conceptoRootLoader,
      children: [
        {
          path: ":idConcepto/edit",
          element: <ABMConcepto />,
          loader: conceptoLoader,
          action: conceptoAction,
          errorElement: <ErrorPage />,
        },
        {
          path: "new",
          element: <ABMConcepto />,
          loader: conceptoLoader,
          action: conceptoAction,
          errorElement: <ErrorPage />,
        },
        {
          path: ":idConcepto/edit/destroy",
          action: destroyConcepto,
          errorElement: <ErrorPage />,
        },
      ],
    },
  ]);
  return <RouterProvider router={router} />;
}
