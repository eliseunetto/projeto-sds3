import { Link } from "react-router-dom";

const NavBar = () => {
  return (
    <div className="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-light border-bottom shadow-sm">
      <div className="container">
        <nav className="my-2 my-md-0 mr-md-3">
          <Link style={{ textDecoration: "none" }} to="/">
            <h2>
              eliseu<span className="logo">netto</span>
            </h2>
          </Link>
        </nav>
      </div>
    </div>
  );
};

export default NavBar;
