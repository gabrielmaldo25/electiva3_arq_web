import {
  Outlet,
  Link,
  useLoaderData,
  Form,
  redirect,
  NavLink,
  useNavigation,
  useSubmit,
} from "react-router-dom";
import { getClientes, createCliente } from "./clientes";
import { useEffect } from "react";
import Layout from "../../components/layout";

export async function loader({ request }) {
  const url = new URL(request.url);
  const q = url.searchParams.get("q");
  const clientes = await getClientes(q);
  return { clientes, q };
}

export async function action() {
  return redirect(`/clientes/new`);
}

export default function Root() {
  const { clientes, q } = useLoaderData();
  const navigation = useNavigation();
  const submit = useSubmit();

  const searching =
    navigation.location &&
    new URLSearchParams(navigation.location.search).has("q");

  useEffect(() => {
    document.getElementById("q").value = q;
  }, [q]);

  return (
    <Layout>
      <div className="flex flex-1 flex-row">
        <div id="sidebar" className="flex flex-col w-3/5 bg-slate-500">
          <div>
            <Form id="search-form" role="search">
              <input
                id="q"
                aria-label="Search clientes"
                placeholder="Buscar"
                type="search"
                name="q"
                defaultValue={q}
                onChange={(event) => {
                  submit(event.currentTarget.form);
                }}
                className={searching ? "loading" : ""}
              />
              <div id="search-spinner" aria-hidden hidden={true} />
              <div className="sr-only" aria-live="polite"></div>
            </Form>
            <Form method="post">
              <button type="submit">Nuevo</button>
            </Form>
          </div>
          <nav>
            {clientes.length ? (
              <ul>
                {clientes.map((cliente) => (
                  <li key={cliente.idCliente}>
                    <NavLink
                      to={`${cliente.idCliente}`}
                      className={({ isActive, isPending }) =>
                        isActive ? "active" : isPending ? "pending" : ""
                      }
                    >
                      <Link to={`${cliente.idCliente}`}>
                        {cliente.nombre || cliente.apellido ? (
                          <>
                            {cliente.nombre} {cliente.apellido}
                          </>
                        ) : (
                          <i>No Name</i>
                        )}{" "}
                      </Link>
                    </NavLink>
                  </li>
                ))}
              </ul>
            ) : (
              <p>
                <i>No clientes</i>
              </p>
            )}
          </nav>
        </div>
        <div
          //id="detail"
          // className={navigation.state === "loading" ? "loading" : ""}
          className="w-2/5 flex"
        >
          <Outlet />
        </div>
      </div>
    </Layout>
  );
}
