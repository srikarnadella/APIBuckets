import React, { useEffect, useState } from "react";
import axios from "axios";
import "./DataTable.css";

const DataTable = ({ filename }) => {
  const [data, setData] = useState([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(50);

  useEffect(() => {
    const fetchData = async () => {
      const res = await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}api/${filename}`
      );
      setData(res.data);
      setPage(0);
    };
    fetchData();
  }, [filename]);

  const start = page * rowsPerPage;
  const pageData = data.slice(start, start + rowsPerPage);
  const totalPages = Math.ceil(data.length / rowsPerPage);

  return (
    <div className="table-container">
      <h2>{filename}</h2>
      <table className="csv-table">
        <thead>
          <tr>
            {pageData[0] &&
              Object.keys(pageData[0]).map((key) => <th key={key}>{key}</th>)}
          </tr>
        </thead>
        <tbody>
          {pageData.map((row, i) => (
            <tr key={i}>
              {Object.values(row).map((val, idx) => (
                <td key={idx}>{val}</td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
      <div className="pagination">
        <button
          onClick={() => setPage((p) => Math.max(p - 1, 0))}
          disabled={page === 0}
        >
          Previous
        </button>

        <div style={{ display: "flex", alignItems: "center", gap: "12px" }}>
          <span style={{ color: "black" }}>
            Page {page + 1} of {totalPages}
          </span>
          <select
            value={rowsPerPage}
            onChange={(e) => {
              setRowsPerPage(Number(e.target.value));
              setPage(0);
            }}
          >
            {[10, 25, 50, 100, 200].map((num) => (
              <option key={num} value={num}>
                {num} per page
              </option>
            ))}
          </select>
        </div>

        <button
          onClick={() => setPage((p) => Math.min(p + 1, totalPages - 1))}
          disabled={page >= totalPages - 1}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default DataTable;
