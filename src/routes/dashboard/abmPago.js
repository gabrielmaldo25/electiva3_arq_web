import {
  Autocomplete,
  ListItemText,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import React, { useState, useEffect } from "react";

import { Form, useNavigate, useLoaderData, redirect } from "react-router-dom";
import { createPago, updatePago, getPago, deletePago } from "./pagos";
import { getClientes } from "../clientes/clientes";

export function loader({ params }) {
  if (params.idPago) return getPago(params.idPago);
  else return [];
}
export async function action({ request, params }) {
  console.log("REQUEST: ", request);
  const formData = await request.formData();
  const updates = Object.fromEntries(formData);
  let res;
  if (params.idPago) {
    await updatePago(params.idPago, updates);
  } else {
    console.log("LLEGA: ", updates);
    res = await createPago(updates);
  }
  if (res.error && res.message) alert(res.message);
  else return redirect(`/`);
}

export async function destroyPago({ params }) {
  await deletePago(params.idPago);
  return redirect("/");
}

export default function ABMPago({ open, setOpen }) {
  const pago = useLoaderData();
  const navigate = useNavigate();
  const [clientes, setClientes] = useState([]);
  const [idCliente, setIdCliente] = useState(null);
  useEffect(() => {
    (async () => {
      let res = await getClientes();
      console.log("ABER: ", res);
      res = res.map((cliente) => ({
        id: cliente.idCliente,
        label: cliente.nombre,
      }));
      setClientes(res);
    })();
  }, []);
  return (
    <div className="flex flex-1 bg-zinc-900 space-y-4 p-4  sm:py-6 lg:py-4  xl:py-6 justify-center ">
      <div className=" flex flex-col justify-center">
        <h1 className="text-3xl font-bold text-white">
          {pago?.idPago ? "Editar" : "Nuevo"} Pago
        </h1>

        <Form method="post">
          <div className="bg-sand-300 space-y-4 w-full pt-4 ">
            <input
              type="text"
              placeholder="Monto"
              name="monto"
              defaultValue={pago?.monto}
              required
            />

            <div className="flex flex-col">
              <text className="text-lg text-white pb-4">
                Seleccionar Cliente
              </text>
              <Autocomplete
                id="combo-box-demo"
                options={clientes}
                sx={{ width: 300 }}
                renderInput={(params) => (
                  <TextField {...params} label="Cliente" required />
                )}
                defaultValue={pago?.idCliente}
                className="bg-white"
                name="idCliente"
                onChange={(event, value) => setIdCliente(value.id)}
              />
              <input hidden value={idCliente} name="idCliente" />
            </div>
          </div>
          <div className="space-x-8 p-8 flex flex-row">
            <div className="space-x-4">
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
          </div>
        </Form>
        {pago?.idPago && (
          <Form
            className="flex flex-1 self-center"
            method="post"
            action="destroy"
            onSubmit={(event) => {
              if (
                !confirm(
                  "Por favor confima que quieres eliminar este concepto."
                )
              ) {
                event.preventDefault();
              }
            }}
          >
            <button type="submit">Eliminar Pago</button>
          </Form>
        )}
      </div>
    </div>
  );
}
