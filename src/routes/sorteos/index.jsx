import { useState, useEffect } from "react";
import Layout from "../../components/layout";
/*Parte de la tabla */
import * as React from "react";
import { useTheme } from "@mui/material/styles";
import FirstPageIcon from "@mui/icons-material/FirstPage";
import KeyboardArrowLeft from "@mui/icons-material/KeyboardArrowLeft";
import KeyboardArrowRight from "@mui/icons-material/KeyboardArrowRight";
import LastPageIcon from "@mui/icons-material/LastPage";
import {
  Box,
  Table,
  TableRow,
  TableBody,
  TableCell,
  TableContainer,
  TableFooter,
  TablePagination,
  Paper,
  IconButton,
  TableHead,
} from "@mui/material";
import {
  Form,
  redirect,
  Outlet,
  useOutlet,
  useLoaderData,
  useNavigate,
} from "react-router-dom";
import { getCanjes, getParticipantes } from "./sorteos";

function TablePaginationActions(props) {
  const theme = useTheme();
  const { count, page, rowsPerPage, onPageChange } = props;

  const handleFirstPageButtonClick = (event) => {
    onPageChange(event, 0);
  };

  const handleBackButtonClick = (event) => {
    onPageChange(event, page - 1);
  };

  const handleNextButtonClick = (event) => {
    onPageChange(event, page + 1);
  };

  const handleLastPageButtonClick = (event) => {
    onPageChange(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
  };

  return (
    <Box sx={{ flexShrink: 0, ml: 2.5 }}>
      <IconButton
        onClick={handleFirstPageButtonClick}
        disabled={page === 0}
        aria-label="first page"
      >
        {theme.direction === "rtl" ? <LastPageIcon /> : <FirstPageIcon />}
      </IconButton>
      <IconButton
        onClick={handleBackButtonClick}
        disabled={page === 0}
        aria-label="previous page"
      >
        {theme.direction === "rtl" ? (
          <KeyboardArrowRight />
        ) : (
          <KeyboardArrowLeft />
        )}
      </IconButton>
      <IconButton
        onClick={handleNextButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="next page"
      >
        {theme.direction === "rtl" ? (
          <KeyboardArrowLeft />
        ) : (
          <KeyboardArrowRight />
        )}
      </IconButton>
      <IconButton
        onClick={handleLastPageButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="last page"
      >
        {theme.direction === "rtl" ? <FirstPageIcon /> : <LastPageIcon />}
      </IconButton>
    </Box>
  );
}

export async function action() {
  return redirect(`/sorteos/new`);
}

export async function loader({ request }) {
  const url = new URL(request.url);
  const q = url.searchParams.get("q");
  const sorteos = await getParticipantes(q);
  return { sorteos, q };
}

export default function Index() {
  const [open, setOpen] = useState(false);
  const outlet = useOutlet();
  const { sorteos, q } = useLoaderData();
  useEffect(() => {
    console.log("CANJES: ", sorteos);
  }, [sorteos]);
  const navigate = useNavigate();

  /* Parte de la tabla */
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - sorteos.length) : 0;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  /* *** */

  return (
    <Layout>
      <div className="flex-1">
        {!outlet && (
          <div>
            <section>
              <header className="bg-zinc-900 space-y-4 p-4  sm:py-6 lg:py-4  xl:py-6">
                <div className="flex items-center justify-between">
                  <h1 className="text-3xl font-bold text-white">
                    Participantes Habilitados
                  </h1>
                  <Form method="post">
                    <button
                      className="hover:bg-green-600 group flex items-center rounded-md bg-green-800 text-white text-sm font-medium pl-2 pr-3 py-2 shadow-sm"
                      method="post"
                    >
                      <svg
                        width="20"
                        height="20"
                        fill="currentColor"
                        className="mr-2"
                        aria-hidden="true"
                      >
                        <path d="M10 5a1 1 0 0 1 1 1v3h3a1 1 0 1 1 0 2h-3v3a1 1 0 1 1-2 0v-3H6a1 1 0 1 1 0-2h3V6a1 1 0 0 1 1-1Z" />
                      </svg>
                      Realizar Sorteo
                    </button>
                  </Form>
                </div>
              </header>

              <TableContainer component={Paper}>
                <Table
                  aria-label="custom pagination table"
                  className="p-4 sm:px-8 sm:py-6 lg:p-4 xl:px-8 xl:py-6"
                >
                  <TableHead className="bg-green-800">
                    <TableRow>
                      <TableCell className="text-white">Cliente</TableCell>
                      <TableCell className="text-white">Puntos</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {(rowsPerPage > 0
                      ? sorteos.slice(
                          page * rowsPerPage,
                          page * rowsPerPage + rowsPerPage
                        )
                      : sorteos
                    ).map((row) => (
                      <TableRow
                        key={row.idCliente}
                        className=" hover:bg-zinc-300 ring-1 ring-gray-900 "
                      >
                        <TableCell className="text-sand-300 hover:text-gray-900">
                          {row.nombre} {row.apellido}
                        </TableCell>
                        <TableCell className="text-sand-300 hover:text-gray-900">
                          {row.puntos}
                        </TableCell>
                      </TableRow>
                    ))}
                    {emptyRows > 0 && (
                      <TableRow style={{ height: 53 * emptyRows }}>
                        <TableCell colSpan={6} />
                      </TableRow>
                    )}
                  </TableBody>
                  <TableFooter className="bg-green-800">
                    <TableRow>
                      <TablePagination
                        rowsPerPageOptions={[
                          5,
                          10,
                          25,
                          { label: "All", value: -1 },
                        ]}
                        colSpan={6}
                        count={sorteos.length}
                        rowsPerPage={rowsPerPage}
                        page={page}
                        SelectProps={{
                          inputProps: {
                            "aria-label": "rows per page",
                          },
                          native: true,
                        }}
                        onPageChange={handleChangePage}
                        onRowsPerPageChange={handleChangeRowsPerPage}
                        ActionsComponent={TablePaginationActions}
                      />
                    </TableRow>
                  </TableFooter>
                </Table>
              </TableContainer>
            </section>
          </div>
        )}
        <Outlet />
      </div>
    </Layout>
  );
}
