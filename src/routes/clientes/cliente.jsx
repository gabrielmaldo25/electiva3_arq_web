import { Form, useLoaderData } from "react-router-dom";
import { getCliente } from "./clientes";

export async function loader({ params }) {
  const cliente = await getCliente(params.clienteId);
  if (!cliente) {
    throw new Response("", {
      status: 404,
      statusText: "Not Found",
    });
  }
  return cliente;
}

export default function Cliente() {
  const cliente = useLoaderData();

  return (
    <div className="flex flex-1 p-4">
      <div>
        <h1 className="text-lg text-white font-medium">
          {cliente.first || cliente.last ? (
            <>
              {cliente.first} {cliente.last}
            </>
          ) : (
            <i>No Name</i>
          )}{" "}
        </h1>

        {cliente.twitter && (
          <p>
            <a target="_blank" href={`https://twitter.com/${cliente.twitter}`}>
              {cliente.twitter}
            </a>
          </p>
        )}

        {cliente.notes && <p>{cliente.notes}</p>}

        <div>
          <Form action="edit">
            <button type="submit">Editar</button>
          </Form>
          <Form
            method="post"
            action="destroy"
            onSubmit={(event) => {
              if (!confirm("Please confirm you want to delete this record.")) {
                event.preventDefault();
              }
            }}
          >
            <button type="submit">Eliminar</button>
          </Form>
        </div>
      </div>
    </div>
  );
}
