import React, { useEffect, useState } from "react";
import axios from "axios";
import "./FileTable.css";

const FileTable = ({ onFileSelect }) => {
  const [files, setFiles] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api")
      .then((response) => {
        setFiles(response.data);
      })
      .catch((error) => {
        console.error("Error fetching uploaded files:", error);
      });
  }, []);

  return (
    <div className="file-table-container">
      <h3>Uploaded Files</h3>
      <ul className="file-list">
        {files.map((file) => (
          <li key={file} className="file-entry">
            <span className="file-name">{file}</span>
            <button
              className="view-data-pill"
              onClick={() => onFileSelect(file)}
            >
              View Data
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FileTable;
