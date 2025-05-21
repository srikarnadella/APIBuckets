// src/components/MetadataCard.jsx
import React, { useState, useEffect } from "react";
import axios from "axios";
import "./MetadataCard.css";

const MetadataCard = ({ filename }) => {
  const [metadata, setMetadata] = useState(null);
  const [collapsed, setCollapsed] = useState(true);

  useEffect(() => {
    const fetchMetadata = async () => {
      try {
        const res = axios.get(
          `${import.meta.env.VITE_API_BASE_URL}api/${filename}`
        );
        const firstRow = res.data[0] || {};
        setMetadata({
          rowCount: res.data.length,
          columnCount: Object.keys(firstRow).length,
          columns: Object.keys(firstRow),
        });
      } catch (err) {
        console.error("Failed to fetch metadata", err);
      }
    };

    if (filename) fetchMetadata();
  }, [filename]);

  if (!metadata) return null;

  return (
    <div className="metadata-card">
      <div className="metadata-header" onClick={() => setCollapsed(!collapsed)}>
        <h3>Metadata</h3>
      </div>
      <div className="metadata-content">
        <p>
          <strong>Rows:</strong> {metadata.rowCount}
        </p>
        <p>
          <strong>Columns:</strong> {metadata.columnCount}
        </p>
        <p>
          <strong>Column Names:</strong>
        </p>
        <ul>
          {metadata.columns.map((col) => (
            <li key={col}>{col}</li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default MetadataCard;
