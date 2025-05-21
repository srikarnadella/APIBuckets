import React, { useState } from "react";
import axios from "axios";
import "./UploadForm.css";
const UploadForm = ({ onUpload }) => {
  const [file, setFile] = useState(null);

  const handleUpload = async () => {
    if (!file) return;
    const formData = new FormData();
    formData.append("file", file);
    try {
      await axios.post(`${import.meta.env.VITE_API_BASE_URL}upload`, formData);
      onFileSelect(file.name.replace(".csv", ""));
      setFile(null);
    } catch {
      alert("Error uploading file.");
    }
  };

  return (
    <div className="uploader">
      <input type="file" onChange={(e) => setFile(e.target.files[0])} />
      <button onClick={handleUpload}>Upload</button>
    </div>
  );
};

export default UploadForm;
