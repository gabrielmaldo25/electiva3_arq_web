import Layout from "../src/components/layout";
import { BrowserRouter, Routes, Route, Link,Router } from "react-router-dom";
import Root, {
  loader as rootLoader,
  action as rootAction,
} from "./routes/root";
import IndexClientes from "../src/routes/clientes/index";
import Contacts from "./routes/clientes/contacts";
import ErrorPage from "./error-page";
import Contact, { loader as contactLoader } from "./routes/contact";
import EditContact, { action as editAction } from "./routes/edit";
import { action as destroyAction } from "./routes/destroy";
import Index from "./routes/index";
function App() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route index element={<IndexClientes />} />
          <Route path="/" element={<IndexClientes />} />
          <Route
            path="clientes"
            element={<Contacts />}
            errorElement={<ErrorPage />}
          />

          <Route
            path="contacts"
            element={<Root />}
            errorElement={<ErrorPage />}
            loader={rootLoader}
            action={rootAction}
          >
            <Route index element={<Index />} errorElement={<ErrorPage />} />
            <Route
              path={"contacts/:contactId"}
              element={<Contact />}
              loader={contactLoader}
              errorElement={<ErrorPage />}
            />
            <Route
              path={"contacts/:contactId/edit"}
              element={<EditContact />}
              action={editAction}
              loader={contactLoader}
              errorElement={<ErrorPage />}
            />
            <Route
              path={"contacts/:contactId/destroy"}
              action={destroyAction}
              errorElement={<ErrorPage />}
            />
          </Route>
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}

export default App;
