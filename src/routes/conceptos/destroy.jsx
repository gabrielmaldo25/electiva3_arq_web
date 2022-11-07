import { redirect } from "react-router-dom";
import { deleteConcepto } from "./conceptos";

export async function action({ params }) {
  await deleteConcepto(params.idConcepto);
  return redirect("/conceptos");
}
