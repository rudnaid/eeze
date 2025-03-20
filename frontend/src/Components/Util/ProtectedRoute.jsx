import AuthFailureModal from "./AuthFailureModal.jsx";
import {useAuth} from "../../Context/AuthContext.jsx";

const ProtectedRoute = ({ children }) => {
  const { user } = useAuth();

  return (
    <>
      {user ? children : <AuthFailureModal />}
    </>
  );
};

export default ProtectedRoute;
