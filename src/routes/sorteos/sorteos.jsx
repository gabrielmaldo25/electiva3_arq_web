export async function getParticipantes(query) {
  let participantes = [];
  try {
    let q = `/api/puntos/participantes`;
    let res = await fetch(q);
    participantes = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  return participantes;
}

export async function getGanador(query) {
  let ganador = [];
  try {
    let q = `/api/puntos/sortear`;
    let res = await fetch(q);
    ganador = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  return ganador;
}

export async function getCanjes(query) {
  let canjes = [];
  try {
    let q = `/api/reportes/uso-puntos`;
    if (query) q += `?cliente=${query}`;

    let res = await fetch(q);
    canjes = await res.json();
    //ordenar por id descendente
    canjes = canjes.sort(function (a, b) {
      if (a.idPuntosCab < b.idPuntosCab) {
        return 1;
      }
      if (a.idPuntosCab > b.idPuntosCab) {
        return -1;
      }
      // a must be equal to b
      return 0;
    });
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
