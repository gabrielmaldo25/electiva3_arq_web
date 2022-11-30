export async function getReglas(query) {
  let reglas;
  try {
    let res = await fetch(`/api/reglas`);
    reglas = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!reglas) reglas = [];

  return reglas;
}

export async function createRegla(payload) {
  let regla;
  console.log("Va enviar: ", payload);
  try {
    let res = await fetch(`/api/reglas`, {
      method: "POST",
      body: JSON.stringify(payload),
      headers: { "Content-Type": "application/json" },
    });
    regla = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!regla) throw new Error("No se pudo crear");

  return regla;
}

export async function getRegla(id) {
  let regla;
  try {
    let res = await fetch(`/api/reglas/${id}`);
    regla = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!regla) regla = [];

  return regla;
}

export async function updateRegla(id, updates) {
  let regla;
  try {
    let res = await fetch(`/api/reglas/${id}`, {
      method: "PUT",
      body: JSON.stringify(updates),
      headers: { "Content-Type": "application/json" },
    });
    regla = await res.json();
  } catch (error) {
    console.log("ERROR; ", error);
  }
  if (!regla) throw new Error("No regla found for", id);

  return regla;
}

export async function deleteRegla(id) {
  try {
    await fetch(`/api/reglas/${id}`, {
      method: "DELETE",
    });
  } catch (error) {
    console.log("ERROR AL ELIMINAR ", error);
  }
  return;
}
