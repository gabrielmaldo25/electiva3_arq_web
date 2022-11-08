import React, { useState } from "react";

import { Form, useNavigate, useLoaderData, redirect } from "react-router-dom";
import { createRegla, updateRegla, getRegla, deleteRegla } from "./reglas";

export function loader({ params }) {
  if (params.idRegla) return getRegla(params.idRegla);
  else return [];
}
export async function action({ request, params }) {
  const formData = await request.formData();
  const updates = Object.fromEntries(formData);
  let res;
  if (params.idRegla) {
    await updateRegla(params.idRegla, updates);
  } else {
    res = await createRegla(updates);
  }
  return redirect(`/reglas`);
}

export async function destroyRegla({ params }) {
  await deleteRegla(params.idRegla);
  return redirect("/reglas");
}

export default function ABMRegla({ open, setOpen }) {
  const regla = useLoaderData();
  const navigate = useNavigate();

  return (
    <div className="flex flex-1 bg-zinc-900 space-y-4 p-4  sm:py-6 lg:py-4  xl:py-6 justify-center ">
      <div className=" flex flex-col justify-center">
        <h1 className="text-3xl font-bold text-white">
          {regla?.idRegla ? "Editar" : "Nueva"} Regla
        </h1>

        <Form method="post">
          <div className="bg-sand-300 space-y-4 w-full pt-4 flex flex-col">
            <input
              type="text"
              placeholder="Limite Inferior"
              name="limiteInferior"
              defaultValue={regla.limiteInferior}
              required
            />
            <input
              type="text"
              placeholder="Limite Superior"
              name="limiteSuperior"
              defaultValue={regla.limiteSuperior}
              required
            />
            <input
              type="text"
              placeholder="Puntos"
              name="monto"
              defaultValue={regla.monto}
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
        {regla?.idRegla && (
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
            <button type="submit">Eliminar Regla</button>
          </Form>
        )}
      </div>
    </div>
  );
}
