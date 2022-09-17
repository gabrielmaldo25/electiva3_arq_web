import { Form, useLoaderData, redirect, useNavigate } from "react-router-dom";
import { getContact, updateContact } from "../contacts";

export function loader({ params }) {
  return getContact(params.contactId);
}
export async function action({ request, params }) {
  const formData = await request.formData();
  const updates = Object.fromEntries(formData);
  await updateContact(params.contactId, updates);
  return redirect(`/contacts/${params.contactId}`);
}
export default function Edit() {
  const contact = useLoaderData();
  const navigate = useNavigate();

  return (
    <div className="flex flex-1 bg-zinc-400 flex-col gap-4 p-4 justify-center">
      <h1 className="text-lg text-white font-semibold ">
        {contact.first ? "EDITAR" : "AGREGAR"}{" "}
      </h1>
      <Form method="post" className="flex  flex-col gap-4 ">
        <div className="flex flex-col xl:flex-row gap-4 justify-center">
          <input
            placeholder="Nombre"
            aria-label="First name"
            type="text"
            name="first"
            defaultValue={contact.first}
            className="flex-1"
          />
          <input
            placeholder="Apellido"
            aria-label="Last name"
            type="text"
            name="last"
            defaultValue={contact.last}
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
            <input type="text" name="twitter" placeholder="Nro" />
          </div>
        </div>
        <div className="flex flex-col xl:flex-row gap-4 justify-center">
          <div className="flex flex-col flex-1">
            <span>Email</span>
            <input
              className="flex flex-1 w-full"
              type="text"
              name="twitter"
              placeholder="mail@example.com"
            />
          </div>
          <div className="flex flex-col flex-1">
            <span>Telefono</span>
            <input
              className="flex flex-1 w-full"
              type="text"
              name="twitter"
              placeholder="Numero de telefono"
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
              name="twitter"
              placeholder="Seleccionar"
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
