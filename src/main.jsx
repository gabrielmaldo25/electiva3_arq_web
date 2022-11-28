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
import IndexDashboard, {
  action as pagoRootAction,
  loader as pagoRootLoader,
} from "./routes/dashboard/index";
import ABMPago, {
  loader as pagoLoader,
  action as pagoAction,
  destroyPago,
} from "./routes/dashboard/abmPago";
import IndexReglas, {
  action as reglaRootAction,
  loader as reglaRootLoader,
} from "./routes/reglas/index";
import IndexConceptos, {
  action as conceptoRootAction,
  loader as conceptoRootLoader,
} from "./routes/conceptos/index";
import ABMConcepto, {
  loader as conceptoLoader,
  action as conceptoAction,
  destroyConcepto,
} from "./routes/conceptos/abmConcepto";

import ABMRegla, {
  loader as reglaLoader,
  action as reglaAction,
  destroyRegla,
} from "./routes/reglas/abmRegla";

import IndexCanjes, {
  action as canjesRootAction,
  loader as canjesRootLoader,
} from "./routes/canjes/index";
import ABMCanje, {
  //loader as canjeLoader,
  action as canjeAction,
} from "./routes/canjes/abmCanje";

import IndexSorteos, {
  action as sorteosRootAction,
  loader as sorteosRootLoader,
} from "./routes/sorteos/index";
import ABMSorteo, {
  //loader as canjeLoader,
  action as sorteoAction,
} from "./routes/sorteos/abmSorteo";

export default function Main() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <IndexDashboard />,
      errorElement: <ErrorPage />,
      action: pagoRootAction,
      loader: pagoRootLoader,
      children: [
        {
          path: ":idPago/edit",
          element: <ABMPago />,
          loader: pagoLoader,
          action: pagoAction,
          errorElement: <ErrorPage />,
        },
        {
          path: "/pagos/new",
          element: <ABMPago />,
          loader: pagoLoader,
          action: pagoAction,
          errorElement: <ErrorPage />,
        },
        {
          path: ":idPago/edit/destroy",
          action: destroyPago,
          errorElement: <ErrorPage />,
        },
      ],
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
      action: reglaRootAction,
      loader: reglaRootLoader,
      children: [
        {
          path: ":idRegla/edit",
          element: <ABMRegla />,
          loader: reglaLoader,
          action: reglaAction,
          errorElement: <ErrorPage />,
        },
        {
          path: "new",
          element: <ABMRegla />,
          loader: reglaLoader,
          action: reglaAction,
          errorElement: <ErrorPage />,
        },
        {
          path: ":idRegla/edit/destroy",
          action: destroyRegla,
          errorElement: <ErrorPage />,
        },
      ],
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

    {
      path: "canjes",
      element: <IndexCanjes />,
      errorElement: <ErrorPage />,
      loader: canjesRootLoader,
      action: canjesRootAction,

      children: [
        /* {
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
        }, */
        {
          path: "new",
          element: <ABMCanje />,
          /* loader: canjeLoader, */
          action: canjeAction,
          errorElement: <ErrorPage />,
        },
        /* {
          path: ":idCliente/destroy",
          action: destroyAction,
          errorElement: <ErrorPage />,
        }, */
      ],
    },

    {
      path: "sorteos",
      element: <IndexSorteos />,
      errorElement: <ErrorPage />,
      loader: sorteosRootLoader,
      action: sorteosRootAction,

      children: [
        /* {
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
        }, */
        {
          path: "new",
          element: <ABMSorteo />,
          /* loader: canjeLoader, */
          action: sorteoAction,
          errorElement: <ErrorPage />,
        },
        /* {
          path: ":idCliente/destroy",
          action: destroyAction,
          errorElement: <ErrorPage />,
        }, */
      ],
    },
  ]);
  return <RouterProvider router={router} />;
}
