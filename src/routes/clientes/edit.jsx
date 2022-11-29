import { Form, useLoaderData, redirect, useNavigate } from "react-router-dom";
import { getCliente, updateCliente, createCliente } from "./clientes";

export function loader({ params }) {
  if (params.idCliente) return getCliente(params.idCliente);
  else return [];
}
export async function action({ request, params }) {
  const formData = await request.formData();
  const updates = Object.fromEntries(formData);
  console.log("ENVIAR CLIENTE: ", updates);
  let res;
  if (params.idCliente) {
    await updateCliente(params.idCliente, updates);
  } else {
    res = await createCliente(updates);
    return redirect(`/clientes/${res.idCliente}`);
  }
  return redirect(`/clientes/${params.idCliente}`);
}
export default function Edit() {
  const cliente = useLoaderData();
  const navigate = useNavigate();

  return (
    <div className="flex flex-1 bg-zinc-400 flex-col gap-4 p-4 justify-center">
      <h1 className="text-lg text-white font-semibold ">
        {cliente?.nombre ? "EDITAR" : "AGREGAR"}{" "}
      </h1>
      <Form method="post" className="flex  flex-col gap-4 ">
        <div className="flex flex-col xl:flex-row gap-4 justify-center">
          <input
            placeholder="Nombre"
            aria-label="nombre"
            type="text"
            name="nombre"
            defaultValue={cliente?.nombre}
            className="flex-1"
          />
          <input
            placeholder="Apellido"
            aria-label="apellido"
            type="text"
            name="apellido"
            defaultValue={cliente?.apellido}
            className="flex-1"
          />
        </div>
        <div className="flex flex-col xl:flex-row gap-4 justify-center">
          <div className="flex flex-col flex-1">
            <span>Tipo de documento</span>
            <select id="countries" class=" text-sm rounded-lg p-2.5 ">
              <option selected>Seleccionar</option>
              <option value="ci">CI</option>
              <option value="ruc">RUC</option>
            </select>
          </div>
          <div className="flex flex-col flex-1">
            <span>Numero de documento</span>
            <input
              type="text"
              placeholder="Nro"
              name="nroDocumento"
              defaultValue={cliente?.nroDocumento}
            />
          </div>
        </div>
        <div className="flex flex-col xl:flex-row gap-4 justify-center">
          <div className="flex flex-col flex-1">
            <span>Email</span>
            <input
              className="flex flex-1 w-full"
              type="text"
              name="email"
              placeholder="mail@example.com"
              defaultValue={cliente?.email}
            />
          </div>
          <div className="flex flex-col flex-1">
            <span>Telefono</span>
            <input
              className="flex flex-1 w-full"
              type="text"
              name="telefono"
              placeholder="Numero de telefono"
              defaultValue={cliente?.telefono}
            />
          </div>
        </div>
        <div className="flex flex-col lg:flex-row gap-4 justify-center">
          <div className=" flex-1">
            <span>Fecha de Nacimiento</span>
          </div>
          <div className="flex-1">
            <input
              className="w-full"
              type="date"
              name="fechaNac"
              placeholder="Seleccionar"
              defaultValue={cliente?.fechaNac}
            />
          </div>
        </div>

        <div className="flex gap-4 justify-center">
          <button type="submit">Guardar</button>
          <button
            type="button"
            onClick={() => {
              navigate(-1);
            }}
          >
            Cancelar
          </button>
        </div>
      </Form>
    </div>
  );
}
