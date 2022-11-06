import { Form, useLoaderData } from "react-router-dom";
import { getCliente } from "./clientes";

export async function loader({ params }) {
  let cliente;
  if (params.idCliente) {
    cliente = await getCliente(params.idCliente);
    if (!cliente) {
      throw new Response("", {
        status: 404,
        statusText: "Not Found",
      });
    }
  }
  return cliente;
}

export default function Cliente() {
  const cliente = useLoaderData();

  return (
    <div className="flex flex-1 p-4">
      <div>
        <h1 className="text-lg text-white font-medium">
          {cliente.nombre || cliente.apellido ? (
            <>
              {cliente.nombre} {cliente.apellido}
            </>
          ) : (
            <i>No Name</i>
          )}
        </h1>
        {cliente.email && (
          <h1 className="text-md text-gray-600 font-medium">
            Email: {cliente.email}
          </h1>
        )}
        {cliente.nroDocumento && (
          <h1 className="text-md text-gray-600 font-medium">
            N° Documento: {cliente.nroDocumento}
          </h1>
        )}
        {cliente.fechaNac && (
          <h1 className="text-md text-gray-600 font-medium">
            Fecha Nacimiento: {cliente.fechaNac}
          </h1>
        )}
        {cliente.telefono && (
          <h1 className="text-md text-gray-600 font-medium">
            Telefono: {cliente.telefono}
          </h1>
        )}

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
