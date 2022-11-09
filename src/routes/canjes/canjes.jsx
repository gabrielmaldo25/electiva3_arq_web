export async function getCanjes(query) {
  let canjes = [];
  try {
    let res = await fetch(`/api/reportes/uso-puntos`);
    canjes = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }

  return canjes;
}

export async function createCanje(payload) {
  let canje = [];
  try {
    let res = await fetch(`/api/puntos/canjear`, {
      method: "POST",
      body: JSON.stringify(payload),
      headers: { "Content-Type": "application/json" },
    });
    canje = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }

  return canje;
}
/* 
export async function getPago(id) {
  let pago;
  try {
    let res = await fetch(`/api/pagos/${id}`);
    pago = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!pago) pago = [];

  return pago;
}

export async function updatePago(id, updates) {
  let pago;
  try {
    let res = await fetch(`/api/pagos/${id}`, {
      method: "PUT",
      body: JSON.stringify(updates),
      headers: { "Content-Type": "application/json" },
    });
    pago = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!pago) throw new Error("No pago found for", id);

  return pago;
}

export async function deletePago(id) {
  try {
    await fetch(`/api/pagos/${id}`, {
      method: "DELETE",
    });
  } catch (error) {
    console.log("ERROR AL ELIMINAR ", error);
  }
  return;
}
 */
