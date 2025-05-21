import React, { useEffect, useState } from "react";
import axios from "axios";
import "./FileTable.css";

const FileTable = ({ onFileSelect }) => {
  const [files, setFiles] = useState([]);

  useEffect(() => {
    const fetchFiles = async () => {
      try {
        const res = await axios.get(`${import.meta.env.VITE_API_BASE_URL}api`);
        setFiles(res.data);
      } catch (err) {
        console.error("Failed to load uploaded files", err);
      }
    };
    fetchFiles();
  }, []);

  const openApiDocs = (filename) => {
    const swaggerUrl = `${
      import.meta.env.VITE_API_BASE_URL
    }swagger-ui/index.html#/dynamic-data-controller/getAllRowsUsingGET_1`;
    window.open(swaggerUrl, "_blank");
  };

  return (
    <div className="file-table">
      <h3>Uploaded Files</h3>
      <ul>
        {files.map((file) => (
          <li key={file}>
            <div className="file-entry">
              <span>{file}</span>
              <div className="file-buttons">
                <button
                  className="pill-button"
                  onClick={() => onFileSelect(file)}
                >
                  View Data
                </button>
                <button
                  className="pill-button secondary"
                  onClick={() => openApiDocs(file)}
                >
                  API Docs
                </button>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FileTable;
