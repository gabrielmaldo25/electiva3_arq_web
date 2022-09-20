import { redirect } from "react-router-dom";
import { deleteCliente } from "./clientes";

export async function action({ params }) {
  await deleteCliente(params.clienteId);
  return redirect("/clientes");
}
