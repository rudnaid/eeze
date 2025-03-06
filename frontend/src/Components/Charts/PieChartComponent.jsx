import {PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer} from 'recharts';

const PieChartComponent = ({chartData}) => {
  const COLORS = ["#0088FE", "#00C49F", "#FFBB28", "#FF8042", "#FF6384", "#36A2EB", "#FFCE56"];

  return (
    <ResponsiveContainer width="70%" height={300}>
    <PieChart >
      <Pie
        margin={{ top: 20, right: 30, left: 0, bottom: 0 }}
        data={chartData}
        cx="50%"
        cy="50%"
        outerRadius={100}
        dataKey="totalAmount"
        nameKey="categoryName"
      >
        {chartData.map((entry, index) => (
          <Cell key={entry.categoryName} fill={COLORS[index % COLORS.length]}></Cell>
        ))}
      </Pie>
      <Tooltip/>
      <Legend/>
    </PieChart>
    </ResponsiveContainer>
  )
};

export default PieChartComponent;
