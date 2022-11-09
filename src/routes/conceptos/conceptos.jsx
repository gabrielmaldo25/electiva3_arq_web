export async function getConceptos(query) {
  let conceptos;
  try {
    let q = `/api/conceptos`;
    if (query) q += `?concepto=${query}`;
    let res = await fetch(q);
    conceptos = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!conceptos) conceptos = [];

  return conceptos;
}

export async function createConcepto(payload) {
  let concepto;
  try {
    let res = await fetch(`/api/conceptos`, {
      method: "POST",
      body: JSON.stringify(payload),
      headers: { "Content-Type": "application/json" },
    });
    concepto = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!concepto) throw new Error("No se pudo crear");

  return concepto;
}

export async function getConcepto(id) {
  let concepto;
  try {
    let res = await fetch(`/api/conceptos/${id}`);
    concepto = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!concepto) concepto = [];

  return concepto;
}

export async function updateConcepto(id, updates) {
  let concepto;
  try {
    let res = await fetch(`/api/conceptos/${id}`, {
      method: "PUT",
      body: JSON.stringify(updates),
      headers: { "Content-Type": "application/json" },
    });
    concepto = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!concepto) throw new Error("No concepto found for", id);

  return concepto;
}

export async function deleteConcepto(id) {
  try {
    await fetch(`/api/conceptos/${id}`, {
      method: "DELETE",
    });
  } catch (error) {
    console.log("ERROR AL ELIMINAR ", error);
  }
  return;
}
