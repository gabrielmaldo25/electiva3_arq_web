export async function getPagos(query) {
  let pagos = [];
  try {
    let q = `/api/reportes/bolsa-puntos`;
    if (query) q += `?cliente=${query}`;
    let res = await fetch(q);
    pagos = await res.json();
    //ordenar por id descendente
    pagos = pagos.sort(function (a, b) {
      if (a.idBola < b.idBolsa) {
        return 1;
      }
      if (a.idBolsa > b.idBolsa) {
        return -1;
      }
      // a must be equal to b
      return 0;
    });
  } catch (error) {
    console.log("ERROR; ", error);
  }

  return pagos;
}

export async function createPago(payload) {
  let pago = [];
  try {
    let res = await fetch(`/api/puntos/pago`, {
      method: "POST",
      body: JSON.stringify(payload),
      headers: { "Content-Type": "application/json" },
    });
    console.log("RES: ", res);
    /*  pago = await res.json(); */
  } catch (error) {
    console.log("ERROR; ", error);
  }

  return pago;
}

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
