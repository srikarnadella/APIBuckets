import React, { useState } from "react";
import UploadForm from "./components/UploadForm";
import FileTable from "./components/FileTable";
import DataTable from "./components/DataTable";
import "./App.css";

const App = () => {
  const [selectedFile, setSelectedFile] = useState(null);

  return (
    <div className="app-container" style={{ backgroundColor: "#1c1c1c" }}>
      {/* Top Header */}
      <header
        style={{
          backgroundColor: "#1c1c1c",
          color: "#ffffff",
          padding: "15px 30px",
          fontSize: "1.5rem",
          fontWeight: "600",
          borderBottom: "1px solid #333",
          textAlign: "center",
        }}
      >
        CSV to API Dashboard
      </header>
      <div
        className="left-pane"
        style={{
          backgroundColor: "#1c1c1c",
          color: "#f5f5f5",
          padding: "20px",
          borderRadius: "12px",
          minWidth: "280px",
        }}
      >
        <UploadForm onFileSelect={setSelectedFile} />
        <FileTable onFileSelect={setSelectedFile} />
      </div>
      <div className="right-pane" style={{ backgroundColor: "#1c1c1c" }}>
        {selectedFile && <DataTable filename={selectedFile} />}
      </div>
    </div>
  );
};

export default App;
