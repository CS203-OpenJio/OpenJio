import { Link } from "react-router-dom";

export default function TicketFooter() {
  return (
    <div>
      <Link to="/purchased">
        <button>Purchase</button>
      </Link>
    </div>
  );
}
