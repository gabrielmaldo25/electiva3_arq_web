import { Form, useLoaderData, redirect, useNavigate } from "react-router-dom";
import { getContact, updateContact } from "../contacts";

export function loader({ params }) {
  return getContact(params.contactId);
}
export async function action({ request, params }) {
  const formData = await request.formData();
  const updates = Object.fromEntries(formData);
  await updateContact(params.contactId, updates);
  return redirect(`/contacts/${params.contactId}`);
}
export default function Edit() {
  const contact = useLoaderData();
  const navigate = useNavigate();

  return (
    <Form
      method="post"
      /* id="contact-form" */ className="flex flex-1 bg-red-500 flex-col gap-4"
    >
      <div className="flex ">
        <span>Name</span>
        <div className="flex gap-4">
          <input
            placeholder="First"
            aria-label="First name"
            type="text"
            name="first"
            defaultValue={contact.first}
          />
          <input
            placeholder="Last"
            aria-label="Last name"
            type="text"
            name="last"
            defaultValue={contact.last}
          />
        </div>
      </div>
      <div className="flex ">
        <span>Twitter</span>
        <input
          type="text"
          name="twitter"
          placeholder="@jack"
          defaultValue={contact.twitter}
          className="flex flex-grow"
        />
      </div>
      <div className="flex ">
        <span>Avatar URL</span>
        <input
          placeholder="https://example.com/avatar.jpg"
          aria-label="Avatar URL"
          type="text"
          name="avatar"
          defaultValue={contact.avatar}
          className="flex flex-grow"
        />
      </div>
      <div className="flex">
        <span>Notes</span>
        <textarea
          name="notes"
          defaultValue={contact.notes}
          rows={6}
          className="flex flex-grow"
        />
      </div>
      <p>
        <button type="submit">Save</button>
        <button
          type="button"
          onClick={() => {
            navigate(-1);
          }}
        >
          Cancel
        </button>
      </p>
    </Form>
  );
}
