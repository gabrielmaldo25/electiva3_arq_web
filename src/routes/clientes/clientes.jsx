import localforage from "localforage";
import { matchSorter } from "match-sorter";
import sortBy from "sort-by";

export async function getClientes(query) {
  let clientes;
  try {
    let q = `/api/clientes`;
    if (query) q += `?cliente=${query}`;
    let res = await fetch(q);
    clientes = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!clientes) clientes = [];

  return clientes;
}

export async function createCliente(payload) {
  let cliente;
  try {
    let res = await fetch(`/api/clientes`, {
      method: "POST",
      body: JSON.stringify(payload),
      headers: { "Content-Type": "application/json" },
    });
    cliente = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!cliente) throw new Error("No se pudo crear");

  return cliente;
}

export async function getCliente(id) {
  let cliente;
  try {
    let res = await fetch(`/api/clientes/${id}`);
    cliente = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!cliente) cliente = [];

  return cliente;
}

export async function updateCliente(id, updates) {
  let cliente;
  try {
    let res = await fetch(`/api/clientes/${id}`, {
      method: "PUT",
      body: JSON.stringify(updates),
      headers: { "Content-Type": "application/json" },
    });
    cliente = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!cliente) throw new Error("No cliente found for", id);

  return cliente;
}

export async function deleteCliente(id) {
  try {
    await fetch(`/api/clientes/${id}`, {
      method: "DELETE",
    });
  } catch (error) {
    console.log("ERROR AL ELIMINAR ", error);
  }
  return;
}

function set(clientes) {
  return localforage.setItem("clientes", clientes);
}

// fake a cache so we don't slow down stuff we've already seen
let fakeCache = {};

async function fakeNetwork(key) {
  if (!key) {
    fakeCache = {};
  }

  if (fakeCache[key]) {
    return;
  }

  fakeCache[key] = true;
  return new Promise((res) => {
    setTimeout(res, Math.random() * 800);
  });
}
