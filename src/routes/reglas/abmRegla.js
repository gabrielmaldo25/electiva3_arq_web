import React, { useState, useEffect } from "react";

import { Form, useNavigate, useLoaderData, redirect } from "react-router-dom";
import { createRegla, updateRegla, getRegla, deleteRegla } from "./reglas";

export function loader({ params }) {
  if (params.idRegla) return getRegla(params.idRegla);
  else return [];
}
export async function action({ request, params }) {
  const formData = await request.formData();
  const updates = Object.fromEntries(formData);
  console.log("PARA REGLA: ", updates);
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
  const [tipoRegla, setTipoRegla] = useState(
    regla.destino ? regla.destino : "ninguno"
  );
  function tipoReglaChange(event) {
    console.log(event.target.value);
    setTipoRegla(event.target.value);
  }

  useEffect(() => {
    console.log("REGLA A EDITAR: ", regla);
  }, [regla]);
  return (
    <div className="flex flex-1 bg-zinc-900 space-y-4 p-4  sm:py-6 lg:py-4  xl:py-6 justify-center ">
      <div className=" flex flex-col justify-center">
        <h1 className="text-3xl font-bold text-white">
          {regla?.idRegla ? "Editar" : "Nueva"} Regla
        </h1>

        <Form method="post">
          <div className="bg-sand-300 space-y-4 w-full pt-4 flex flex-col">
            <fieldset className="text-white">
              <legend>Tipo de Regla</legend>
              <div className="flex flex-row gap-4" onChange={tipoReglaChange}>
                <label>
                  <input
                    type="radio"
                    name="destino"
                    value="ninguno"
                    checked={tipoRegla === "ninguno"}
                  />{" "}
                  Regla Normal
                </label>
                <label>
                  <input
                    type="radio"
                    name="destino"
                    value="sorteo"
                    checked={tipoRegla === "sorteo"}
                  />{" "}
                  Regla de Sorteo
                </label>
                <label>
                  <input
                    type="radio"
                    name="destino"
                    value="alerta"
                    checked={tipoRegla === "alerta"}
                  />{" "}
                  Regla de Alerta
                </label>
              </div>
            </fieldset>

            {/*  <label>
              <input
                type="checkbox"
                placeholder="Validez de puntos (días)"
                name="isSorteo"
                defaultValue={regla.esSorteo}
                required
              />{" "}
              Regla de sorteo
            </label>
            <label>
              <input
                type="checkbox"
                placeholder="Validez de puntos (días)"
                name="isSorteo"
                defaultValue={regla.esSorteo}
                required
              />{" "}
              Regla de alerta
            </label> */}
            {tipoRegla !== "alerta" && (
              <div className="flex flex-col gap-4">
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
              </div>
            )}
            {tipoRegla == "ninguno" && (
              <input
                type="text"
                placeholder={"Monto (1 punto cada monto)"}
                name="monto"
                defaultValue={regla.monto}
                required
              />
            )}
            {tipoRegla !== "sorteo" && (
              <input
                type="text"
                placeholder={
                  tipoRegla == "ninguno"
                    ? "Validez de puntos (días)"
                    : "Cuántos días antes del vencimiento avisar"
                }
                name="validezDias"
                defaultValue={regla.validezDias}
                required
              />
            )}
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
