import {PieChart, Pie, Cell, Tooltip, Legend} from 'recharts';

const PieChartComponent = ({chartData}) => {
  const COLORS = ["#0088FE", "#00C49F", "#FFBB28", "#FF8042"];

  return (
    <PieChart width={300} height={300}>
      <Pie
        cx="50%"
        cy="50%"
        outerRadius={100}
        dataKey="amount"
      >
        {chartData.map((_, index) => (
          <Cell key={index} fill={COLORS[index % COLORS.length]}></Cell>
        ))}
      </Pie>
      <Tooltip/>
      <Legend/>
    </PieChart>
  )
};

export default PieChartComponent;
