import React, { useState } from "react";
import UploadForm from "./components/UploadForm";
import FileTable from "./components/FileTable";
import DataTable from "./components/DataTable";
import MetadataCard from "./components/MetadataCard";
import ChartCard from "./components/ChartCard";
import "./App.css";

const App = () => {
  const [selectedFile, setSelectedFile] = useState(null);

  return (
    <div className="app-container" style={{ backgroundColor: "#1c1c1c" }}>
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

      <div className="main-content" style={{ display: "flex" }}>
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
          {selectedFile && <MetadataCard filename={selectedFile} />}
        </div>

        <div
          className="right-pane"
          style={{
            backgroundColor: "#1c1c1c",
            flex: 1,
            padding: "20px",
            overflow: "auto",
          }}
        >
          {selectedFile && (
            <>
              <DataTable filename={selectedFile} />
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default App;
