import React, { useEffect, useState } from "react";
import axios from "axios";
import "./DataTable.css";

const DataTable = ({ filename }) => {
  const [data, setData] = useState([]);
  const [page, setPage] = useState(0);
  const rowsPerPage = 50;

  useEffect(() => {
    const fetchData = async () => {
      const res = await axios.get(`http://localhost:8080/api/${filename}`);
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
        <span>
          Page {page + 1} of {totalPages}
        </span>
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
