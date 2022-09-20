import React, {
  ChangeEvent,
  FormEvent,
  useState,
  useEffect,
  useRef,
} from "react";
import {
  Dialog,
  DialogActions,
  DialogTitle,
  Button,
  DialogContentText,
  Select,
  MenuItem,
  ListItemText,
  SelectChangeEvent,
} from "@mui/material";

function Input({ ...props }) {
  return (
    <input
      className="w-full appearance-none bg-transparent border-b-2 focus:border-green-300 border-black text-black p-2 placeholder-green-800 leading-tight focus:outline-none"
      {...props}
    />
  );
}
const clientes = [
  { id: 1, nombre: "Angel" },
  { id: 2, nombre: "Alexis" },
  { id: 3, nombre: "Simon" },
];
export default function ABMRegla({ open, setOpen }) {
  const [openDelete, setOpenDelete] = useState(false);

  const handleClose = () => {
    setOpen(false);
  };

  const handleCloseDelete = () => {
    setOpenDelete(false);
  };

  /*   const handleChange = ({ target: { name, value } }) =>
      setUsuario({ ...usuario, [name]: value }); */

  /* const handleSelectChange = (event) => {
      const { value } = event.target;
  
      setSelectedRol({ ...selectedRol, id: value });
    }; */

  function afterSaved() {
    setOpen(false);
  }

  const handleSubmit = async (e) => {
    e.preventDefault();

    afterSaved();
  };
  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle className="bg-gray-900 text-white">
          Regla
        </DialogTitle>
        <form onSubmit={handleSubmit}>
          <div className="bg-sand-300 space-y-4 w-full p-8 pt-4 ">
            <Input
              type="text"
              placeholder="Monto"
              name="name"
              //onChange={handleChange}
              //value={usuario.name}
              required
            />
            <Input
              type="text"
              placeholder="Puntos por el monto"
              name="name"
              //onChange={handleChange}
              //value={usuario.name}
              required
            />
            <Input
              type="text"
              placeholder="Validez en días (desde la acreditacion)"
              name="name"
              //onChange={handleChange}
              //value={usuario.name}
              required
            />
          </div>
          <DialogActions className="bg-gray-900">
            <Button
              onClick={handleClose}
              className="normal-case hover:ring-green-800 hover:ring-1 group flex items-center rounded-md text-white text-sm font-medium pl-2 pr-3 py-2 shadow-sm"
            >
              Cancelar
            </Button>
            <Button
              className="normal-case hover:bg-green-600 group flex items-center rounded-md bg-green-800 text-white text-sm font-medium pl-2 pr-3 py-2 shadow-sm"
              type="submit"
            >
              Guardar
              {/* {user ? "Actualizar" : "Guardar"} */}
            </Button>
            {/* {user && (
                <Button
                  onClick={() => setOpenDelete(true)}
                  className="normal-case"
                  color="warning"
                >
                  Eliminar Usuario
                </Button>
              )} */}
          </DialogActions>
        </form>
      </Dialog>
      <Dialog open={openDelete} onClose={handleCloseDelete}>
        <DialogTitle className="bg-gray-900 text-white">
          Eliminar Usuario
        </DialogTitle>
        <div className="bg-gray-900 text-white p-4">
          <text>Estas seguro de que quieres eliminar este usuario?</text>
        </div>
        <DialogActions className="bg-gray-900">
          <Button
            onClick={handleCloseDelete}
            className="normal-case hover:ring-green-800 hover:ring-1 group flex items-center rounded-md text-white text-sm font-medium pl-2 pr-3 py-2 shadow-sm"
          >
            Cancelar
          </Button>
          <Button
            //onClick={handleDelete}
            className="normal-case hover:bg-green-600 group flex items-center rounded-md bg-green-800 text-white text-sm font-medium pl-2 pr-3 py-2 shadow-sm"
          >
            Eliminar
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
