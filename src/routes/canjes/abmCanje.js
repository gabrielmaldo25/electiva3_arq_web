import {
  Autocomplete,
  Box,
  ListItemText,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import React, { useState, useEffect } from "react";

import { Form, useNavigate, useLoaderData, redirect } from "react-router-dom";
import { createCanje } from "./canjes";
import { getClientes } from "../clientes/clientes";
import { getConceptos } from "../conceptos/conceptos";

/* export function loader({ params }) {
  if (params.idPago) return getPago(params.idPago);
  else return [];
} */
export async function action({ request, params }) {
  console.log("REQUEST: ", request);
  const formData = await request.formData();
  const updates = Object.fromEntries(formData);
  let res;
  if (params.idPago) {
    await updatePago(params.idPago, updates);
  } else {
    res = await createCanje(updates);
  }
  if (res.error && res.message) alert(res.message);
  else return redirect(`/canjes`);
}

/* export async function destroyPago({ params }) {
  await deletePago(params.idPago);
  return redirect("/");
} */

export default function ABMCanje({ open, setOpen }) {
  //const pago = useLoaderData();
  const navigate = useNavigate();
  const [clientes, setClientes] = useState([]);
  const [conceptos, setConceptos] = useState([]);
  const [idCliente, setIdCliente] = useState(null);
  const [idConcepto, setIdConcepto] = useState(null);
  useEffect(() => {
    (async () => {
      let res = await getConceptos();
      res = res.map((concepto) => ({
        id: concepto.idConcepto,
        label: concepto.descripcion,
        puntos: concepto.puntos,
      }));
      setConceptos(res);
    })();
  }, []);
  useEffect(() => {
    (async () => {
      let res = await getClientes();
      console.log("ABER: ", res);
      res = res.map((cliente) => ({
        id: cliente.idCliente,
        label: cliente.nombre,
        puntos: cliente.puntos,
      }));
      setClientes(res);
    })();
  }, []);
  return (
    <div className="flex flex-1 bg-zinc-900 space-y-4 p-4  sm:py-6 lg:py-4  xl:py-6 justify-center ">
      <div className=" flex flex-col justify-center">
        <h1 className="text-3xl font-bold text-white">Canjear</h1>

        <Form method="post">
          <div className="bg-sand-300 space-y-4 w-full pt-4 ">
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
                //defaultValue={pago?.idCliente}
                className="bg-white"
                name="idCliente"
                onChange={(event, value) => setIdCliente(value.id)}
                renderOption={(props, option, { selected }) => (
                  <li {...props}>
                    <Box>
                      {option.label}
                      <br />
                      <p className="text-sm text-gray-500">
                        {option.puntos} puntos disponibles
                      </p>
                    </Box>
                  </li>
                )}
              />
              <input hidden value={idCliente} name="idCliente" />
            </div>
          </div>
          <div className="flex flex-col">
            <text className="text-lg text-white pb-4">
              Seleccionar Concepto
            </text>
            <Autocomplete
              id="combo-box-demo"
              options={conceptos}
              sx={{ width: 300 }}
              renderInput={(params) => (
                <TextField {...params} label="Concepto" required />
              )}
              //defaultValue={pago?.idCliente}
              className="bg-white"
              name="idCliente"
              onChange={(event, value) => setIdConcepto(value.id)}
              renderOption={(props, option, { selected }) => (
                <li {...props}>
                  <Box>
                    {option.label}
                    <br />
                    <p className="text-sm text-gray-500">
                      {option.puntos} puntos
                    </p>
                  </Box>
                </li>
              )}
            />
            <input hidden value={idConcepto} name="idConcepto" />
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
        {/* {pago?.idPago && (
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
        )} */}
      </div>
    </div>
  );
}
