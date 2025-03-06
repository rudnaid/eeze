import {BarChart, CartesianGrid, Bar, XAxis, YAxis, Tooltip, Legend, ResponsiveContainer} from "recharts";

const BarChartComponent = ({chartData}) => {
  return (
    <>
      <ResponsiveContainer width="70%" height={200}>
        <BarChart
          data={chartData}
          margin={{ top: 20, right: 30, left: 0, bottom: 0 }}
        >
          <CartesianGrid strokeDasharray="3 3"/>
          <XAxis dataKey="month"/>
          <YAxis/>
          <Bar dataKey="totalIncome" fill="#3498db" name="Total Income"/>
          <Bar dataKey="totalExpense" fill="#e74c3c" name="Total Expense"/>
          <Tooltip cursor={false}/>
          <Legend/>
        </BarChart>
      </ResponsiveContainer>
    </>
  );
};

export default BarChartComponent;
