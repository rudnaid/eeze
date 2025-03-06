import {BarChart, CartesianGrid, Bar, XAxis, YAxis, Tooltip, Legend} from "recharts";

const BarChartComponent = ({chartData}) => {
  return (
    <>
      <BarChart data={chartData}>
        <CartesianGrid strokeDasharray="3 3"/>
        <XAxis dataKey="date"/>
        <YAxis/>
        <Bar dataKey="totalIncome" fill="#3498db" name="Total Income"/>
        <Bar dataKey="totalExpense" fill="#e74c3c" name="Total Expense"/>
        <Tooltip/>
        <Legend/>
      </BarChart>
    </>
  );
};

export default BarChartComponent;
