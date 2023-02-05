import { Routes, Route, Navigate } from "react-router-dom";
import { Dashboard } from "./layouts/dashboard";
import { Auth } from "./layouts/auth";


function App() {

  return (
    <Routes>
      <Route path="/dashboard/*" element={<Dashboard />} />
      <Route path="/auth/*" element={<Auth />} />
      <Route path="*" element={<Navigate to="/dashboard/home" replace />} />
    </Routes>
    

  );
}

export default App;
