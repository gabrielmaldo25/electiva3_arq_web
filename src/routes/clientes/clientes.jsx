import localforage from "localforage";
import { matchSorter } from "match-sorter";
import sortBy from "sort-by";

export async function getClientes(query) {
  await fakeNetwork(`getClientes:${query}`);
  let clientes = await localforage.getItem("clientes");
  if (!clientes) clientes = [];
  if (query) {
    clientes = matchSorter(clientes, query, { keys: ["first", "last"] });
  }
  return clientes.sort(sortBy("last", "createdAt"));
}

export async function createCliente() {
  await fakeNetwork();
  let id = Math.random().toString(36).substring(2, 9);
  let cliente = { id, createdAt: Date.now() };
  let clientes = await getClientes();
  clientes.unshift(cliente);
  await set(clientes);
  return cliente;
}

export async function getCliente(id) {
  await fakeNetwork(`cliente:${id}`);
  let clientes = await localforage.getItem("clientes");
  let cliente = clientes.find(cliente => cliente.id === id);
  return cliente ?? null;
}

export async function updateCliente(id, updates) {
  await fakeNetwork();
  let clientes = await localforage.getItem("clientes");
  let cliente = clientes.find(cliente => cliente.id === id);
  if (!cliente) throw new Error("No cliente found for", id);
  Object.assign(cliente, updates);
  await set(clientes);
  return cliente;
}

export async function deleteCliente(id) {
  let clientes = await localforage.getItem("clientes");
  let index = clientes.findIndex(cliente => cliente.id === id);
  if (index > -1) {
    clientes.splice(index, 1);
    await set(clientes);
    return true;
  }
  return false;
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
  return new Promise(res => {
    setTimeout(res, Math.random() * 800);
  });
}