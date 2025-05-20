// src/components/ChartCard.jsx
import React from "react";
import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  BarElement,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(BarElement, CategoryScale, LinearScale, Tooltip, Legend);

const ChartCard = ({ data, column }) => {
  if (!data || data.length === 0 || !column) return null;

  const labels = data.map((row, i) => `Row ${i + 1}`);
  const values = data
    .map((row) => parseFloat(row[column]))
    .filter((v) => !isNaN(v));

  const chartData = {
    labels,
    datasets: [
      {
        label: column,
        data: values,
        backgroundColor: "rgba(75, 192, 192, 0.6)",
      },
    ],
  };

  return (
    <div className="chart-card">
      <h3>{column} Distribution</h3>
      <Bar data={chartData} />
    </div>
  );
};

export default ChartCard;
