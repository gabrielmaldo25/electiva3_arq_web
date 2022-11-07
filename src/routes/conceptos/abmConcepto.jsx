import React from "react";
import { useEffect } from "react";

import { Form, useNavigate, useLoaderData, redirect } from "react-router-dom";
import { createConcepto, updateConcepto, getConcepto } from "./conceptos";

export function loader({ params }) {
  if (params.idConcepto) return getConcepto(params.idConcepto);
  else return [];
}
export async function action({ request, params }) {
  const formData = await request.formData();
  const updates = Object.fromEntries(formData);
  let res;
  if (params.idConcepto) {
    await updateConcepto(params.idConcepto, updates);
  } else {
    res = await createConcepto(updates);
  }
  return redirect(`/conceptos`);
}

export default function ABMConcepto({ open, setOpen }) {
  const concepto = useLoaderData();
  const navigate = useNavigate();

  return (
    <div className="flex flex-1 bg-zinc-900 space-y-4 p-4  sm:py-6 lg:py-4  xl:py-6 justify-center ">
      <div className=" flex flex-col justify-center">
        <h1 className="text-3xl font-bold text-white">Concepto</h1>
        <Form method="post">
          <div className="bg-sand-300 space-y-4 w-full pt-4 flex flex-col">
            <input
              type="text"
              placeholder="Descripcion"
              name="descripcion"
              defaultValue={concepto.descripcion}
              required
            />
            <input
              type="text"
              placeholder="Puntos"
              name="puntos"
              defaultValue={concepto.puntos}
              required
            />
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
        {concepto?.idConcepto && (
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
            <button type="submit">Eliminar concepto</button>
          </Form>
        )}
      </div>
    </div>
  );
}
