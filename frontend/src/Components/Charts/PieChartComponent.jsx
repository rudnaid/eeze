import {PieChart, Pie, Cell, Tooltip, Legend} from 'recharts';

const PieChartComponent = ({chartData}) => {
  const COLORS = ["#0088FE", "#00C49F", "#FFBB28", "#FF8042", "#FF6384", "#36A2EB", "#FFCE56"];

  return (
    <PieChart width={400} height={400}>
      <Pie
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
  )
};

export default PieChartComponent;
